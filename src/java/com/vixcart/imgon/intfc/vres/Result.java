/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vixcart.imgon.intfc.vres;

/**
 *
 * @author rifaie
 */
public interface Result {

    /**
     *
     * @return
     */
    public String getValidationResult();

    /**
     *
     * @return
     */
    public boolean isRequestValid();

    /**
     *
     * @return
     */
    public String getAllErrors();
}
