package org.mcclone.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 * Created by mcclone on 17-7-8.
 */
public class HttpSolrClientTest {

    private HttpSolrClient solrClient;

    @Before
    public void setUp() throws Exception {
        solrClient = new HttpSolrClient.Builder("http://localhost:8983/solr/test").build();
    }

    @Test
    public void add() throws Exception {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", UUID.randomUUID().toString().replaceAll("-", ""));
        document.addField("name", "zhdfgdfg");
        document.addField("flow", "12312312");
        solrClient.add(document);
        solrClient.commit();
    }

    @Test
    public void query() throws Exception {
        SolrQuery solrParams = new SolrQuery("*:*");
        QueryResponse response = solrClient.query(solrParams);
        response.getResults().forEach(System.out::println);
    }

    @Test
    public void facet() throws Exception {
        SolrQuery solrParams = new SolrQuery("*:*");
        solrParams.setFacet(true);
        solrParams.addFacetField("name");
        QueryResponse response = solrClient.query(solrParams);
        response.getResults().forEach(System.out::println);
        FacetField facetField = response.getFacetField("name");
        facetField.getValues().forEach(count -> {
            System.out.println(count.getName() + ":" + count.getCount());
        });
    }

    public static void main(String[] args) {
        System.out.println(new Date().toInstant().toString());
    }
}
