/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.dao;

import com.bjsouth.flooring.dto.Tax;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Bree
 */
public class TaxDAOTest {
    TaxDAO taxDao;
    
    public TaxDAOTest(){
        taxDao = new TaxDAOImpl();
    }
    
    @Test
    public void addOneGetOneTest(){
        Tax alpha = new Tax("KY", "Kentucky", new BigDecimal("6.00"));
        
        taxDao.createTax("KY", alpha);
        Tax retrievedTax = taxDao.readSignleTax("KY");
        
        Assertions.assertEquals(alpha, retrievedTax, "The taxes should match");
    }
    
    @Test
    public void allTwoGetAllTest(){
        Tax alpha = new Tax("KY", "Kentucky", new BigDecimal("6.00"));
        Tax bravo = new Tax("IN", "Indiana", new BigDecimal("7.00"));
        
        taxDao.createTax("KY", alpha);
        taxDao.createTax("IN", bravo);
        List<Tax> taxList = taxDao.readAllTaxes();
        
        Assertions.assertNotNull(taxList, "List should not be null");
        Assertions.assertEquals(2, taxList.size(), "There should be two taxes in list");
        Assertions.assertTrue(taxList.contains(alpha), "Tax list should have the first tax stored");
        Assertions.assertTrue(taxList.contains(bravo), "Tax list should have the second tax stored");
    }
}
