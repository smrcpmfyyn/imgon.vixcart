/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vixcart.imgon.result;

import com.vixcart.imgon.intfc.vres.Result;
import com.vixcart.imgon.message.CorrectMsg;
import com.vixcart.imgon.message.ErrMsg;
import com.vixcart.imgon.message.ValidationMsg;

/**
 * @company techvay
 * @author rifaie
 */
public class AddLogoResult implements Result {
    private String at;
    private String admintype;
    private String company;
    private String filename;
    private String filesize;
    private String reqValidation;

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getAdmintype() {
        return admintype;
    }

    public void setAdmintype(String admintype) {
        this.admintype = admintype;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getReqValidation() {
        return reqValidation;
    }

    public void setReqValidation(String reqValidation) {
        this.reqValidation = reqValidation;
    }
    
    @Override
    public String getValidationResult() {
        String result;
        if (isRequestValid()) {
            result = ValidationMsg.VALID;
        } else {
            result = getAllErrors();
        }
        return result;
    }

    @Override
    public boolean isRequestValid() {
        boolean flag = false;
        if (reqValidation.startsWith(CorrectMsg.CORRECT_MESSAGE)) {
            flag = true;
        }
        return flag;
    }

    @Override
    public String getAllErrors() {
        String error = ErrMsg.ERR_ERR + "#";
        if (at.startsWith(ErrMsg.ERR_MESSAGE)) {
            error += "at#";
        } else if (admintype.startsWith(ErrMsg.ERR_MESSAGE)) {
            error += "admintype#";
        } else {
            if (company.startsWith(ErrMsg.ERR_MESSAGE)) {
                error += "company#";
            }
            if (filename.startsWith(ErrMsg.ERR_MESSAGE)) {
                error += "filename#";
            }
            if (filesize.startsWith(ErrMsg.ERR_MESSAGE)) {
                error += "filesize#";
            }
        }
        return error.substring(0, error.length() - 1);
    }
}
