/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe;

import jaredbgreat.dldungeons.ConfigHandler;
import static jaredbgreat.dldungeons.builder.Builder.commandPlaceDungeon;
import static jaredbgreat.dldungeons.builder.Builder.placeDungeon;
import jaredbgreat.dldungeons.themes.ThemeReader;
import jaredbgreat.dldungeons.themes.ThemeType;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
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
        
        Bukkit.getLogger().log(Level.INFO, "  _____                        _ _ _        ");
        Bukkit.getLogger().log(Level.INFO, " |  __ \\                      | (_) |       ");
        Bukkit.getLogger().log(Level.INFO, " | |  | | ___   ___  _ __ ___ | |_| | _____ ");
        Bukkit.getLogger().log(Level.INFO, " | |  | |/ _ \\ / _ \\| '_ ` _ \\| | | |/ / _ \\");
        Bukkit.getLogger().log(Level.INFO, " | |__| | (_) | (_) | | | | | | | |   <  __/");
        Bukkit.getLogger().log(Level.INFO, " |_____/ \\___/ \\___/|_| |_| |_|_|_|_|\\_\\___|");
        Bukkit.getLogger().log(Level.INFO, " |  __ \\                                    ");
        Bukkit.getLogger().log(Level.INFO, " | |  | |_   _ _ __   __ _  ___  ___  _ __  ");
        Bukkit.getLogger().log(Level.INFO, " | |  | | | | | '_ \\ / _` |/ _ \\/ _ \\| '_ \\ ");
        Bukkit.getLogger().log(Level.INFO, " | |__| | |_| | | | | (_| |  __/ (_) | | | |");
        Bukkit.getLogger().log(Level.INFO, " |_____/ \\__,_|_| |_|\\__, |\\___|\\___/|_| |_|");
        Bukkit.getLogger().log(Level.INFO, "  / ____|     (_)     __/ | | |             ");
        Bukkit.getLogger().log(Level.INFO, " | (___  _ __  _  __ |___/_ | |_            ");
        Bukkit.getLogger().log(Level.INFO, "  \\___ \\| '_ \\| |/ _` |/ _ \\| __|           ");
        Bukkit.getLogger().log(Level.INFO, "  ____) | |_) | | (_| | (_) | |_            ");
        Bukkit.getLogger().log(Level.INFO, " |_____/| .__/|_|\\__, |\\___/ \\__|           ");
        Bukkit.getLogger().log(Level.INFO, "        | |       __/ |                     ");
        Bukkit.getLogger().log(Level.INFO, "        |_|      |___/                      ");
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
                Logging.logInfo("[Doomlike Dungeons] Add DoomlikeDungeon Populator to world: " + world_name);
                event.getWorld().getPopulators().add(new DungeonPopulator());
            } else {
                Logging.logInfo("[Doomlike Dungeons] DoomlikeDungeon Populator is not used in " + world_name);
            }
        }
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (command.getName().equalsIgnoreCase("doomlikedungeonspigot")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("dld.admin")) {
                    player.sendMessage("You don't have the permission required to use this plugin");
                    return true;
                }
            }
            String worldName;
            if(args.length == 0) {
                sender.sendMessage("[Doomlike Dungeon] Invalid world name");
                return true;
            }
            worldName = args[0];
            wc.addWorld(worldName);
            sender.sendMessage("[Doomlike Dungeon] Done");
            return true;
        } else if (command.getName().equalsIgnoreCase("doomlikedungeonspigot_place")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("[Doomlike Dungeon] Player only command...");
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("dld.place")) {
                player.sendMessage("You don't have the permission required to place a dungeon");
                return true;
            }
            Location loc = player.getLocation();
            Chunk chunk = loc.getChunk();
            World world = loc.getWorld();
            
            try {
                boolean res = commandPlaceDungeon(new Random(), chunk.getX(), chunk.getZ(), world);
                if(!res) {
                    sender.sendMessage("Fail: No theme available for this chunk...");
                } else {
                    sender.sendMessage("Done");
                }
            } catch (Throwable ex) {
                sender.sendMessage("Internal Error when placing a dungeon in this chunk...");
            }
            
            return true;
        }
        return false;
    }
}
