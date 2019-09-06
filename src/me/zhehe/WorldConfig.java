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
import java.util.HashSet;
import java.util.Set;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldConfig {
    public Set<String> world = new HashSet<>();
    public transient File file;
    public transient JavaPlugin plugin;
    
    public WorldConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder().toString() + File.separator + "world.json");
    }
    
    public void addWorld(String world_name) {
        world.add(world_name);
        save();
    }
    
    public void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this);
        try(OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8")) {
            oStreamWriter.append(json);
            oStreamWriter.close();
        } catch (IOException ex) {
            Logging.logError("Error while saving world config file.");
        }
    }
    
    public void load() {
        WorldConfig wc;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))) {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            wc = (new Gson()).fromJson(sb.toString(), WorldConfig.class);
        } catch (Exception ex) {
            Logging.logError("Error while loading world config file.");
            return;
        }
        if(wc == null) return;
        this.world = wc.world;
    }
}
