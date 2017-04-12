/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vixcart.imgon.intfc.validation;

/**
 * @company techvay
 * @author rifaie
 */
public interface AddLogoValidator extends Validator{
    
    public String validateCompany() throws Exception;
    
    public String validateFileName() throws Exception;
    
    public String validateFileSize() throws Exception;
}
