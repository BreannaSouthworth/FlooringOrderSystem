/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.dao;

import com.bjsouth.flooring.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author Bree
 */
public class OrderDAOImpl implements OrderDAO{
    private Map<Integer, Order> orderMap = new TreeMap<>();
    private static final String DELIMITER = ",";

    public OrderDAOImpl() {}

    @Override
    public Order createOrder(Integer orderNumber, Order order) {
        return orderMap.put(orderNumber, order);
    }

    @Override
    public Order readSignleOrder(Integer orderNumber) {
        return orderMap.get(orderNumber);
    }

    @Override
    public List<Order> readAllOrders() {
        List<Order> orderList = new ArrayList<>(orderMap.values());
        return orderList;
    }

    @Override
    public void updateOrder(Integer orderNumber, Order newOrder) {
        orderMap.replace(orderNumber, newOrder);
    }

    @Override
    public Order deleteOrder(Integer orderNumber) {
        return orderMap.remove(orderNumber);
    }
    
    private Order unmarshallOrder(String orderLine){
        Order orderFromFile = new Order();
        String[] splitLineProperties = orderLine.split(DELIMITER);
        
        String orderNumberAsString = splitLineProperties[0];
        orderFromFile.setCustomerName(splitLineProperties[1]);
        orderFromFile.setState(splitLineProperties[2]);
        String taxRateAsString = splitLineProperties[3];
        orderFromFile.setProductType(splitLineProperties[4]);
        String areaAsString = splitLineProperties[5];
        String costPerSquareFootAsString = splitLineProperties[6];
        String laborCostPerSquareFootAsString = splitLineProperties[7];
        String materialCostAsString = splitLineProperties[8];
        String laborCostAsString = splitLineProperties[9];
        String taxAsString = splitLineProperties[10];
        String totalAsString = splitLineProperties[11];
        
        Integer orderNumber = Integer.parseInt(orderNumberAsString);
        orderFromFile.setOrderNumber(orderNumber);
        
        BigDecimal taxRate = new BigDecimal(taxRateAsString);
        orderFromFile.setTaxRate(taxRate);
        
        BigDecimal area = new BigDecimal(areaAsString);
        orderFromFile.setArea(area);
        
        BigDecimal costPerSquareFoot = new BigDecimal(costPerSquareFootAsString);
        orderFromFile.setCostPerSquareFoot(costPerSquareFoot);
        
        BigDecimal laborCostPerSquareFoot = new BigDecimal(laborCostPerSquareFootAsString);
        orderFromFile.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
        
        BigDecimal materialCost = new BigDecimal(materialCostAsString);
        orderFromFile.setMaterialCost(materialCost);
        
        BigDecimal laborCost = new BigDecimal(laborCostAsString);
        orderFromFile.setLaborCost(laborCost);
        
        BigDecimal tax = new BigDecimal(taxAsString);
        orderFromFile.setTax(tax);
        
        BigDecimal total = new BigDecimal(totalAsString);
        orderFromFile.setTotal(total);
        
        return orderFromFile;
    }
    
    @Override
    public void loadOrders(String filedate) throws FilePersistenceException{
        try{
            FileReader findFile = new FileReader("Orders/Orders_"+filedate+".txt");
            BufferedReader getText = new BufferedReader(findFile);
            Scanner scanner = new Scanner(getText);
            
            LocalDate orderDate = LocalDate.parse(filedate, DateTimeFormatter.ofPattern("MMddyyyy"));
            while(scanner.hasNextLine()){
                String orderLine = scanner.nextLine();
                Order fromLine = this.unmarshallOrder(orderLine);
                fromLine.setOrderDate(orderDate);
                this.orderMap.put(fromLine.getOrderNumber(), fromLine);
            }
            scanner.close();
        }catch(FileNotFoundException e){
            throw new FilePersistenceException("Missing Order File", e);
        }
    }
    
    private String marshallOrder(Order order){
        String orderString = "";
        
        orderString = orderString + order.getOrderNumber()+this.DELIMITER;
        orderString = orderString + order.getCustomerName()+this.DELIMITER;
        orderString = orderString + order.getState()+this.DELIMITER;
        orderString = orderString + order.getTaxRate()+this.DELIMITER;
        orderString = orderString + order.getProductType()+this.DELIMITER;
        orderString = orderString + order.getArea()+this.DELIMITER;
        orderString = orderString + order.getCostPerSquareFoot()+this.DELIMITER;
        orderString = orderString + order.getLaborCostPerSquareFoot()+this.DELIMITER;
        orderString = orderString + order.getMaterialCost()+this.DELIMITER;
        orderString = orderString + order.getLaborCost()+this.DELIMITER;
        orderString = orderString + order.getTax()+this.DELIMITER;
        orderString = orderString + order.getTotal()+this.DELIMITER;
        
        return orderString;
    }
    
    @Override
    public void saveOrders(String filename, List<Order> ordersToSave) throws FilePersistenceException{
        PrintWriter out;
        
        try{
            out = new PrintWriter(new FileWriter(filename));
        }catch(IOException e){
            throw new FilePersistenceException("", e);
        }
        
        String orderAsText;
        for(Order o : ordersToSave){
            orderAsText = marshallOrder(o);
            out.println(orderAsText);
            out.flush();
        }
        out.close();
    }
}
