/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lalotech.barcode.exception;

/**
 *
 * @author Eduardo
 */
public class BarCodeCreateException extends RuntimeException{
    public BarCodeCreateException(String message, Throwable underlyingException) {
        super(message, underlyingException);
    }   
}
