package jaredbgreat.dldungeons.builder;


/* 
 * Doomlike Dungeons by is licensed the MIT License
 * Copyright (c) 2014-2018 Jared Blackburn
 */	

import static jaredbgreat.dldungeons.builder.DBlock.*;
import jaredbgreat.dldungeons.planner.Dungeon;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.World;


public class Builder {
	
	private static boolean debugPole = false;
	
	
	/**
	 * This will place a dungeon into the world, and is called to create the dungeon object 
	 * (which plans the dungeon) when a dungeon is cheated in by command or generated through
	 * the API and have the dungeon built.  Basically, this is used any time and dungeons is 
	 * generated without the use of IChunkGenerator and IChunkProvider objects, that is, not
	 * through an IWorldGenerator.
	 * 
	 * @param random
	 * @param chunkX
	 * @param chunkZ
	 * @param world
	 * @throws Throwable
	 */
	public static void placeDungeon(Random random, int chunkX, int chunkZ, World world) throws Throwable {	
//		if(world.isRemote) return; // Do not perform world-gen on the client!
//		if (MinecraftForge.TERRAIN_GEN_BUS.post(new DLDEvent.PlaceDungeonBegin(random, chunkX, chunkZ, world))) return;
//		DoomlikeDungeons.profiler.startTask("Create Dungeons");
		Dungeon dungeon = new Dungeon(random, 
								world.getBiome(chunkX * 16, chunkZ * 16), 
								world, chunkX, chunkZ);
		buildDungeon(dungeon);
		dungeon.preFinalize();
		dungeon = null;
//		MinecraftForge.TERRAIN_GEN_BUS.post(new DLDEvent.PlaceDungeonFinish(random, chunkX, chunkZ, world, dungeon));
//		DoomlikeDungeons.profiler.endTask("Create Dungeons");
	}
        
        public static boolean commandPlaceDungeon(Random random, int chunkX, int chunkZ, World world) throws Throwable {	
//		if(world.isRemote) return; // Do not perform world-gen on the client!
//		if (MinecraftForge.TERRAIN_GEN_BUS.post(new DLDEvent.PlaceDungeonBegin(random, chunkX, chunkZ, world))) return;
//		DoomlikeDungeons.profiler.startTask("Create Dungeons");
		Dungeon dungeon = new Dungeon(random, 
								world.getBiome(chunkX * 16, chunkZ * 16), 
								world, chunkX, chunkZ);
                boolean res = (dungeon.theme != null);
                
		buildDungeon(dungeon);
		dungeon.preFinalize();
		dungeon = null;
                
                return res;
//		MinecraftForge.TERRAIN_GEN_BUS.post(new DLDEvent.PlaceDungeonFinish(random, chunkX, chunkZ, world, dungeon));
//		DoomlikeDungeons.profiler.endTask("Create Dungeons");
	}
		
	
	/**
	 * This will build the dungeon into the world, technically by having the dungeons map
	 * build itself.
	 * 
	 * @param dungeon
	 */
	public static void buildDungeon(Dungeon dungeon /*TODO: Parameters*/) {
		//System.out.println("[DLDUNGONS] Inside Builder.placeDungeon; building dungeon");
		if(dungeon.theme != null) dungeon.map.build(dungeon);
//                else Bukkit.getLogger().log(Level.SEVERE, "ERROR1"); //$$$
	}
	
	
	/**
	 * This will build a quartz pillar to appear in the center of the dungeon from y=16 to y=240, 
	 * and a lapis lazuli boarder to appear around the area allotted for the dungeon at y=80.
	 * 
	 * This is only called if debugPole == true.
	 * 
	 * @param world
	 * @param chunkX
	 * @param chunkZ
	 * @param dungeon
	 */
	public static void debuggingPole(World world, int chunkX, int chunkZ, Dungeon dungeon) {
		int x = (chunkX * 16) + 8;
		int z = (chunkZ * 16) + 8;
		for(int y = 16; y <= 241; y++) placeBlock(world, x, y, z, quartz);
		for(int i = -dungeon.size.radius; i <= dungeon.size.radius; i++) {
			placeBlock(world, x - dungeon.size.radius, 80, z + i, lapis);
			placeBlock(world, x + dungeon.size.radius, 80, z + i, lapis);
			placeBlock(world, x + i, 80, z - dungeon.size.radius, lapis);
			placeBlock(world, x + i, 80, z + dungeon.size.radius, lapis);
		}		
	}
	
	
	/**
	 * Set the debugPole variable.  Setting this to true will cause a quartz pillar to appear in the
	 * center of the dungeon from y=16 to y=240, and a lapis lazuli boarder to appear around the area
	 * allotted for the dungeon at y=80. 
	 * 
	 * @param val 
	 */
	public static void setDebugPole(boolean val) {
		debugPole = val;
	}
}