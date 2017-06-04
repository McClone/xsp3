package org.mcclone.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.StatsParams;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * solr统计
 * Created by mcclone on 17-6-4.
 */
public class SolrGroupQueryClient {

    public static void main(String[] args) throws IOException, SolrServerException {
        String urlString = "http://localhost:8983/solr/test";
        SolrClient solr = new HttpSolrClient.Builder(urlString).build();
        SolrQuery solrParams = new SolrQuery("*:*");
        solrParams.setParam(StatsParams.STATS_FACET, "name");
        solrParams.setGetFieldStatistics("flow");
        solrParams.setGetFieldStatistics(true);
        QueryResponse response = solr.query(solrParams);
        Map<String, FieldStatsInfo> map = response.getFieldStatsInfo();
        for (String key : map.keySet()) {
            FieldStatsInfo f = map.get(key);
            System.out.println(f.getFacets());
            System.out.println(f.getName());
            System.out.println(f.getCount());
            System.out.println(f.getMax());
            System.out.println(f.getMin());
            System.out.println(f.getSum());
            System.out.println(f.getMean());
            System.out.println(f.getMissing());
            System.out.println(f.getStddev());//标准差
        }
    }
}
