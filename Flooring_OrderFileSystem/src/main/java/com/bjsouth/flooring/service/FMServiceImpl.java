/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.service;

import com.bjsouth.flooring.dao.FilePersistenceException;
import com.bjsouth.flooring.dao.ModeDAO;
import com.bjsouth.flooring.dao.OrderDAO;
import com.bjsouth.flooring.dao.ProductDAO;
import com.bjsouth.flooring.dao.TaxDAO;
import com.bjsouth.flooring.dto.Mode;
import com.bjsouth.flooring.dto.ModeSetting;
import com.bjsouth.flooring.dto.Order;
import com.bjsouth.flooring.dto.Product;
import com.bjsouth.flooring.dto.Tax;
import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


/**
 *
 * @author Bree
 */
public class FMServiceImpl implements FMService{
    private OrderDAO orderDao;
    private TaxDAO taxDao;
    private ProductDAO productDao;
    private ModeDAO modeDao;
    
    public FMServiceImpl(OrderDAO orderDao, TaxDAO taxDao, ProductDAO productDao, ModeDAO modeDao){
        this.orderDao = orderDao;
        this.taxDao = taxDao;
        this.productDao = productDao;
        this.modeDao = modeDao;
    }
    
    // order pass-throughs
    @Override
    public Order addOrder(Integer orderNumber, Order newOrder){
        return orderDao.createOrder(orderNumber, newOrder);
    }
    
    @Override
    public Order getOneOrder(Integer orderNumber) {
        return orderDao.readSignleOrder(orderNumber);
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orderList = orderDao.readAllOrders();
        return orderList;
    }
    
    @Override
    public List<Order> getOrdersByOrderDate(LocalDate orderDate){
        List<Order> matchingOrders = new ArrayList<>();
        for(Order o : this.getAllOrders()){
            if(o.getOrderDate().equals(orderDate)){
                matchingOrders.add(o);
            }
        }
        return matchingOrders;
    }

    @Override
    public void editOrder(Integer orderNumber, Order newOrder) {
        orderDao.updateOrder(orderNumber, newOrder);
    }

    @Override
    public Order removeOrder(Integer orderNumber) {
        return orderDao.deleteOrder(orderNumber);
    }
    
    // tax pass-through
    @Override
    public Tax getOneTax(String stateAbbreviation) {
        return taxDao.readSignleTax(stateAbbreviation);
    }
    
    // product pass-throughs
    @Override
    public Product getOneProduct(String productType) {
        return productDao.readSingleProduct(productType);
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> productList = productDao.readAllProducts();
        return productList;
    }
    
    // mode pass through
    @Override
    public Mode getModeSetting(){
        ModeSetting mode = modeDao.readModeSetting(1);
        return mode.getMode();
    }
    
    @Override
    public void calculateOrderTotal(Order order){
        String state = order.getState();
        for(Tax t : taxDao.readAllTaxes()){
            if(t.getStateAbbreviation().equalsIgnoreCase(state)){
                order.setTaxRate(t.getTaxRate());
            }
        }
        
        String productType = order.getProductType();
        for(Product p : productDao.readAllProducts()){
            if(p.getProductType().equalsIgnoreCase(productType)){
                order.setCostPerSquareFoot(p.getCostPerSquareFoot());
                order.setLaborCostPerSquareFoot(p.getLaborCostPerSquareFoot());
            }
        }
        
        MathContext mc = new MathContext(2);
        order.setMaterialCost(order.getArea().multiply(order.getCostPerSquareFoot()));
        order.setLaborCost(order.getArea().multiply(order.getLaborCostPerSquareFoot()));
        order.setTax((order.getMaterialCost().add(order.getLaborCost())).multiply(order.getTaxRate().divide(new BigDecimal("100.00"))));
        order.setTotal(order.getMaterialCost().add(order.getLaborCost().add(order.getTax())));
    }
    
    @Override
    public void loadData() throws FilePersistenceException{
        taxDao.loadTaxes();
        productDao.loadProducts();
        modeDao.loadMode();
    }
    
    @Override
    public void loadAllOrderFiles() throws FilePersistenceException{
        File folder = new File("Orders/");
        File[] fileArray = folder.listFiles();
        
        for(File file : fileArray){
            String filename = file.getName();
            String filedate = filename.substring(7, 15);
            orderDao.loadOrders(filedate);
        }
    }
    
    @Override
    public void saveAllOrders() throws FilePersistenceException{
        File directory = new File("Orders/");
        for(File file : directory.listFiles()){
            if(!file.isDirectory())
                file.delete();
        }
        
        Set<String> uniqueDates = new HashSet<>();
        for(Order o : getAllOrders()){
            LocalDate orderDate = o.getOrderDate();
            String date = orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy"));
            uniqueDates.add(date);
        }
        
        Map<String, List> mapOfLists = new HashMap<>();
        for(String s : uniqueDates){
            List<Order> ordersList = new ArrayList<>();
            mapOfLists.put(s, ordersList);
        }
        
        for(Order o : getAllOrders()){
            LocalDate orderDate = o.getOrderDate();
            String date = orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy"));
            for(String s : uniqueDates){
                if(date.equals(s)){
                    List<Order> orderList = mapOfLists.get(s);
                    orderList.add(o);
                }
            }  
        }
        
        for(String s : uniqueDates){
            String filename = "Orders/Orders_"+s+".txt";
            orderDao.saveOrders(filename, mapOfLists.get(s));
        }
    }
    
    @Override
    public Integer nextOrderNumber(){
        Integer newOrderNumber = 1;
        for(Order o : this.getAllOrders()){
            Integer comparedON = o.getOrderNumber();
            if(comparedON.compareTo(newOrderNumber) >= 0){
                newOrderNumber = comparedON;
            }
        }
        newOrderNumber++;
        return newOrderNumber;
    }
    
    @Override
    public boolean validateNewOrderDate(String orderDateString){
        boolean answer = this.validateRealDate(orderDateString);
        LocalDate orderDate = LocalDate.parse(orderDateString, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        LocalDate today = LocalDate.now();
        return answer || orderDateString.isBlank() || orderDateString.isEmpty() || today.compareTo(orderDate) >= 0;
    }
    
    @Override
    public boolean validateCustomerName(String customerName){
        boolean patternMatch = Pattern.matches("^[a-zA-Z0-9.,\\p{Blank}]+$", customerName);
        return customerName.isBlank() || customerName.isEmpty() || !patternMatch;
    }
    
    @Override
    public boolean validateState(String state){
        List<Tax> matchingTax = new ArrayList<>();
        for(Tax t :taxDao.readAllTaxes()){
            String stateAbb = t.getStateAbbreviation();
            String stateName = t.getStateName();
            if(stateAbb.equalsIgnoreCase(state) || stateName.equalsIgnoreCase(state)){
                matchingTax.add(t);
            }
        }
        return matchingTax.isEmpty();
    }
    
    @Override
    public boolean validateProductType(String productType){
        List<Product> matchingProduct = new ArrayList<>();
        for(Product p : productDao.readAllProducts()){
            String product = p.getProductType();
            if(product.equalsIgnoreCase(productType)){
                matchingProduct.add(p);
            }
        }
        return matchingProduct.isEmpty();
    }
    
    @Override
    public boolean validateArea(BigDecimal area){
        return area.compareTo(new BigDecimal("100.00")) < 0;
    }
    
    @Override
    public boolean ordersForDateNotFound(List<Order> foundOrders){
        if(foundOrders.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public boolean validateRealDate(String orderDateString){
        try{
            LocalDate.parse(orderDateString, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        }catch(DateTimeParseException e){
            return true;
        }
        return false;
    }
}
