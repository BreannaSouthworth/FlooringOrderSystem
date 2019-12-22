/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.service;

import com.bjsouth.flooring.dto.Order;
import com.bjsouth.flooring.dto.Product;
import com.bjsouth.flooring.dto.Tax;
import com.bjsouth.flooring.service.stubs.ModeDAOStub;
import com.bjsouth.flooring.service.stubs.OrderDAOStub;
import com.bjsouth.flooring.service.stubs.ProductDAOStub;
import com.bjsouth.flooring.service.stubs.TaxDAOStub;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Bree
 */
public class FMServiceImplTest {
    FMService testService;
    Order orderA;
    Order orderB;
    Order orderC;
    Tax stateA;
    Tax stateB;
    Product productA;
    Product productB;
    
    public FMServiceImplTest() {
        orderA = new Order(1, LocalDate.parse("04082017", DateTimeFormatter.ofPattern("MMddyyyy")), "Mystery Inc.", "KY", "Wood", new BigDecimal("130.00"));
        orderB = new Order(2, LocalDate.parse("11192019", DateTimeFormatter.ofPattern("MMddyyyy")), "Santa", "IN", "Carpet", new BigDecimal("200.50"));
        orderC = new Order(5, LocalDate.parse("11192019", DateTimeFormatter.ofPattern("MMddyyyy")), "Cean Phelps", "KY", "Carpet", new BigDecimal("450.75"));
        
        stateA = new Tax("KY", "Kentucky", new BigDecimal("6.00"));
        stateB = new Tax("IN", "Indiana", new BigDecimal("7.00"));
        
        productA = new Product("Wood", new BigDecimal("2.25"), new BigDecimal("5.10"));
        productB = new Product("Carpet", new BigDecimal("3.50"), new BigDecimal("7.45"));
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("TestApplicationContext.xml");
        testService = ctx.getBean("service", FMService.class);
    }
    
    @Test
    public void getOrdersBy04082019Date(){
        //ARRANGE
        //ACT
        List<Order> ordersByDate = testService.getOrdersByOrderDate(LocalDate.parse("04082017", DateTimeFormatter.ofPattern("MMddyyyy")));
        
        //ASSERT
        Assertions.assertNotNull(ordersByDate, "List should not be null");
        Assertions.assertEquals(1, ordersByDate.size(), "There should only be one order in list");
        Assertions.assertTrue(ordersByDate.contains(orderA), "The order in this list should be order A");
    }
    
    @Test
    public void getOrdersBy11192019Date(){
        //ARRANGE
        //ACT
        List<Order> ordersByDate = testService.getOrdersByOrderDate(LocalDate.parse("11192019", DateTimeFormatter.ofPattern("MMddyyyy")));
        
        //ASSERT
        Assertions.assertNotNull(ordersByDate, "List should not be null");
        Assertions.assertEquals(2, ordersByDate.size(), "There should be two orders in this list");
        Assertions.assertTrue(ordersByDate.contains(orderB), "Order B should be in this list");
        Assertions.assertTrue(ordersByDate.contains(orderC), "Orcer C should be in this list");
    }

    @Test
    public void calculateOrderATotalTest() {
        //ARRANGE
        //ACT
        testService.calculateOrderTotal(orderA);
        
        //ASSERT
        BigDecimal expectedTaxRate = new BigDecimal("6.00");
        BigDecimal expectedCPSF = new BigDecimal("2.25");
        BigDecimal expectedLCPSF = new BigDecimal("5.10");
        
        Assertions.assertEquals(expectedTaxRate, orderA.getTaxRate(), "The order tax rate should match the state tax rate");
        Assertions.assertEquals(expectedCPSF, orderA.getCostPerSquareFoot(), "The order CPSF should match the product CPSF");
        Assertions.assertEquals(expectedLCPSF, orderA.getLaborCostPerSquareFoot(), "The order LCPSF should match the product LCPSF");
        
        BigDecimal expectedMaterialCost = new BigDecimal("292.5000");
        BigDecimal expectedLaborCost = new BigDecimal("663.0000");
        BigDecimal expectedTax = new BigDecimal("57.330000");
        BigDecimal expectedTotal = new BigDecimal("1012.830000");
        
        Assertions.assertEquals(expectedMaterialCost, orderA.getMaterialCost(), "The material cost should be 130.00 * 120.00 equaling 15600.00");
        Assertions.assertEquals(expectedLaborCost, orderA.getLaborCost(), "The labor cost should be 130.00 * 35.00 equalling 4550.00");
        Assertions.assertEquals(expectedTax, orderA.getTax(), "The tax should be (15600.00 + 4550.00) * (6.00 / 100.00) equalling 1209.00");
        Assertions.assertEquals(expectedTotal, orderA.getTotal(), "The total should be 15600.00 + 4550.00 + 1209.00 equalling 21359.00");
    }
    
