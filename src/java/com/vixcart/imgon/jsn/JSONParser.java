/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vixcart.imgon.jsn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vixcart.imgon.mongo.mod.AdminID;
import com.vixcart.imgon.result.AddLogoResult;
import java.io.IOException;

/**
 * @company techvay
 * @author rifaie
 */
public class JSONParser {
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    public static AdminID parseJSONAID(String admin) throws IOException {
        AdminID res;
        res = MAPPER.readValue(admin, AdminID.class);
        return res;
    }

    public static AddLogoResult parseJSONADDAFF(String reqv) throws IOException {
        AddLogoResult res;
        res = MAPPER.readValue(reqv, AddLogoResult.class);
        return res;
    }
}
