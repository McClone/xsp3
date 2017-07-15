package org.mcclone.solr;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by mcclone on 17-7-8.
 */
public class CoreAdminRequestTest {
    private HttpSolrClient solrClient;

    @Before
    public void setUp() throws Exception {
        solrClient = new HttpSolrClient.Builder("http://localhost:9031/solr").build();
    }

    @Test
    public void renameCore() throws Exception {
        try {
            CoreAdminRequest.renameCore("test", "test_new", solrClient);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createCore() throws Exception {
        CoreAdminRequest.createCore("test_1", "test_1", solrClient,"solrconfig.xml","schema.xml");
    }
}