    @Test
    public void calculateOrderBTotalTest() {
        //ARRANGE
        //ACT
        testService.calculateOrderTotal(orderB);
        
        //ASSERT
        BigDecimal expectedTaxRate = new BigDecimal("7.00");
        BigDecimal expectedCPSF = new BigDecimal("3.50");
        BigDecimal expectedLCPSF = new BigDecimal("7.45");
        
        Assertions.assertEquals(expectedTaxRate, orderB.getTaxRate(), "The order tax rate should match the state tax rate");
        Assertions.assertEquals(expectedCPSF, orderB.getCostPerSquareFoot(), "The order CPSF should match the product CPSF");
        Assertions.assertEquals(expectedLCPSF, orderB.getLaborCostPerSquareFoot(), "The order LCPSF should match the product LCPSF");
        
        BigDecimal expectedMaterialCost = new BigDecimal("701.7500");
        BigDecimal expectedLaborCost = new BigDecimal("1493.7250");
        BigDecimal expectedTax = new BigDecimal("153.683250");
        BigDecimal expectedTotal = new BigDecimal("2349.158250");
        
        Assertions.assertEquals(expectedMaterialCost, orderB.getMaterialCost(), "The material cost should be 701.7500");
        Assertions.assertEquals(expectedLaborCost, orderB.getLaborCost(), "The labor cost should be 1493.7250");
        Assertions.assertEquals(expectedTax, orderB.getTax(), "The tax should be 152.683250");
        Assertions.assertEquals(expectedTotal, orderB.getTotal(), "The total should be 2349.158250");
    }
    
    @Test
    public void calculateOrderCTotalTest() {
        //ARRANGE
        //ACT
        testService.calculateOrderTotal(orderC);
        
        //ASSERT
        BigDecimal expectedTaxRate = new BigDecimal("6.00");
        BigDecimal expectedCPSF = new BigDecimal("3.50");
        BigDecimal expectedLCPSF = new BigDecimal("7.45");
        
        Assertions.assertEquals(expectedTaxRate, orderC.getTaxRate(), "The order tax rate should match the state tax rate");
        Assertions.assertEquals(expectedCPSF, orderC.getCostPerSquareFoot(), "The order CPSF should match the product CPSF");
        Assertions.assertEquals(expectedLCPSF, orderC.getLaborCostPerSquareFoot(), "The order LCPSF should match the product LCPSF");
        
        BigDecimal expectedMaterialCost = new BigDecimal("1577.6250");
        BigDecimal expectedLaborCost = new BigDecimal("3358.0875");
        BigDecimal expectedTax = new BigDecimal("296.142750");
        BigDecimal expectedTotal = new BigDecimal("5231.855250");
        
        Assertions.assertEquals(expectedMaterialCost, orderC.getMaterialCost(), "The material cost should be 1577.6250");
        Assertions.assertEquals(expectedLaborCost, orderC.getLaborCost(), "The labor cost should be 3358.0875");
        Assertions.assertEquals(expectedTax, orderC.getTax(), "The tax should be 296.142750");
        Assertions.assertEquals(expectedTotal, orderC.getTotal(), "The total should be 5231.855250");
    }
    
    @Test
    public void getNextOrderNumberTest(){
        //ARRANGE
        //ACT
        Integer foundOrderNumber = testService.nextOrderNumber();
        Integer expectedOrderNumber = 6;
        
        //ASSERT
        Assertions.assertEquals(expectedOrderNumber, foundOrderNumber, "The next order number should be 6");
    }
    
    @Test
    public void validatePastDatePropertyTest(){
        //ARRANGE
        String pastDate = "04-08-1997";
        
        //ACT
        boolean validation = testService.validateNewOrderDate(pastDate);
        
        //ASSERT
        Assertions.assertTrue(validation, "Orders cannot be placed to fill for the past");
    }
    
    @Test
    public void validatePresentDatePropertyTest(){
        //ARRANGE
        LocalDate today = LocalDate.now();
        String presentDate = today.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        
        //ACT
        boolean validation = testService.validateNewOrderDate(presentDate);
        
        //ASSERT
        Assertions.assertTrue(validation, "Orders cannot be placed to fill for today");
    }
    
    @Test
    public void validateFutureDatePropertyTest(){
        //ARRANGE
        String futureDate = "01-01-3020";
        
        //ACT
        boolean validation = testService.validateNewOrderDate(futureDate);
        
        //ASSERT
        Assertions.assertFalse(validation, "Orders can be placed for future dates");
    }
    
    @Test
    public void validateCorrectCustomerNameTest(){
        //ARRANGE
        String correctName = "Acme 23, Inc.";
        
        //ACT
        boolean validation = testService.validateCustomerName(correctName);
        
        //ASSERT
        Assertions.assertFalse(validation, "Customer names can have letters, numbers, periods, and commas in them");
    }
    
    @Test
    public void validateIncorrectCustomerNameTest(){
        //ARRANGE
        String incorrectName = "Scooby & Shaggy";
        
        //ACT
        boolean validation = testService.validateCustomerName(incorrectName);
        
        //ASSERT
        Assertions.assertTrue(validation, "Customer names can only have letters, numbers, periods, and commas in them");
    }
    
