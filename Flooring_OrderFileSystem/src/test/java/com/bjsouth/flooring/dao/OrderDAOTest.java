/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.dao;

import com.bjsouth.flooring.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Bree
 */
public class OrderDAOTest {
    OrderDAO orderDao;
    
    public OrderDAOTest(){
        orderDao = new OrderDAOImpl();
    }

    @Test
    public void addOneGetOneTest(){
       Order alpha = new Order(1, LocalDate.parse("04-08-2019", DateTimeFormatter.ofPattern("MM-dd-yyyy")), 
               "Breanna Southworth", "KY", "Tile", new BigDecimal("120.00"));

       orderDao.createOrder(alpha.getOrderNumber(), alpha);
       Order retrievedOrder = orderDao.readSignleOrder(alpha.getOrderNumber());

       Assertions.assertEquals(alpha, retrievedOrder, "The orders should match.");
    }

    @Test
    public void addTwoGetAllTest(){
        Order alpha = new Order(1, LocalDate.parse("04-08-2019", DateTimeFormatter.ofPattern("MM-dd-yyyy")), 
               "Breanna Southworth", "KY", "Tile", new BigDecimal("120.00"));
        Order bravo = new Order(2, LocalDate.parse("09-29-2019", DateTimeFormatter.ofPattern("MM-dd-yyyy")),
                "Sophia Neidig", "KY", "Wood", new BigDecimal("300.00"));
        
        orderDao.createOrder(alpha.getOrderNumber(), alpha);
        orderDao.createOrder(bravo.getOrderNumber(), bravo);
        List<Order> orderList = orderDao.readAllOrders();
        
        Assertions.assertNotNull(orderList, "List should not be null.");
        Assertions.assertEquals(2, orderList.size(), "There should be two orders in list.");
        Assertions.assertTrue(orderList.contains(alpha), "Order list should have the first order stored.");
        Assertions.assertTrue(orderList.contains(bravo), "Order list should have the second order stored.");
    }
    
    @Test
    public void addEditTest(){
        Order alpha = new Order(1, LocalDate.parse("04-08-2019", DateTimeFormatter.ofPattern("MM-dd-yyyy")), 
               "Breanna Southworth", "KY", "Tile", new BigDecimal("120.00"));
        Order bravo = new Order(1, LocalDate.parse("09-29-2019", DateTimeFormatter.ofPattern("MM-dd-yyyy")),
                "Sophia Neidig", "KY", "Wood", new BigDecimal("300.00"));
        
        orderDao.createOrder(alpha.getOrderNumber(), alpha);
        orderDao.updateOrder(alpha.getOrderNumber(), bravo);
        
        Order retrievedOrder = orderDao.readSignleOrder(1);
        List<Order> orderList = orderDao.readAllOrders();
        
        Assertions.assertEquals(bravo, retrievedOrder, "Order should have been replaced by second.");
        Assertions.assertEquals(1, orderList.size(), "There should only be one order.");
        Assertions.assertTrue(orderList.contains(bravo), "Only order left should be the second one.");
    }
    
    @Test
    public void addRemoveTest(){
        Order alpha = new Order(1, LocalDate.parse("04-08-2019", DateTimeFormatter.ofPattern("MM-dd-yyyy")), 
               "Breanna Southworth", "KY", "Tile", new BigDecimal("120.00"));
        
        orderDao.createOrder(1, alpha);
        Order removedOrder = orderDao.deleteOrder(1);
        Order retrievedOrder = orderDao.readSignleOrder(1);
        List<Order> orderList = orderDao.readAllOrders();
        
        Assertions.assertEquals(alpha, removedOrder, "Stored Order and removed order should match");
        Assertions.assertNull(retrievedOrder, "Order should no longer be in DAO");
        Assertions.assertEquals(0, orderList.size(), "Order list should be empty");
    }
}

