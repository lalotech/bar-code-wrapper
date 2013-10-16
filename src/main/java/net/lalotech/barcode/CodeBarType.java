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
public enum CodeBarType {
    CODABAR("codabar"),CODE_39("code39"),
    POSTNET("postnet"),INTl2of5("intl2of5"),
    EAN_128("ean-128"),ROYAL_MAIL_CBC("royal-mail-cbc"),
    EAN_13("ean-13"),ITF_14("itf-14"),
    DATAMATRIX("datamatrix"),CODE_128("code128"),
    PDF417("pdf417"),USPS4CB("usps4cb"),
    UPC_A("upc-a"),UPC_E("upc-e"),EAN_8("ean-8");

    private String value = null;
    private CodeBarType(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return this.value;
    }
    
}
