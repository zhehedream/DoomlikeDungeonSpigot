/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zhehe
 */
public class IProperty {
    String value;
    public IProperty(String value) {
        this.value = value;
    }
    
    public int getInt() {
        try {
            return Integer.parseInt(value);
        } catch(Exception ex) {
            return 0;
        }
    }
    
    public boolean getBoolean(Boolean bool) {
        try {
            return Boolean.parseBoolean(value);
        } catch(Exception ex) {
            return bool;
        }
    }
    
    public String[] getStringList() {
        List<String> list = new ArrayList<>();
        String[] array = value.split(",");
        for(String sub : array) {
            try {
                list.add(sub);
            } catch(Exception ex) {
                
            }
        }
        String[] res = new String[list.size()];
        for(int i = 0; i < res.length; i++) {
            res[i] = list.get(i);
        }
        return res;
    }
    
    public int[] getIntList() {
        List<Integer> list = new ArrayList<>();
        String[] array = value.split(",");
        for(String sub : array) {
            try {
                list.add(Integer.parseInt(sub));
            } catch(Exception ex) {
                
            }
        }
        int[] res = new int[list.size()];
        for(int i = 0; i < res.length; i++) {
            res[i] = list.get(i);
        }
        return res;
    }
}
