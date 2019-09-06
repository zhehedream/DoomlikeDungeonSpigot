/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe;

import jaredbgreat.dldungeons.ConfigHandler;
import static jaredbgreat.dldungeons.builder.Builder.placeDungeon;
import jaredbgreat.dldungeons.themes.ThemeReader;
import jaredbgreat.dldungeons.themes.ThemeType;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Zhehe
 */
public class Main extends JavaPlugin {
    WorldConfig wc;
    public static boolean enable;
    
    @Override
    public void onEnable() {
        enable = true;
        ConfigHandler.configDir = this.getDataFolder();
        if(!ConfigHandler.configDir.exists()) ConfigHandler.configDir.mkdirs();
        ThemeReader.setConfigDir(this.getDataFolder());
        ConfigHandler.reload();
        
        ConfigHandler.generateLists();
    	ThemeReader.readThemes(); 
    	ThemeType.SyncMobLists();
        
        wc = new WorldConfig(this);
        wc.load();
        
        getServer().getPluginManager().registerEvents(new DLDWorldListener(), this);
    }
    
    @Override
    public void onDisable() {
        enable = false;
    }
    
    private class DLDWorldListener implements Listener {
        @EventHandler(priority = EventPriority.LOW)
        public void onWorldInit(WorldInitEvent event) {
            if(!enable) return;
            String world_name = event.getWorld().getName();
            if(wc.world.contains(world_name)) {
                Logging.logInfo("Add DoomlikeDungeon Populator to world: " + world_name);
                event.getWorld().getPopulators().add(new DungeonPopulator());
            } else {
                Logging.logInfo("DoomlikeDungeon Populator is not used in " + world_name);
            }
        }
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("DoomlikeDungeonSpigot.command")) {
                player.sendMessage("You don't have the permission required to use this plugin");
                return true;
            }
        }
        if (command.getName().equalsIgnoreCase("doomlikedungeonspigot")) {
            String worldName;
            if(args.length == 0) {
                sender.sendMessage("Wrong world name");
                return true;
            }
            worldName = args[0];
            wc.addWorld(worldName);
            return true;
        }
        return false;
    }
}
