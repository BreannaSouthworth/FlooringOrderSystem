/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.dao;

import com.bjsouth.flooring.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Bree
 */
public class ProductDAOImpl implements ProductDAO{
    private Map<String, Product> productMap = new HashMap<>();
    private static final String FILENAME = "Data/Products.txt";
    private static final String DELIMITER = ",";
    
    public ProductDAOImpl(){}

    @Override
    public Product createProduct(String productType, Product product){
        return productMap.put(productType, product);
    }
    
    @Override
    public Product readSingleProduct(String productType) {
        return productMap.get(productType);
    }

    @Override
    public List<Product> readAllProducts() {
        List<Product> productList = new ArrayList<>(productMap.values());
        return productList;
    }
    
    private Product unmarshallProduct(String productLine){
        Product productFromFile = new Product();
        String[] splitLineProperties = productLine.split(DELIMITER);
        
        productFromFile.setProductType(splitLineProperties[0]);
        String costPerSquareFootAsString = splitLineProperties[1];
        String laborCostPerSquareFootAsString = splitLineProperties[2];
        
        BigDecimal costPerSquareFoot = new BigDecimal(costPerSquareFootAsString);
        productFromFile.setCostPerSquareFoot(costPerSquareFoot);
        
        BigDecimal laborCostPerSquareFoot = new BigDecimal(laborCostPerSquareFootAsString);
        productFromFile.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
        
        return productFromFile;
    }
    
    @Override
    public void loadProducts() throws FilePersistenceException{
        try{
            FileReader findFile = new FileReader(this.FILENAME);
            BufferedReader getText = new BufferedReader(findFile);
            Scanner scanner = new Scanner(getText);
            
            while(scanner.hasNextLine()){
                String productLine = scanner.nextLine();
                Product fromLine = this.unmarshallProduct(productLine);
                this.productMap.put(fromLine.getProductType(), fromLine);
            }
            scanner.close();
        }catch(FileNotFoundException e){
            throw new FilePersistenceException("Missing Products File", e);
        }
    }
}
