/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vixcart.imgon.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.exclude;
import com.vixcart.imgon.message.ErrMsg;
import com.vixcart.imgon.mongo.mod.AdminID;
import java.io.IOException;
import org.bson.Document;

import com.vixcart.imgon.jsn.JSONParser;

/**
 * @company techvay
 * @author rifaie
 */
public class MongoConnect {
    
    private final MongoDatabase db;
    private final MongoClient mongoClient;

    public MongoConnect() throws Exception {
        MongoClientURI uri = new MongoClientURI("mongodb://35.154.242.9/");
        mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase("vaydeal");
    }
    
    public void closeConnection() throws Exception{
        mongoClient.close();
    }
    
    public AdminID getAdminID(String at) throws IOException {
        MongoCollection<Document> gpi = db.getCollection("admin_access_token");
        FindIterable<Document> find = gpi.find(Filters.and(eq("token", at), eq("status", "logged"))).projection(exclude("token", "_id", "status"));
        AdminID aid = null;
        if (find.iterator().hasNext()) {
            aid = JSONParser.parseJSONAID(find.first().toJson());
        } else {
            aid = new AdminID();
            aid.setProfile_id(ErrMsg.ERR_ACCESS_TOKEN);
        }
        return aid;
    }
}
