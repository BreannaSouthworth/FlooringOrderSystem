/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.service;

/**
 *
 * @author Bree
 */
public class IncorrectDateFormException extends Exception {
    public IncorrectDateFormException(String message){
        super(message);
    }
    
    public IncorrectDateFormException(String message, Throwable cause){
        super(message, cause);
    }
}
