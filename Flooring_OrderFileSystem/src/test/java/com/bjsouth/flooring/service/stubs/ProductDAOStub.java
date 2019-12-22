/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.service.stubs;

import com.bjsouth.flooring.dao.FilePersistenceException;
import com.bjsouth.flooring.dao.ProductDAO;
import com.bjsouth.flooring.dto.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bree
 */
public class ProductDAOStub implements ProductDAO{
    public Product alpha;
    public Product bravo;
    
    public ProductDAOStub(){
        this.alpha = new Product("Wood", new BigDecimal("2.25"), new BigDecimal("5.10"));
        this.bravo = new Product("Carpet", new BigDecimal("3.50"), new BigDecimal("7.45"));
    }

    @Override
    public Product createProduct(String productType, Product product){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Product readSingleProduct(String productType) {
        if(productType == alpha.getProductType()){
            return alpha;
        }else if(productType == bravo.getProductType()){
            return bravo;
        }else{
            return null;
        }
    }

    @Override
    public List<Product> readAllProducts() {
        List<Product> stubProducts = new ArrayList<>();
        stubProducts.add(alpha);
        stubProducts.add(bravo);
        return stubProducts;
    }

    @Override
    public void loadProducts() throws FilePersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
