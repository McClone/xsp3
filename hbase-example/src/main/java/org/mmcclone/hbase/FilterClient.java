package org.mmcclone.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcclone on 17-6-19.
 */
public class FilterClient {

    public static void main(String[] args) {
        List<Filter> filters = new ArrayList<>();
        FilterList filterList = new FilterList(filters);
        Filter filter = new ValueFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("1"));
        filters.add(filter);
        Configuration config = HBaseConfiguration.create();
        try {
            Connection connection = ConnectionFactory.createConnection(config);
            Scan scan = new Scan();
            scan.setFilter(filterList);
            Table table = connection.getTable(TableName.valueOf("test"));
            ResultScanner resultScanner = table.getScanner(scan);
            HbaseUtils.printResultScanner(resultScanner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