    @Test
    public void validateCorrectStateATest(){
        //ARRANGE
        String correctStateAbb = "KY";
        String correctStateName = "Kentucky";
        
        //ACT
        boolean validation1 = testService.validateState(correctStateAbb);
        boolean validation2 = testService.validateState(correctStateName);
        
        //ASSERT
        Assertions.assertFalse(validation1, "States can be given as their abbreviations");
        Assertions.assertFalse(validation2, "States can be given as their full name");
    }
    
    @Test
    public void validateCorrectStateBTest(){
        //ARRANGE
        String correctStateAbb = "IN";
        String correctStateName = "Indiana";
        
        //ACT
        boolean validation1 = testService.validateState(correctStateAbb);
        boolean validation2 = testService.validateState(correctStateName);
        
        //ASSERT
        Assertions.assertFalse(validation1, "States can be given as their abbreviations");
        Assertions.assertFalse(validation2, "States can be given as their full name");
    }
    
    @Test
    public void validateIncorrectStateTest(){
        //ARRANGE
        String incorrectStateAbb = "TX";
        String incorrectStateName = "Texas";
        
        //ACT
        boolean validation1 = testService.validateState(incorrectStateAbb);
        boolean validation2 = testService.validateState(incorrectStateName);
        
        //ASSERT
        Assertions.assertTrue(validation1, "Only state abbreviations in the DAO can be used");
        Assertions.assertTrue(validation2, "Only state names in the DAO can be used");
    }
    
    @Test
    public void validateCorrectProductsTest(){
        //ARRANGE
        String correctProduct1 = "wood";
        String correctProduct2 = "carpet";
        
        //ACT
        boolean validation1 = testService.validateProductType(correctProduct1);
        boolean validation2 = testService.validateProductType(correctProduct2);
        
        //ASSERT
        Assertions.assertFalse(validation1, "Wood is in the DAO and can be used");
        Assertions.assertFalse(validation2, "Carpet is in the DAO and can be used");
    }
    
    @Test
    public void validateIncorrectProductTest(){
        //ARRANGE
        String incorrectProduct = "tile";
        
        //ACT
        boolean validation = testService.validateProductType(incorrectProduct);
        
        //ASSERT
        Assertions.assertTrue(validation, "Tile is not in the DAO and cannot be used");
    }
    
    @Test
    public void validateUnderMinAreaTest(){
        //ARRANGE
        BigDecimal tooLowA = new BigDecimal("50.00");
        BigDecimal tooLowB = new BigDecimal("99.99");
        
        //ACT
        boolean validationA = testService.validateArea(tooLowA);
        boolean validationB = testService.validateArea(tooLowB);
        
        //ASSERT
        Assertions.assertTrue(validationA, "Area cannot be lower than 100.00");
        Assertions.assertTrue(validationB, "Area cannot be lower than 100.00");
    }
    
    @Test
    public void validateAtMinAreaTest(){
        //ARRANGE
        BigDecimal atMin = new BigDecimal("100.00");
        
        //ACT
        boolean validation = testService.validateArea(atMin);
        
        //ASSERT
        Assertions.assertFalse(validation, "Area can be 100.00");
    }
    
    @Test
    public void validateOverMinAreaTest(){
        BigDecimal overMinA = new BigDecimal("100.99");
        BigDecimal overMinB = new BigDecimal("300.00");
        
        //ACT
        boolean validationA = testService.validateArea(overMinA);
        boolean validationB = testService.validateArea(overMinB);
        
        //ASSERT
        Assertions.assertFalse(validationA, "Area can be over 100.00");
        Assertions.assertFalse(validationB, "Area can be over 100.00");
    }
    
    @Test
    public void orderForDateNotFoundTest(){
        //ARRANGE
        LocalDate noOrdersDate = LocalDate.parse("09-29-2019", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        List<Order> orderList = testService.getOrdersByOrderDate(noOrdersDate);
        
        //ACT
        boolean listWasEmpty = testService.ordersForDateNotFound(orderList);
        
        //ASSERT
        Assertions.assertTrue(listWasEmpty, "There should be no orders with this date");
    }
    
    @Test
    public void validateRealDateTest(){
        //ARRANGE
        String realDate = "01-01-2019";
        
        //ACT
        boolean datePassed = testService.validateRealDate(realDate);
        
        //ASSERT
        Assertions.assertFalse(datePassed, "The date needs to be real");
    }
    
    @Test
    public void validateFakeDateTest(){
        //ARRANGE
        String notRealDateA = "13-01-2019";
        String notRealDateB = "01-32-2019";
        
        //ACT
        boolean fakeDateA = testService.validateRealDate(notRealDateA);
        boolean fakeDateB = testService.validateRealDate(notRealDateB);
        
        //ASSERT
        Assertions.assertTrue(fakeDateA, "The date needs to be fake");
        Assertions.assertTrue(fakeDateB, "The date needs to be fake");
    }
}
