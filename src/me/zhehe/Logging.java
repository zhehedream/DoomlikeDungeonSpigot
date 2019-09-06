/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.zhehe;

import java.util.logging.Level;
import org.bukkit.Bukkit;

public class Logging {
    public static void logInfo(String str) {
        Bukkit.getLogger().log(Level.INFO, str);
    }
    public static void logError(String str) {
        Bukkit.getLogger().log(Level.SEVERE, str);
    }
}
