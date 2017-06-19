package org.mmcclone.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;

import java.io.IOException;

/**
 * Created by mcclone on 17-3-12.
 */
public class HbaseClient {

    public static void main(String... args) {
        Configuration config = HBaseConfiguration.create();
        try {
            Connection connection = ConnectionFactory.createConnection(config);
            Table table = connection.getTable(TableName.valueOf("test"));
            Put put = new Put("20170404001".getBytes());
            put.addColumn("cf".getBytes(), "c1".getBytes(), "value1".getBytes());
            put.addColumn("cf".getBytes(), "c2".getBytes(), "value2".getBytes());
            table.put(put);
            table.getScanner("cf".getBytes()).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createSchemaTables(Configuration config, String tableName, String familyName) throws IOException {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Admin admin = connection.getAdmin()) {
            HTableDescriptor table = new HTableDescriptor(TableName.valueOf(tableName));
            table.addFamily(new HColumnDescriptor(familyName).setCompressionType(Compression.Algorithm.NONE));
            if (!admin.tableExists(table.getTableName())) {
                admin.createTable(table);
            }
        }
    }

}
