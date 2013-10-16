/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lalotech.barcode;

/**
 *
 * @author Eduardo
 */
public enum MediaType {
    JPEG("image/jpeg"),
    PNG("image/png"),
    GIF("image/gif"),
    SVG("image/svg"),
    EPS("image/eps"),    
    BMP("image/bmp");
    private String value = null;
    private MediaType(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return this.value;
    }
}
