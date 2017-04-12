/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vixcart.imgon.imgpath;

import java.io.File;

/**
 * @company techvay
 * @author rifaie
 */
public final class ImagePath {
    private static final String LOGO_PATH = "imgs"+File.separator+"logo"+File.separator;

    public static String getLOGO_PATH() {
        return LOGO_PATH;
    }
    
    
}
