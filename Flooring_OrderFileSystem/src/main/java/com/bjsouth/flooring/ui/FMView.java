/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.ui;

import com.bjsouth.flooring.dto.Order;
import com.bjsouth.flooring.dto.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

/**
 *
 * @author Bree
 */
public class FMView {
    UserIO io;
    
    public FMView(UserIO io){
        this.io = io;
    }
    
    public int printMainMenuSelection(){
        io.print("<< Flooring Program >>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Save Current Work");
        io.print("6. Exit");
        
        return io.readInt("", 1, 6);
    }
    
    public void displayOrdersBanner(){
        io.print("<< Display Orders >>");
    }
    
    public void displayOrders(List<Order> ordersByOrderDate, LocalDate dateChoice){
        String dateFormatted = dateChoice.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        io.print("Showing Orders for Delivery Date "+dateFormatted+":");
        io.print("");
        for(Order o : ordersByOrderDate){
            io.print("Order Number: "+o.getOrderNumber());
            io.print("   Customer: "+o.getCustomerName());
            io.print("   State: "+o.getState());
            io.print("   Product Type: "+o.getProductType());
            io.print("   Area: "+o.getArea()+" Sqft");
            io.print("   Total: $"+o.getTotal());
            io.print("");
        }
    }
    
    public String getDateChoice(){
        return io.readString("Order Date in MM-DD-YYYY format:");
    }
    
    public Integer getOrderNumberChoice(){
        return io.readInt("Order Number:");
    }
    
    public String getCustomerName(){
        return io.readString("Customer Name:");
    }
    
    public String getState(){
        return io.readString("State:");
    }
    
    public String getProductType(List<Product> productList){
        for(Product p : productList){
            io.print(p.getProductType());
        }
        return io.readString("Product Type:");
    }
    
    public BigDecimal getArea(){
        return new BigDecimal(io.readString("Area:"));
    }
    
    public void displayOrderSummary(Order order){
        io.print("Order Summary:");
        io.print("   Order Date: "+order.getOrderDate());
        io.print("   Customer Name: "+order.getCustomerName());
        io.print("   State: "+order.getState());
        io.print("   Tax Rate: "+order.getTaxRate()+"%");
        io.print("   Product Type: "+order.getProductType());
        io.print("   Area: "+order.getArea()+" Sqft");
        io.print("   Cost per Square Foot: $"+order.getCostPerSquareFoot());
        io.print("   Labor Cost per Square Foot: $"+order.getLaborCostPerSquareFoot());
        io.print("   Material Cost: $"+order.getMaterialCost());
        io.print("   Labor Cost: $"+order.getLaborCost());
        io.print("   Tax: $"+order.getTax());
        io.print("   Total: $"+order.getTotal());
        
    }
    
    public boolean getConfirmAddOrder(){
        String answer = io.readString("Will you add this order?");
        boolean unclearAnswer = false;
        do{
            if(answer.equalsIgnoreCase("yes")){
                unclearAnswer = false;
                return true;
            } else if(answer.equalsIgnoreCase("no")){
                unclearAnswer = false;
                return false;
            } else{
                io.print("Please enter Yes or No.");
                unclearAnswer = true;
            }
        }while(unclearAnswer);
        return true;
    }
    
    public void displayConfrimOrderSaved(Integer orderNumber){
        io.print("This Order has been saved under the Order Number "+orderNumber);
    }
    
    public void displayConfirmOrderDiscard(){
        io.print("This Order has been discarded.");
    }
    
    public void editOrderBanner(){
        io.print("<< Edit Order >>");
    }
    
    public void removeOrderBanner(){
        io.print("<< Remove Order >>");
    }
    
    public void saveOrdersBanner(){
        io.print("<< Save Orders >>");
    }
    
    public void exitBanner(){
        io.print("<< Exit >>");
    }
    
    public void unknownCommand(){
        io.print("Unknown Command, Please retry again.");
    }

    public void displayNoDataError() {
        io.print("There seems to be no accessible files in the Data or Orders folders.");
        io.print("This system will shut down in response.");
        io.print("Please see IT for additional help.");
    }
    
    public void displaySaveError(){
        io.print("Error: there was an issue saving changes.");
        io.print("This system cannot save changes at this time.");
        io.print("Please see IT for additional help.");
    }

    public void displayInvalidValueError(String property){
        io.print("The "+property+" you entered is invalid.");
        io.print("Please re-enter it based on the following rules:");
    }
    
    public void displayNewOrderDateRules(){
        io.print("   Order Dates must be a real date using the specified format, be due after today, and cannot be blank.");
        io.print("");
    }
    
