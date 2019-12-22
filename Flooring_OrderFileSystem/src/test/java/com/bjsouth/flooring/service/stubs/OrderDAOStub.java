/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.service.stubs;

import com.bjsouth.flooring.dao.FilePersistenceException;
import com.bjsouth.flooring.dao.OrderDAO;
import com.bjsouth.flooring.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bree
 */
public class OrderDAOStub implements OrderDAO{
    public Order alpha;
    public Order bravo;
    public Order charlie;
    
    public OrderDAOStub(){
        this.alpha = new Order(1, LocalDate.parse("04082017", DateTimeFormatter.ofPattern("MMddyyyy")), "Mystery Inc.", "KY", "Wood", new BigDecimal("130.00"));
        this.bravo = new Order(2, LocalDate.parse("11192019", DateTimeFormatter.ofPattern("MMddyyyy")), "Santa", "IN", "Carpet", new BigDecimal("200.50"));
        this.charlie = new Order(5, LocalDate.parse("11192019", DateTimeFormatter.ofPattern("MMddyyyy")), "Cean Phelps", "KY", "Carpet", new BigDecimal("450.75"));
    }

    @Override
    public Order createOrder(Integer orderNumber, Order order) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order readSignleOrder(Integer orderNumber) {
        if(orderNumber == alpha.getOrderNumber()){
            return alpha;
        }else if(orderNumber == bravo.getOrderNumber()){
            return bravo;
        }else if(orderNumber == charlie.getOrderNumber()){
            return charlie;
        }else{
            return null;
        }
        
    }

    @Override
    public List<Order> readAllOrders() {
        List<Order> stubOrders = new ArrayList<>();
        stubOrders.add(alpha);
        stubOrders.add(bravo);
        stubOrders.add(charlie);
        return stubOrders;
    }

    @Override
    public void updateOrder(Integer orderNumber, Order order) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order deleteOrder(Integer orderNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadOrders(String filedate) throws FilePersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveOrders(String filename, List<Order> ordersToSave) throws FilePersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
