/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Zhehe
 */
public class Configuration {
    public List<String> Category = new ArrayList<>();
    public List<Map<String, String>> Content = new ArrayList<>();
    public File file;
    public Configuration(File file) {
        this.file = file;
    }
    
    public void addCustomCategoryComment(String name, String comment) {
        if(Category.contains(name)) return;
        Category.add(name);
        Content.add(new HashMap<String, String>());
    }
    
    public IProperty get(String category, String key, int dvalue, String comment) {
        if(!Category.contains(category)) {
            Category.add(category);
            Map<String, String> sub = new HashMap<>();
            sub.put(key, Integer.toString(dvalue));
            Content.add(sub);
            return new IProperty(Integer.toString(dvalue));
        }
        int index = Category.indexOf(category);
        Map<String, String> dict = Content.get(index);
        if(!dict.containsKey(key)) {
            dict.put(key, Integer.toString(dvalue));
            return new IProperty(Integer.toString(dvalue));
        }
        return new IProperty(dict.get(key));
    }
    
    public IProperty get(String category, String key, int[] array, String comment) {
        if(!Category.contains(category)) {
            Category.add(category);
            Map<String, String> sub = new HashMap<>();
            sub.put(key, intArrayToString(array));
            Content.add(sub);
            return new IProperty(intArrayToString(array));
        }
        int index = Category.indexOf(category);
        Map<String, String> dict = Content.get(index);
        if(!dict.containsKey(key)) {
            dict.put(key, intArrayToString(array));
            return new IProperty(intArrayToString(array));
        }
        return new IProperty(dict.get(key));
    }
    
    public IProperty get(String category, String key, boolean bool, String comment) {
        if(!Category.contains(category)) {
            Category.add(category);
            Map<String, String> sub = new HashMap<>();
            sub.put(key, Boolean.toString(bool));
            Content.add(sub);
            return new IProperty(Boolean.toString(bool));
        }
        int index = Category.indexOf(category);
        Map<String, String> dict = Content.get(index);
        if(!dict.containsKey(key)) {
            dict.put(key, Boolean.toString(bool));
            return new IProperty(Boolean.toString(bool));
        }
        return new IProperty(dict.get(key));
    }
    
    public IProperty get(String category, String key, String[] array, String comment) {
        if(!Category.contains(category)) {
            Category.add(category);
            Map<String, String> sub = new HashMap<>();
            sub.put(key, StringArrayToString(array));
            Content.add(sub);
            return new IProperty(StringArrayToString(array));
        }
        int index = Category.indexOf(category);
        Map<String, String> dict = Content.get(index);
        if(!dict.containsKey(key)) {
            dict.put(key, StringArrayToString(array));
            return new IProperty(StringArrayToString(array));
        }
        return new IProperty(dict.get(key));
    }
    
    private static String intArrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for(int value : array) {
            sb.append(value);
            sb.append(',');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
    
    private static String StringArrayToString(String[] array) {
        StringBuilder sb = new StringBuilder();
        for(String value : array) {
            sb.append(value);
            sb.append(',');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
    
    public int getInt(String name, String category, int defaultValue, int minValue, int maxValue, String comment) {
        if(!Category.contains(category)) {
            Category.add(category);
            Map<String, String> sub = new HashMap<>();
            sub.put(name, Integer.toString(defaultValue));
            Content.add(sub);
            return defaultValue;
        }
        int index = Category.indexOf(category);
        Map<String, String> dict = Content.get(index);
        if(!dict.containsKey(name)) {
            dict.put(name, Integer.toString(defaultValue));
            return defaultValue;
        }
        String tmp = dict.get(name);
        int res;
        try {
            res = Integer.parseInt(tmp);
        } catch (Exception ex) {
            res = defaultValue;
        }
        
        if(res < minValue) res = minValue;
        if(res > maxValue) res = maxValue;
        return res;
    }
    
    public void load() {
        Configuration cf;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))) {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            cf = (new Gson()).fromJson(sb.toString(), Configuration.class);
        } catch (Exception ex) {
            return;
        }
        if(cf == null) return;
        this.Category = cf.Category;
        this.Content = cf.Content;
    }
    
    public void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this);
        try(OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8")) {
            oStreamWriter.append(json);
            oStreamWriter.close();
        } catch (IOException ex) {
            
        }
    }
}
