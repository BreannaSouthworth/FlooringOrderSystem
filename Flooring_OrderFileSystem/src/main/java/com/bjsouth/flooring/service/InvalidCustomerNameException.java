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
public class InvalidCustomerNameException extends Exception {
    public InvalidCustomerNameException(String message){
        super(message);
    }
    
    public InvalidCustomerNameException(String message, Throwable cause){
        super(message, cause);
    }
}
