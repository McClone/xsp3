package org.mmcclone.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Created by mcclone on 17-6-19.
 */
public abstract class HbaseUtils {

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

    public static void printResultScanner(ResultScanner resultScanner) {
        for (Result res : resultScanner) {
            for (Cell c : res.rawCells()) {
                System.out.println(Bytes.toString(CellUtil.cloneRow(c))
                        + " ==> " + Bytes.toString(CellUtil.cloneFamily(c))
                        + " {" + Bytes.toString(CellUtil.cloneQualifier(c))
                        + ":" + Bytes.toString(CellUtil.cloneValue(c))
                        + "}");
            }
        }
    }

}
