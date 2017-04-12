/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vixcart.imgon.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * @company techvay
 * @author rifaie
 */
public class DBConnect {

    private Connection connect = null;
    private ResultSet rs;

    public DBConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/vaydeal", "techvay", "techvay");
    }

    /**
     *
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        connect.close();
    }
    
    public int checkCompany(String company) throws SQLException {
        PreparedStatement ps = connect.prepareStatement("SELECT count(*) FROM affiliates WHERE company = ?");
        int count = 0;
        ps.setString(1, company);
        rs = ps.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();
        ps.close();
        return count;
    }

    public HashSet<String> getSubTypes(String type) throws SQLException {
        PreparedStatement getSubtypes = connect.prepareStatement("SELECT type,id FROM admin_user_type WHERE supertype = ?");
        int typeID = getTypeID(type);
        HashSet<String> types = new HashSet<>();
        types.add(type);
        if (typeID != 0) {
            getSubtypes.setInt(1, typeID);
            rs = getSubtypes.executeQuery();
            while (rs.next()) {
                types.add(rs.getString(1));
            }
        }
        rs.close();
        return types;
    }

    private int getTypeID(String type) throws SQLException {
        PreparedStatement getTypeID = connect.prepareStatement("Select id FROM admin_user_type WHERE type = ?");
        getTypeID.setString(1, type);
        rs = getTypeID.executeQuery();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt(1);
        }
        rs.close();
        getTypeID.close();
        return id;
    }
    
    public boolean checkNBAdminID(String admin_id) throws SQLException {
        PreparedStatement checkNBAdminID = connect.prepareStatement("SELECT count(*) FROM admin_logger_not_blocked WHERE admin_id = ?");
        checkNBAdminID.setString(1, admin_id);
        rs = checkNBAdminID.executeQuery();
        if(rs.next()){
            if(rs.getInt(1)==1){
                return true;
            }
        }
        return false;
    }
    
    public boolean blockAdmin(String admin_id) throws SQLException {
        PreparedStatement blockAdmin = connect.prepareStatement("UPDATE admin_login SET blockstatus = 2 WHERE admin_id = ?");
        blockAdmin.setString(1, admin_id);
        int count = blockAdmin.executeUpdate();
        if(count == 1){
            return true;
        }
        blockAdmin.close();
        return false;
    }
}
