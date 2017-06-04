package org.mcclone.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by mcclone on 17-6-4.
 */
public class SolrInsertClient {

    public static void main(String[] args) throws IOException, SolrServerException {
        String urlString = "http://localhost:8983/solr/test";
        SolrClient solr = new HttpSolrClient.Builder(urlString).build();
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", UUID.randomUUID().toString().replaceAll("-", ""));
        document.addField("name", "name1");
        document.addField("flow", 3);
        solr.add(document);
        solr.commit();
    }
}
