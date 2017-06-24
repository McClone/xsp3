package org.mcclone.mongodb;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcclone on 17-4-2.
 */
public class MongodbClient {

    public static void main(String[] args) {
        //鉴权
        MongoCredential credential = MongoCredential.createCredential("test", "test", "test".toCharArray());
        List<MongoCredential> mongoCredentialList = new ArrayList<>();
        mongoCredentialList.add(credential);
        //连接
        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017), mongoCredentialList);
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("collection");
        FindIterable<Document> documents = collection.find();
        documents.forEach((Block<? super Document>) document -> System.out.println(document.toString()));

    }
}
