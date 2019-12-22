/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjsouth.flooring.dao;

import com.bjsouth.flooring.dto.ModeSetting;

/**
 *
 * @author Bree
 */
public interface ModeDAO {
    public ModeSetting createModeSetting(Integer key, ModeSetting modeSetting);
    public ModeSetting readModeSetting(Integer only1ModeSetting);
    
    public void loadMode() throws FilePersistenceException;
}
