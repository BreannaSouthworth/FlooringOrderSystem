/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.controller;

import com.bjsouth.flooring.dao.FilePersistenceException;
import com.bjsouth.flooring.dto.Order;
import com.bjsouth.flooring.service.FMServiceImpl;
import com.bjsouth.flooring.ui.FMView;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Bree
 */
public class FMController {
    FMView view;
    FMServiceImpl service;
    
    public FMController(FMView view, FMServiceImpl service){
        this.service = service;
        this.view = view;
        
    }
    
    public void run(){
        try{
            service.loadData();
            service.loadAllOrderFiles();
        }catch(FilePersistenceException e){
            view.displayNoDataError();
            System.exit(0);
        }
        boolean keepGoing = true;
        while(keepGoing){
            switch(service.getModeSetting()){
                case PRODUCTION:
                    break;
                case TRAINING:
                    view.displayTrainingBanner();
                    break;
            }
            
            int menuSelection = getMainMenuSelection();
            switch(menuSelection){
                case 1:
                    displayOrders();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    saveOrders();
                    break;
                case 6:
                    exit();
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
            }
        }
    }
    
    private int getMainMenuSelection(){
        return view.printMainMenuSelection();
    }
    
    private void addOrder(){
        Order newOrder = new Order();
        
        boolean invalidOrderDate;
        String orderDateString = "";
        do{
            orderDateString = view.getDateChoice();
            invalidOrderDate = service.validateNewOrderDate(orderDateString);
            if(invalidOrderDate){
                view.displayInvalidValueError("Order Date");
                view.displayNewOrderDateRules();
            }
        }while(invalidOrderDate);
        LocalDate orderDate = LocalDate.parse(orderDateString, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        newOrder.setOrderDate(orderDate);
        
        boolean invalidCustomerName;
        String customerName = "";
        do{
            customerName = view.getCustomerName();
            invalidCustomerName = service.validateCustomerName(customerName);
            if(invalidCustomerName){
                view.displayInvalidValueError("Customer Name");
                view.displayCustomerNameRules();
            }
        }while(invalidCustomerName);
        newOrder.setCustomerName(customerName);
        
        boolean invalidState;
        String state = "";
        do{
            state = view.getState();
            invalidState = service.validateState(state);
            if(invalidState){
                view.displayInvalidValueError("State");
                view.displayStateRules();
            }
        }while(invalidState);
        newOrder.setState(state);
        
        boolean invalidProductType;
        String productType = "";
        do{
            productType = view.getProductType(service.getAllProduct());
            invalidProductType = service.validateProductType(productType);
            if(invalidProductType){
                view.displayInvalidValueError("Product Type");
                view.displayProductTypeRules();
            }
        }while(invalidProductType);
        newOrder.setProductType(productType);
        
        boolean invalidArea;
        BigDecimal area;
        do{
            area = view.getArea();
            invalidArea = service.validateArea(area);
            if(invalidArea){
                view.displayInvalidValueError("Area");
                view.displayAreaRules();
            }
        }while(invalidArea);
        newOrder.setArea(area);
        
        service.calculateOrderTotal(newOrder);
        
        view.displayOrderSummary(newOrder);
        boolean save = view.getConfirmAddOrder();
        if(save){
            Integer orderNumber = service.nextOrderNumber();
            newOrder.setOrderNumber(orderNumber);
            service.addOrder(orderNumber, newOrder);
            view.displayConfrimOrderSaved(orderNumber);
            view.returnToMainMenu();
        }else{
            view.displayConfirmOrderDiscard();
            view.returnToMainMenu();
        }
    }
    
    private void displayOrders(){
        view.displayOrdersBanner();
        LocalDate orderDate = getOrderDateChoice();
        List<Order> ordersByDate = getOrdersOfDateChoice(orderDate);
        
        boolean noOrdersOfDateFound = service.ordersForDateNotFound(ordersByDate);
        if(noOrdersOfDateFound){
            view.displayEmptyDisplayOrdersError();
        } else{
            view.displayOrders(ordersByDate, orderDate);
        }
        view.returnToMainMenu();
    }
    
    private LocalDate getOrderDateChoice(){
        boolean invalidDate;
        String dateChoice;
        do{
            dateChoice = view.getDateChoice();
            invalidDate = service.validateRealDate(dateChoice);
            
            if(invalidDate){
                view.displayInvalidValueError("Order Date");
                view.displaySearchOrderDateRules();
            }
        }while(invalidDate);
        LocalDate orderDate = LocalDate.parse(dateChoice, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        return orderDate;
    }
    
    private List<Order> getOrdersOfDateChoice(LocalDate orderDate){
        List<Order> ordersByDate = service.getOrdersByOrderDate(orderDate);
        return ordersByDate;
    }
    
    private Integer getOrderNumberChoice(){
        boolean invalidInteger;
        Integer orderNumber = 0;
        
        do{
            try{
                orderNumber = view.getNumberChoice();
                invalidInteger = false;
            }catch(NumberFormatException e){
                view.displayInvalidValueError("Order Number");
                view.displayOrderNumberRules();
                invalidInteger = true;
            }
        }while(invalidInteger);
        
        return orderNumber;
    }
    
    private Order getOrderOfNumberChoice(Integer orderNumber, List<Order> ordersByDate){
        Order foundOrder = new Order();
        for(Order o : ordersByDate){
            if(o.getOrderNumber().compareTo(orderNumber) == 0){
                foundOrder = o;
            }
        }
        
        if(foundOrder.getOrderNumber() == null){
            foundOrder = null;
        }
        
        return foundOrder;
    }
    
    private void editOrder(){
        view.editOrderBanner();
        LocalDate orderDate = getOrderDateChoice();
        List<Order> ordersByDate = getOrdersOfDateChoice(orderDate);
        
        boolean noOrdersOfDateFound = service.ordersForDateNotFound(ordersByDate);
        if(noOrdersOfDateFound){
            view.displayEmptyDisplayOrdersError();
        } else{
            Integer orderNumber = getOrderNumberChoice();
            Order foundOrder = getOrderOfNumberChoice(orderNumber, ordersByDate);
            
            if(foundOrder == null){
                view.displayNoOrderFoundError();
            }else{
                editEachOrderProperty(orderNumber, foundOrder);
            }
        }
        view.returnToMainMenu();
    }
    
    private void editEachOrderProperty(Integer orderNumber, Order editedOrder){
        boolean invalidOrderDate;
        String orderDateString = "";
        do{
            orderDateString = view.getEditedOrderDate(editedOrder.getOrderDate());
            invalidOrderDate = service.validateNewOrderDate(orderDateString);
            if(invalidOrderDate){
                view.displayInvalidValueError("Order Date");
                view.displayNewOrderDateRules();
            }
        }while(invalidOrderDate);
        LocalDate orderDate = LocalDate.parse(orderDateString, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        boolean invalidCustomerName;
        String customerName = "";
        do{
            customerName = view.getEditedCustomerName(editedOrder.getCustomerName());
            invalidCustomerName = service.validateCustomerName(customerName);
            if(invalidCustomerName){
                view.displayInvalidValueError("Customer Name");
                view.displayCustomerNameRules();
            }
        }while(invalidCustomerName);
        
        boolean invalidState;
        String state = "";
        do{
            state = view.getEditedState(editedOrder.getState());
            invalidState = service.validateState(state);
            if(invalidState){
                view.displayInvalidValueError("State");
                view.displayStateRules();
            }
        }while(invalidState);
        
        boolean invalidProductType;
        String productType = "";
        do{
            productType = view.getEditedProductType(editedOrder.getProductType(), service.getAllProduct());
            invalidProductType = service.validateProductType(productType);
            if(invalidProductType){
                view.displayInvalidValueError("Product Type");
                view.displayProductTypeRules();
            }
        }while(invalidProductType);
        
        boolean invalidArea;
        BigDecimal area;
        do{
            area = view.getEditedArea(editedOrder.getArea());
            invalidArea = service.validateArea(area);
            if(invalidArea){
                view.displayInvalidValueError("Area");
                view.displayAreaRules();
            }
        }while(invalidArea);
        
        service.calculateOrderTotal(editedOrder);
        
        view.displayOrderSummary(editedOrder);
        boolean save = view.getConfirmAddOrder();
        if(save){
            editedOrder.setOrderDate(orderDate);
            editedOrder.setCustomerName(customerName);
            editedOrder.setState(state);
            editedOrder.setProductType(productType);
            editedOrder.setArea(area);
            
            service.editOrder(orderNumber, editedOrder);
            view.displayConfrimOrderSaved(orderNumber);
        }else{
            view.displayConfirmOrderDiscard();
        }
    }
    
    private void removeOrder(){
        view.removeOrderBanner();
        LocalDate orderDate = getOrderDateChoice();
        List<Order> ordersByDate = getOrdersOfDateChoice(orderDate);
        
        boolean noOrdersOfDateFound = service.ordersForDateNotFound(ordersByDate);
        if(noOrdersOfDateFound){
            view.displayEmptyDisplayOrdersError();
        } else{
            Integer orderNumber = getOrderNumberChoice();
            Order foundOrder = getOrderOfNumberChoice(orderNumber, ordersByDate);
            
            if(foundOrder == null){
                view.displayNoOrderFoundError();
            }else{
                view.displayOrderSummary(foundOrder);
                if(view.getConfirmRemoval(foundOrder)){
                    view.displayRemovedOrderSuccess(service.removeOrder(orderNumber));
                }else{
                    view.displayOrderNotRemoved();
                }
            }
        }
        view.returnToMainMenu();
    }
    
    private void saveOrders(){
        view.saveOrdersBanner();
        switch(service.getModeSetting()){
            case PRODUCTION:
                if(view.savePrompt()){
                    try {
                        service.saveAllOrders();
                        view.displaySaveSuccess();
                    } catch (FilePersistenceException ex) {
                        view.displaySaveError();
                    }
                }else{
                    view.displayUnsavedSuccess();
                }
                break;
            case TRAINING:
                view.displaySaveUnavailible();
                break;
        }
        view.returnToMainMenu();
    }
    
    private void exit(){
        view.exitBanner();
        switch(service.getModeSetting()){
            case PRODUCTION:
                if(view.savePrompt()){
                    try {
                        service.saveAllOrders();
                        view.displaySaveSuccess();
                    } catch (FilePersistenceException ex) {
                        view.displaySaveError();
                    }
                }else{
                    view.displayUnsavedSuccess();
                }
                break;
            case TRAINING:
                break;
        }
        view.displayExitMessage();
    }
    
    private void unknownCommand(){
        view.unknownCommand();
        view.returnToMainMenu();
    }
}
