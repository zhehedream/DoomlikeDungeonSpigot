/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.zhehe;

import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;

public class Constant {
    public static BlockData ladder5 = Bukkit.createBlockData("minecraft:ladder[facing=east]");
    public static BlockData ladder4 = Bukkit.createBlockData("minecraft:ladder[facing=west]");
    public static BlockData ladder3 = Bukkit.createBlockData("minecraft:ladder[facing=south]");
    public static BlockData ladder2 = Bukkit.createBlockData("minecraft:ladder[facing=north]");
    
    public static BlockData slab0 = Bukkit.createBlockData("minecraft:stone_slab[type=bottom]");
    public static BlockData slab8 = Bukkit.createBlockData("minecraft:stone_slab[type=top]");
}
