/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.dao;

import com.bjsouth.flooring.dto.Order;
import java.util.List;

/**
 *
 * @author Bree
 */
public interface OrderDAO {
    Order createOrder(Integer orderNumber, Order order);
    Order readSignleOrder(Integer orderNumber);
    List<Order> readAllOrders();
    void updateOrder(Integer orderNumber, Order order);
    Order deleteOrder(Integer orderNumber);
    
    public void loadOrders(String filedate) throws FilePersistenceException;
    public void saveOrders(String filename, List<Order> ordersToSave) throws FilePersistenceException;
}
