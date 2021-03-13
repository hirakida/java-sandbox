package com.example.client;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;

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
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery.ScoreMode;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.ScriptQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder.FilterFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchClient {
    private final RestHighLevelClient client;
    private final ActionListener<IndexResponse> actionListener;

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

    public BulkResponse bulkIndex(String index, Map<String, String> sources) {
        BulkRequest bulkRequest = new BulkRequest();
        sources.forEach((key, value) -> {
            IndexRequest request = new IndexRequest(index);
            request.id(key);
            request.source(value, XContentType.JSON);
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

    public UpdateResponse update(String index, String id, String source) {
        UpdateRequest request = new UpdateRequest();
        request.index(index);
        request.id(id);
        request.doc(source, XContentType.JSON);
        try {
            return client.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public UpdateResponse update(String index, String id, Script script) {
        UpdateRequest request = new UpdateRequest();
        request.index(index);
        request.id(id);
        request.script(script);
        try {
            return client.update(request, RequestOptions.DEFAULT);
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

    public SearchResponse searchByMatchAll(String index, String fieldName) {
        MatchAllQueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort(fieldName);

        return search(index, queryBuilder, sortBuilder);
    }

    public SearchResponse searchByTerm(String index, String fieldName, Object value) {
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery(fieldName, value);
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort(fieldName)
                                                   .order(SortOrder.ASC);

        return search(index, queryBuilder, sortBuilder);
    }

    public SearchResponse searchByTerms(String index, String fieldName, Object... values) {
        TermsQueryBuilder queryBuilder = QueryBuilders.termsQuery(fieldName, values);
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort(fieldName)
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

    public SearchResponse searchByBool(String index, Map<String, Object> queries) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queries.forEach((fieldName, value) -> queryBuilder.must(QueryBuilders.termQuery(fieldName, value)));
        ScoreSortBuilder sortBuilder = SortBuilders.scoreSort();

        return search(index, queryBuilder, sortBuilder);
    }

    public SearchResponse searchByScript(String index, String fieldName, String value) {
        Script script = new Script(String.format("doc['%s'].value > %s", fieldName, value));
        ScriptQueryBuilder queryBuilder = QueryBuilders.scriptQuery(script);
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort(fieldName)
                                                   .order(SortOrder.DESC);

        return search(index, queryBuilder, sortBuilder);
    }

    public SearchResponse searchByFunctionScore(String index, String fieldName) {
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

    public SearchResponse searchByFunctionScore(String index, String fieldName, Map<String, Object> queries) {
        FilterFunctionBuilder[] functionBuilders =
                queries.entrySet()
                       .stream()
                       .map(entry -> new FilterFunctionBuilder(
                               QueryBuilders.termQuery(entry.getKey(), entry.getValue()),
                               ScoreFunctionBuilders.fieldValueFactorFunction(fieldName)))
                       .toArray(FilterFunctionBuilder[]::new);

        FunctionScoreQueryBuilder queryBuilder = QueryBuilders.functionScoreQuery(functionBuilders)
                                                              .boostMode(CombineFunction.REPLACE)
                                                              .scoreMode(ScoreMode.SUM);
        ScoreSortBuilder sortBuilder = SortBuilders.scoreSort();

        return search(index, queryBuilder, sortBuilder);
    }

    public CountResponse count(String index, QueryBuilder queryBuilder) {
        CountRequest request = new CountRequest(index)
                .query(queryBuilder);

        try {
            return client.count(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private SearchResponse search(String index, QueryBuilder queryBuilder, SortBuilder<?> sortBuilder) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(queryBuilder)
                .sort(sortBuilder)
                .from(0)
                .size(100)
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
