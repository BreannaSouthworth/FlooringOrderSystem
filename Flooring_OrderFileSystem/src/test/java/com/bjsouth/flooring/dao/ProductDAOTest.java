/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.dao;

import com.bjsouth.flooring.dto.Product;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Bree
 */
public class ProductDAOTest {
    ProductDAO productDao;
    
    public ProductDAOTest(){
        productDao = new ProductDAOImpl();
    }
    
    @Test
    public void addOneGetOneTest(){
        Product alpha = new Product("Wood", new BigDecimal("2.25"), new BigDecimal("5.10"));
        
        productDao.createProduct("Wood", alpha);
        Product retrievedProduct = productDao.readSingleProduct("Wood");
        
        Assertions.assertEquals(alpha, retrievedProduct, "The products should match");
    }
    
    @Test
    public void allTwoGetAllTest(){
        Product alpha = new Product("Wood", new BigDecimal("2.25"), new BigDecimal("5.10"));
        Product bravo = new Product("Carpet", new BigDecimal("3.50"), new BigDecimal("7.45"));
        
        productDao.createProduct("Wood", alpha);
        productDao.createProduct("Carpet", bravo);
        List<Product> productList = productDao.readAllProducts();
        
        Assertions.assertNotNull(productList, "List should not be null");
        Assertions.assertEquals(2, productList.size(), "There should be two products in list");
        Assertions.assertTrue(productList.contains(alpha), "Product list should have the first product stored");
        Assertions.assertTrue(productList.contains(bravo), "Product list should have the second product stored");
    }
}
