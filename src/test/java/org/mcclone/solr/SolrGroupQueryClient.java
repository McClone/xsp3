package org.mcclone.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;

/**
 * solr统计
 * Created by mcclone on 17-6-4.
 */
public class SolrGroupQueryClient {

    public static void main(String[] args) throws IOException, SolrServerException {
        String urlString = "http://localhost:8983/solr/test";
        SolrClient solr = new HttpSolrClient.Builder(urlString).build();
        SolrQuery solrParams = new SolrQuery("*:*");
        solrParams.setFacet(true);
        solrParams.addFacetField("name");
        QueryResponse response = solr.query(solrParams);
        FacetField facetField = response.getFacetField("name");
        facetField.getValues().forEach(count -> {
            System.out.println(count.getName() + ":" + count.getCount());
        });
    }
}
