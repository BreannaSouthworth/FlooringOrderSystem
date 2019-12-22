/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.service;

import com.bjsouth.flooring.dao.FilePersistenceException;
import com.bjsouth.flooring.dto.Mode;
import com.bjsouth.flooring.dto.Order;
import com.bjsouth.flooring.dto.Product;
import com.bjsouth.flooring.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Bree
 */
public interface FMService {
    public Order addOrder(Integer orderNumber, Order newOrder);
    public Order getOneOrder(Integer orderNumber);
    public List<Order> getAllOrders();
    public List<Order> getOrdersByOrderDate(LocalDate orderDate);
    public void editOrder(Integer orderNumber, Order newOrder);
    public Order removeOrder(Integer orderNumber);
    
    public Tax getOneTax(String stateAbbreviation);
    
    public Product getOneProduct(String productType);
    public List<Product> getAllProduct();
    
    public Mode getModeSetting();
    
    public void calculateOrderTotal(Order order);
    public void loadData() throws FilePersistenceException;
    public void loadAllOrderFiles() throws FilePersistenceException;
    public void saveAllOrders() throws FilePersistenceException;
    public Integer nextOrderNumber();
    public boolean validateNewOrderDate(String orderDateString);
    public boolean validateCustomerName(String customerName);
    public boolean validateState(String state);
    public boolean validateProductType(String productType);
    public boolean validateArea(BigDecimal area);
    public boolean ordersForDateNotFound(List<Order> foundOrders);
    public boolean validateRealDate(String orderDateString);
}
