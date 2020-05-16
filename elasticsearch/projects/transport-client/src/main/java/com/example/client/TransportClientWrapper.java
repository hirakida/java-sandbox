package com.example.client;

import java.util.List;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery.ScoreMode;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TransportClientWrapper {
    private static final String TYPE = "type";
    @SuppressWarnings("deprecation")
    private final TransportClient client;

    @SuppressWarnings("deprecation")
    public TransportClientWrapper(TransportClient client) {
        this.client = client;
    }

    public BulkResponse bulkIndex(String index, List<String> sources) {
        BulkRequestBuilder builder = client.prepareBulk();
        sources.forEach(source -> {
            IndexRequestBuilder request = client.prepareIndex()
                                                .setIndex(index)
                                                .setType(TYPE)
                                                .setSource(source, XContentType.JSON);
            builder.add(request);
        });

        return builder.execute()
                      .actionGet();
    }

    public boolean indexExists(String index) {
        return client.admin()
                     .indices()
                     .exists(new IndicesExistsRequest(index))
                     .actionGet()
                     .isExists();
    }

    public AcknowledgedResponse deleteIndex(String index) {
        return client.admin()
                     .indices()
                     .delete(new DeleteIndexRequest(index))
                     .actionGet();
    }

    public GetResponse get(String index, String id) {
        return client.prepareGet()
                     .setIndex(index)
                     .setId(id)
                     .get();
    }

    public DeleteResponse delete(String index, String id) {
        return client.prepareDelete()
                     .setIndex(index)
                     .setId(id)
                     .get();
    }

    public SearchResponse searchByMultiMatch(String index, Object text, String sortFieldName,
                                             String... fieldNames) {
        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(text, fieldNames);
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort(sortFieldName)
                                                   .order(SortOrder.ASC);

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

        return client.search(request)
                     .actionGet();
    }
}
