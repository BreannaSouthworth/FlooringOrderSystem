/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.dao;

import com.bjsouth.flooring.dto.Tax;
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
public class TaxDAOImpl implements TaxDAO{
    private Map<String, Tax> taxMap = new HashMap<>();
    private static final String FILENAME = "Data/Taxes.txt";
    private static final String DELIMITER = ",";

    public TaxDAOImpl() {}
    
    @Override
    public Tax createTax(String stateAbb, Tax tax){
        return taxMap.put(stateAbb, tax);
    }

    @Override
    public Tax readSignleTax(String stateAbbreviation) {
        return taxMap.get(stateAbbreviation);
    }
    
    @Override
    public List<Tax> readAllTaxes() {
        List<Tax> taxList = new ArrayList<>(taxMap.values());
        return taxList;
    }
    
    private Tax unmarshallTax(String taxLine){
        Tax taxFromFile = new Tax();
        String[] splitLineProperties = taxLine.split(DELIMITER);
        
        taxFromFile.setStateAbbreviation(splitLineProperties[0]);
        taxFromFile.setStateName(splitLineProperties[1]);
        String taxRateAsString = splitLineProperties[2];
        
        BigDecimal taxRate = new BigDecimal(taxRateAsString);
        taxFromFile.setTaxRate(taxRate);
        
        return taxFromFile;
    }
    
    @Override
    public void loadTaxes() throws FilePersistenceException{
        try{
            FileReader findFile = new FileReader(this.FILENAME);
            BufferedReader getText = new BufferedReader(findFile);
            Scanner scanner = new Scanner(getText);
            
            while(scanner.hasNextLine()){
                String taxLine = scanner.nextLine();
                Tax fromLine = this.unmarshallTax(taxLine);
                this.taxMap.put(fromLine.getStateAbbreviation(), fromLine);
            }
            scanner.close();
        }catch(FileNotFoundException e){
            throw new FilePersistenceException("Missing Taxes File", e);
        }
    }
}
