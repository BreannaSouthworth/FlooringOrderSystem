/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.dao;

import com.bjsouth.flooring.dto.Product;
import java.util.List;

/**
 *
 * @author Bree
 */
public interface ProductDAO {
    Product createProduct(String productType, Product product);
    Product readSingleProduct(String productType);
    List<Product> readAllProducts();
    public void loadProducts() throws FilePersistenceException;
}