    public void displaySearchOrderDateRules(){
        io.print("   Order Dates must be a real date using the specified format and cannot be blank.");
        io.print("");
    }
    
    public void displayCustomerNameRules(){
        io.print("   Customer Names must contain only letters, number, commas, and periods in them and cannot be blank.");
        io.print("");
    }

    public void displayStateRules(){
        io.print("   States must be of those caetered to by this company.");
        io.print("");
    }
    
    public void displayProductTypeRules(){
        io.print("   Product Types must be of those provided by this company.");
        io.print("");
    }
    
    public void displayAreaRules(){
        io.print("   Area must be a positive number with the minimum amount being 100 Sqft.");
        io.print("");
    }
    
    public void displayEmptyDisplayOrdersError(){
        io.print("There are no orders for this date.");
    }
    
    public void returnToMainMenu(){
        io.readString("Press Enter to continue.");
    }

    public void displayTrainingBanner() {
        io.print("<< Training Mode >>");
    }

    public boolean savePrompt() {
        String answer = io.readString("Will you save changes made to Orders?");
        boolean unclearAnswer = false;
        do{
            if(answer.equalsIgnoreCase("yes")){
                unclearAnswer = false;
                return true;
            } else if(answer.equalsIgnoreCase("no")){
                unclearAnswer = false;
                return false;
            } else{
                io.print("Please enter Yes or No.");
                unclearAnswer = true;
            }
        }while(unclearAnswer);
        return true;
    }

    public void displaySaveSuccess() {
        io.print("All changes were saved successfully.");
    }

    public void displayUnsavedSuccess() {
        io.print("No changes will be saved.");
    }

    public void displaySaveUnavailible() {
        io.print("Saving is unavailble while in Training Mode.");
    }

    public void displayExitMessage() {
        io.print("Thank you for using TSG's Order File System.");
        io.print("Shutting down...");
    }

    public Integer getNumberChoice() {
        return io.readInt("Order Number:");
    }

    public void displayOrderNumberRules() {
        io.print("   Order Number must be a number.");
    }

    public void displayNoOrderFoundError() {
        io.print("No Order can be found with that number.");
    }

    public void displayMissingFunction() {
        io.print("This fucntion has yet to be fully implemented due to other");
    }

    public boolean getConfirmRemoval(Order foundOrder) {
        String answer = io.readString("Are you sure you want to remove Order numbered "+foundOrder.getOrderNumber()+"?");
        boolean unclearAnswer = false;
        do{
            if(answer.equalsIgnoreCase("yes")){
                unclearAnswer = false;
                return true;
            } else if(answer.equalsIgnoreCase("no")){
                unclearAnswer = false;
                return false;
            } else{
                io.print("Please enter Yes or No.");
                unclearAnswer = true;
            }
        }while(unclearAnswer);
        return true;
    }

    public void displayRemovedOrderSuccess(Order removedOrder) {
        io.print("The Order numbered "+removedOrder.getOrderNumber()+" for "+removedOrder.getCustomerName()+" has been successfully removed.");
    }

    public void displayOrderNotRemoved() {
        io.print("The Order was not removed.");
    }
    
    public String getEditedOrderDate(LocalDate oldOrderDate){
        String answer = io.readString("Enter Order Date ("+oldOrderDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))+"): ");
        if(answer.isBlank() || answer.isEmpty()){
            return oldOrderDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        }else{
            return answer;
        }
    }
    
    public String getEditedCustomerName(String oldCustomerName){
        String answer = io.readString("Enter Customer Name ("+oldCustomerName+"): ");
        if(answer.isBlank() || answer.isEmpty()){
            return oldCustomerName;
        }else{
            return answer;
        }
    }
    
    public String getEditedState(String oldState){
        String answer = io.readString("Enter State ("+oldState+"): ");
        if(answer.isBlank() || answer.isEmpty()){
            return oldState;
        }else{
            return answer;
        }
    }
    
    public String getEditedProductType(String oldProductType, List<Product> productList){
        for(Product p : productList){
            io.print(p.getProductType());
        }
        String answer = io.readString("Enter Product Type ("+oldProductType+"): ");
        if(answer.isBlank() || answer.isEmpty()){
            return oldProductType;
        }else{
            return answer;
        }
    }
    
    public BigDecimal getEditedArea(BigDecimal oldArea){
        BigDecimal answer;
        String answerString = io.readString("Enter Area ("+oldArea+"): ");
        if(answerString.isBlank() || answerString.isEmpty()){
            return oldArea;
        }else{
            return answer = new BigDecimal(answerString);
        }
    }
}
