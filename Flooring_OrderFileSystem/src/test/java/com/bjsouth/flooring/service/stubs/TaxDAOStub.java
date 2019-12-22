/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.service.stubs;

import com.bjsouth.flooring.dao.FilePersistenceException;
import com.bjsouth.flooring.dao.TaxDAO;
import com.bjsouth.flooring.dto.Tax;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bree
 */
public class TaxDAOStub implements TaxDAO{
    private final Tax alpha;
    private final Tax bravo;
    
    public TaxDAOStub(){
        this.alpha = new Tax("KY", "Kentucky", new BigDecimal("6.00"));
        this.bravo = new Tax("IN", "Indiana", new BigDecimal("7.00"));
    }
    
    @Override
    public Tax createTax(String stateAbb, Tax tax){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tax readSignleTax(String stateAbbreviation) {
        if(stateAbbreviation == alpha.getStateAbbreviation()) return alpha;
        if(stateAbbreviation == bravo.getStateAbbreviation()) return bravo;
        else return null;
    }

    @Override
    public List<Tax> readAllTaxes() {
        List<Tax> stubTaxes = new ArrayList<>();
        stubTaxes.add(alpha);
        stubTaxes.add(bravo);
        return stubTaxes;
    }

    @Override
    public void loadTaxes() throws FilePersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
