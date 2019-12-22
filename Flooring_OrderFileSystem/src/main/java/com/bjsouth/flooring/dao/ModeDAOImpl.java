/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.dao;

import com.bjsouth.flooring.dto.Mode;
import com.bjsouth.flooring.dto.ModeSetting;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Bree
 */
public class ModeDAOImpl implements ModeDAO{
    private static final String FILENAME = "Data/Mode.txt";
    private static final String DELIMITER = "::";
    private Map<Integer, ModeSetting> modeSettingMap = new HashMap<>();
    
    public ModeDAOImpl(){}
    
    @Override
    public ModeSetting createModeSetting(Integer key, ModeSetting modeSetting){
        return modeSettingMap.put(key, modeSetting);
    }
    
    @Override
    public ModeSetting readModeSetting(Integer only1ModeSetting){
        return modeSettingMap.get(1);
    }
    
    private ModeSetting unmarshallModeSetting(String modeSettingLine){
        ModeSetting modeSettingFromFile = new ModeSetting();
        String[] splitLineProperties = modeSettingLine.split(DELIMITER);
        
        String modeAsString = splitLineProperties[1];
        modeSettingFromFile.setMode(Mode.valueOf(modeAsString));
        
        return modeSettingFromFile;
    }
    
    @Override
    public void loadMode() throws FilePersistenceException{
        try{
            FileReader findFile = new FileReader(this.FILENAME);
            BufferedReader getText = new BufferedReader(findFile);
            Scanner scanner = new Scanner(getText);
            
            while(scanner.hasNextLine()){
                String modeSettingLine = scanner.nextLine();
                ModeSetting fromLine = this.unmarshallModeSetting(modeSettingLine);
                this.modeSettingMap.put(1, fromLine);
            }
            scanner.close();
        }catch(FileNotFoundException e){
            throw new FilePersistenceException("Missing Mode File", e);
        }
    }
}
