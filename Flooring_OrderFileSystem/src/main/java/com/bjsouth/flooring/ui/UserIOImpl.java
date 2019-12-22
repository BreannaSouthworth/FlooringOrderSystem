/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.ui;

import java.util.Scanner;

/**
 *
 * @author Bree
 */
public class UserIOImpl implements UserIO{
    Scanner scanner;
    
    public UserIOImpl(){
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        double result;
        
        print(prompt);
        result = scanner.nextDouble();
        scanner.nextLine();
        
        return result;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double result = 0;
        
        print(prompt);
        boolean repeat = true;
        while (repeat){
            result = scanner.nextDouble();
            scanner.nextLine();
            
            if (result >= min && result <= max){
                repeat = false;
            } else {
                print("Sorry, that's not in the correct range. Try again.");
            }
        }
        
        return result;
    }

    @Override
    public float readFloat(String prompt) {
        float result;
        
        print(prompt);
        result = scanner.nextFloat();
        scanner.nextLine();
        
        return result;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float result = 0;
        
        print(prompt);
        boolean repeat = true;
        while (repeat){
            result = scanner.nextFloat();
            scanner.nextLine();
            
            if (result >= min && result <= max){
                repeat = false;
            } else {
                print("Sorry, that's not in the correct range. Try again.");
            }
        }
        
        return result;
    }

    @Override
    public int readInt(String prompt) {
        int result;
        
        print(prompt);
        result = scanner.nextInt();
        scanner.nextLine();
        
        return result;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int result = 0;
        
        print(prompt);
        boolean repeat = true;
        while (repeat){
            result = scanner.nextInt();
            scanner.nextLine();
            
            if (result >= min && result <= max){
                repeat = false;
            } else {
                print("Sorry, that's not in the correct range. Try again.");
            }
        }
        
        return result;
    }

    @Override
    public long readLong(String prompt) {
        long result;
        
        print(prompt);
        result = scanner.nextLong();
        scanner.nextLine();
        
        return result;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long result = 0;
        
        print(prompt);
        boolean repeat = true;
        while (repeat){
            result = scanner.nextLong();
            scanner.nextLine();
            
            if (result >= min && result <= max){
                repeat = false;
            } else {
                print("Sorry, that's not in the correct range. Try again.");
            }
        }
        
        return result;
    }

    @Override
    public String readString(String prompt) {
        String result = "";
        
        print(prompt);
        result = scanner.nextLine();
        
        return result;
    }
}
