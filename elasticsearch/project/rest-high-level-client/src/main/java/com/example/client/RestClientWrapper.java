package com.example.client;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery.ScoreMode;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder.FilterFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import com.example.config.ActionListenerImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestClientWrapper {
    private final ActionListener<IndexResponse> actionListener = new ActionListenerImpl<>();
    private final RestHighLevelClient client;

    public CreateIndexResponse createIndex(String index) {
        CreateIndexRequest request = new CreateIndexRequest(index);
        try {
            return client.indices()
                         .create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public AcknowledgedResponse deleteIndex(String index) {
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        try {
            return client.indices()
                         .delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public boolean indexExists(String index) {
        GetIndexRequest request = new GetIndexRequest(index);
        try {
            return client.indices()
                         .exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public IndexResponse index(String index, String source) {
        IndexRequest request = new IndexRequest(index);
        request.source(source, XContentType.JSON);
        try {
            return client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void indexAsync(String index, String source) {
        IndexRequest request = new IndexRequest(index);
        request.source(source, XContentType.JSON);
        client.indexAsync(request, RequestOptions.DEFAULT, actionListener);
    }

    public BulkResponse bulkIndex(String index, List<String> sources) {
        BulkRequest bulkRequest = new BulkRequest();
        sources.forEach(source -> {
            IndexRequest request = new IndexRequest(index);
            request.source(source, XContentType.JSON);
            bulkRequest.add(request);
        });

        try {
            return client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public boolean exists(String index, String id) {
        GetRequest request = new GetRequest(index, id);
        try {
            return client.exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public GetResponse get(String index, String id) {
        GetRequest request = new GetRequest(index, id);
        try {
            return client.get(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public DeleteResponse delete(String index, String id) {
        DeleteRequest request = new DeleteRequest(index, id);
        try {
            return client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public SearchResponse searchByTerm(String index, String fieldName, Object text) {
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery(fieldName, text);
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort(fieldName)
                                                   .order(SortOrder.ASC);

        return search(index, queryBuilder, sortBuilder);
    }

    public SearchResponse searchByMatch(String index, String fieldName, Object text) {
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery(fieldName, text);
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort(fieldName)
                                                   .order(SortOrder.ASC);

        return search(index, queryBuilder, sortBuilder);
    }

    public SearchResponse searchByMultiMatch(String index, Object text, String sortFieldName,
                                             String... fieldNames) {
        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(text, fieldNames);
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort(sortFieldName)
                                                   .order(SortOrder.ASC);

        return search(index, queryBuilder, sortBuilder);
    }

    public SearchResponse searchByRange(String index, String fieldName, Object from, Object to) {
        RangeQueryBuilder queryBuilder = QueryBuilders.rangeQuery(fieldName)
                                                      .from(from)
                                                      .to(to);
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort(fieldName)
                                                   .order(SortOrder.ASC);

        return search(index, queryBuilder, sortBuilder);
    }

    public SearchResponse searchByBool(String index, Object text, String... fieldNames) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        for (String fieldName : fieldNames) {
            queryBuilder.should(QueryBuilders.matchQuery(fieldName, text));
        }
        ScoreSortBuilder sortBuilder = SortBuilders.scoreSort();

        return search(index, queryBuilder, sortBuilder);
    }

    public SearchResponse searchByScore(String index, String fieldName) {
        FilterFunctionBuilder[] filterFunctionBuilders = {
                new FilterFunctionBuilder(ScoreFunctionBuilders.fieldValueFactorFunction(fieldName)
                                                               .missing(0))
        };
        FunctionScoreQueryBuilder queryBuilder = QueryBuilders.functionScoreQuery(filterFunctionBuilders)
                                                              .boostMode(CombineFunction.REPLACE)
                                                              .scoreMode(ScoreMode.SUM);
        ScoreSortBuilder sortBuilder = SortBuilders.scoreSort();

        return search(index, queryBuilder, sortBuilder);
    }

    private SearchResponse search(String index, QueryBuilder queryBuilder, SortBuilder<?> sortBuilder) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(queryBuilder)
                .sort(sortBuilder)
                .from(0)
                .size(20)
                .timeout(TimeValue.timeValueMinutes(5));
        SearchRequest request = new SearchRequest(index)
                .source(sourceBuilder);

        try {
            return client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
