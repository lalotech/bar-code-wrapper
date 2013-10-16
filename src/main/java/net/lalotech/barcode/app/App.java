/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lalotech.barcode.app;

import net.lalotech.barcode.BarCode;
import net.lalotech.barcode.CodeBarType;
import net.lalotech.barcode.MediaType;

/**
 *
 * @author Eduardo
 */
public class App {
    public static void main(String[] args) throws Exception{
        
        String basePath = "C:\\Users\\Eduardo\\Documents\\";        
        BarCode.from("12345678901234").format(MediaType.PNG).toGrayScale(true).type(CodeBarType.INTl2of5).file(basePath,"int12of5");
        
    }
}
