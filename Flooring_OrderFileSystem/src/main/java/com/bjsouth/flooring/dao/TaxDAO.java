/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.dao;

import com.bjsouth.flooring.dto.Tax;
import java.util.List;

/**
 *
 * @author Bree
 */
public interface TaxDAO {
    Tax createTax(String stateAbb, Tax tax);
    Tax readSignleTax(String stateAbbreviation);
    List<Tax> readAllTaxes();
    public void loadTaxes() throws FilePersistenceException;
}
