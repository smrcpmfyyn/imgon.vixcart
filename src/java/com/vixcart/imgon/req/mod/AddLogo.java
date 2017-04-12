/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vixcart.imgon.req.mod;

/**
 * @company techvay
 * @author rifaie
 */
public class AddLogo {
    private final String  at;
    private String admin_id;
    private String utype;
    private final String company;
    private final String fileName;
    private final long fileSize;

    public AddLogo(String at, String company, String fileName, long fileSize) {
        this.company = company;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.at = at;
    }

    public String getCompany() {
        return company;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public String getAt() {
        return at;
    }
    
}
