/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe;

import jaredbgreat.dldungeons.ConfigHandler;
import static jaredbgreat.dldungeons.builder.Builder.placeDungeon;
import java.util.Random;
import java.util.Set;
import me.zhehe.BiomeDictionary.Type;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

public class DungeonPopulator extends BlockPopulator {
    private static int factor = 6;

    @Override
    public void populate(final World world, final Random random, final Chunk source) {
        if(!Main.enable) return;
        boolean blockedBiome = false;
        Biome biome = world.getBiome(source.getX() * 16, source.getZ() * 16);
        if(biome == Biome.NETHER) {
            if(random.nextInt(5) > 0) return;
        }
        Set<Type> types = BiomeDictionary.getTypes(biome);
        for(Type type : types) {			
            blockedBiome = ConfigHandler.biomeExclusions.contains(type) || blockedBiome;
        }
        if(blockedBiome) return;
        
        Random mrand = new Random(world.getSeed()
		+ (2027 * (long)(source.getX() / factor)) 
		+ (1987 * (long)(source.getX() / factor)));
        int xrand = mrand.nextInt();
        int zrand = mrand.nextInt();
        int xuse = ((source.getX() + xrand) % factor);
        int zuse = ((source.getZ() + zrand) % factor);

        if((xuse == 0) && (zuse == 0)) {
                try {
                        placeDungeon(random, source.getX(), source.getZ(), world);
                } catch (Throwable e) {
                        System.err.println("[DLDUNGEONS] Danger!  Failed to finalize a dungeon after building!");
                        e.printStackTrace();
                }
        }
    }
}
