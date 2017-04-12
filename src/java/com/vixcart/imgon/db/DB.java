/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vixcart.imgon.db;

import java.sql.SQLException;

/**
 * @company techvay
 * @author rifaie
 */
public class DB {
    private static DBConnect dbConnect;
    private static MongoConnect mongoConnect;

    /**
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static DBConnect getConnection() throws ClassNotFoundException, SQLException {
        dbConnect = new DBConnect();
        return dbConnect;
    } 
    
    public static MongoConnect getMongoConnection() throws Exception{
        mongoConnect = new MongoConnect();
        return mongoConnect;
    }

}
