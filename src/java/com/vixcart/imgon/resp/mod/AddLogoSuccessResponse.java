/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vixcart.imgon.resp.mod;

/**
 * @company techvay
 * @author rifaie
 */
public class AddLogoSuccessResponse {
    private final String status;
    private final String accessToken;

    public AddLogoSuccessResponse(String status, String accessToken) {
        this.status = status;
        this.accessToken = accessToken;
    }

    public String getStatus() {
        return status;
    }

    public String getAccessToken() {
        return accessToken;
    }
    
    @Override
    public String toString() {
        return "{\"status\":\""+status + "\"}";
    }
}
