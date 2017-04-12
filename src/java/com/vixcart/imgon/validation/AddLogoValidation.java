/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vixcart.imgon.validation;

import com.vixcart.imgon.intfc.validation.Validation;
import com.vixcart.imgon.message.CorrectMsg;
import com.vixcart.imgon.message.ErrMsg;
import com.vixcart.imgon.req.mod.AddLogo;

/**
 * @company techvay
 * @author rifaie
 */
public class AddLogoValidation implements Validation {
    private final AddLogo req;
    private String paramValue = "";
    private String paramName = "";

    public AddLogoValidation(AddLogo req) {
        this.req = req;
    }

    @Override
    public void validation() throws Exception {
        AddLogoConstraints reqC = new AddLogoConstraints(req);
        String valid = "";
        valid += reqC.validateAccessToken();
        if (valid.startsWith(CorrectMsg.CORRECT_MESSAGE)) {
            String valid1 = reqC.validateUserType("affiliate");
            valid += "#" + valid1;
            if(valid1.startsWith(CorrectMsg.CORRECT_MESSAGE)){
                valid += "#"+reqC.validateCompany();
                valid += "#"+reqC.validateFileName();
                valid += "#"+reqC.validateFileSize();
            }
        }
        reqC.closeConnection();
        int count = 0;
        for (String str : valid.split("#")) {
            paramName += str.split(" ")[1].toLowerCase() + "#";
            if (!str.startsWith(CorrectMsg.CORRECT_MESSAGE)) {
                count++;
                paramValue += str + "#";
            } else {
                paramValue += CorrectMsg.CORRECT_MESSAGE + "#";
            }
        }
        paramName += "reqValidation";
        if (count == 0) {
            paramValue += CorrectMsg.CORRECT_ADD_LOGO;
        } else {
            paramValue += ErrMsg.ERR_ADD_LOGO;
        }
    }
    
    @Override
    public String toString() {
        String[] paramsN = paramName.split("#");
        String[] paramV = paramValue.split("#");
        String json = "";
        for (int i = 0; i < paramsN.length; i++) {
            json += "\"" + paramsN[i] + "\"" + ":" + "\"" + paramV[i] + "\" ,";
        }
        json = json.substring(0, json.length() - 1);
        return "{" + json + "}";
    }
}

