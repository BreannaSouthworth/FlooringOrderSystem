/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.service.stubs;

import com.bjsouth.flooring.dao.FilePersistenceException;
import com.bjsouth.flooring.dao.ModeDAO;
import com.bjsouth.flooring.dto.ModeSetting;

/**
 *
 * @author Bree
 */
public class ModeDAOStub implements ModeDAO{

    @Override
    public void loadMode() throws FilePersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModeSetting createModeSetting(Integer key, ModeSetting modeSetting) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModeSetting readModeSetting(Integer only1ModeSetting) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
