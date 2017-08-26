package org.mcclone.solr;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.LBHttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by mcclone on 17-7-14.
 */
public class LBHttpSolrClientTest {

    @Test
    public void test() throws IOException, SolrServerException {

        String[] servers = {"http://localhost:9031/solr/", "http://localhost:9032/solr/"};
        LBHttpSolrClient lbHttpSolrClient = new LBHttpSolrClient.Builder().withBaseSolrUrls(servers).build();

        for (int i = 0; i < 10; i++) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", UUID.randomUUID().toString().replaceAll("-", ""));
            document.addField("name", "name");
            int flow = RandomUtils.nextInt();
            System.out.println(flow);
            document.addField("flow", flow);
            UpdateRequest request = new UpdateRequest();
            request.add(document);
            request.commit(lbHttpSolrClient, "core_1");
        }
    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE + 2 & Integer.MAX_VALUE);
        System.out.println(Long.MAX_VALUE & Integer.MAX_VALUE);
        System.out.println(999 & Integer.MAX_VALUE);
        System.out.println(999 % 3);
        System.out.println(998 % 3);
    }

}
