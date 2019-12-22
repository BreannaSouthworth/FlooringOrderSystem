/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.dao;

import com.bjsouth.flooring.dto.Mode;
import com.bjsouth.flooring.dto.ModeSetting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Bree
 */
public class ModeDAOTest {
    ModeDAO modeDao;
    private Mode trainingMode = Mode.TRAINING;
    private Mode productionMode = Mode.PRODUCTION;
    
    public ModeDAOTest(){
        modeDao = new ModeDAOImpl();
    }
    
    @Test
    public void getTrainingModeTest(){
        ModeSetting modeSetting = new ModeSetting(trainingMode);
        
        modeDao.createModeSetting(1, modeSetting);
        ModeSetting currentMode = modeDao.readModeSetting(1);
        
        Assertions.assertEquals(modeSetting, currentMode, "The current mode should be training");
    }
    
    @Test
    public void getProductionModeTest(){
        ModeSetting modeSetting = new ModeSetting(productionMode);
        
        modeDao.createModeSetting(1, modeSetting);
        ModeSetting currentMode = modeDao.readModeSetting(1);
        
        Assertions.assertEquals(modeSetting, currentMode, "The current mode should be production");
    }
}
