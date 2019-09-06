package jaredbgreat.dldungeons.builder;

/* 
 * Doomlike Dungeons by is licensed the MIT License
 * Copyright (c) 2014-2018 Jared Blackburn
 */	

import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;

public final class DBlock {
	private final String id;   // The name
	private final Material block; // The Minecraft block
	
	// Block constants used by the mod for various purposes, usually for placement
	public static final Material spawner = Material.SPAWNER;
	public static final Material chest   = Material.CHEST;
	public static final Material portal1 = Material.END_PORTAL_FRAME;
	public static final Material portal2 = Material.END_PORTAL;
	public static final Material quartz  = Material.QUARTZ_BLOCK;
	public static final Material lapis   = Material.LAPIS_BLOCK;
	public static final Material water   = Material.WATER;
	public static final Material air     = Material.AIR;
	
	// Block IDs, used for comparison to tell if the block is allowed to be replaced
//	public static final int chestid   = Block.getIdFromBlock(chest);	
//	public static final int spawnerid = Block.getIdFromBlock(spawner);	
//	public static final int portal1id = Block.getIdFromBlock(portal1);	
//	public static final int portal2id = Block.getIdFromBlock(portal2);
	
	// All blocks, complete with meta-data used by the mod
	public static final ArrayList<DBlock> registry = new ArrayList<DBlock>();
	
	
	/**
	 * Gets the item named by the string "in" -- hacky, but might work 
	 * for now, hopefully....
	 * 
	 * @param in
	 * @return
	 */
//	private static Item getItem(String in) {
//		return Item.REGISTRY.getObject(new ResourceLocation(in));
//	}
		
	
	/**
	 * Construct a dungeon block using a theme format from version 1.7 of the mod
	 * or newer.
	 * 
	 * @param id
	 */
	private DBlock(String id) throws NoSuchElementException {		
		this.id = id;
                this.block = Bukkit.createBlockData(id).getMaterial();
//		Block theBlock;
//		int meta;
//		StringTokenizer nums = new StringTokenizer(id, ":({[]})");
//		String modid = nums.nextToken();
//		ResourceLocation name = new ResourceLocation(modid 
//				+ ":" + nums.nextToken());
//		theBlock = GameRegistry.findRegistry(Block.class).getValue(name);
//		if(theBlock == null) {
//			String error = "[DLDUNGEONS] ERROR! Block read as \"" + id 
//					+ "\" was was not in registry (returned null).";
//			Logging.logError(error);
//			throw new NoSuchElementException(error);
//		}
//		if(nums.hasMoreElements()) {
//			meta = Integer.parseInt(nums.nextToken()); 
//		} else {
//			meta = 0;
//		}
//		block = theBlock.getStateFromMeta(meta);
//		if(block.toString().contains("minecraft:air") 
//				&& !id.contains("minecraft:air")) {
//			String error = "[DLDUNGEONS] ERROR! Block read as \"" + id 
//					+ "\" parsed into an air block!";
//			Logging.logError(error);
//			throw new NoSuchElementException(error);
//		}
	}
	
	
	/**
	 * This is the same as just place(), and will use the full block state.  It 
	 * is maintained for compatibility, but no longer has a real purpose.
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 */
//	@Deprecated
//	public void placeNoMeta(World world, int x, int y, int z) {
//		if(isProtectedBlock(world, x, y, z)) return;
//		BlockPos pos = new BlockPos(x, y, z);
//		if (MinecraftForge.TERRAIN_GEN_BUS.post(new DLDEvent.PlaceDBlock(world, pos, this))) return;
//		world.setBlockState(new BlockPos(x, y, z), block);
//	}
	
	
	/**
	 * Places the block in the world, including its correct meta-data.  This wrapping allow
	 * for changes in block / state representation and method signatures to be easily
	 * adapted and for meta-blocks to easily be used in dungeons. 
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 */
	public void place(World world, int x, int y, int z) {
		if(isProtectedBlock(world, x, y, z)) return;
                world.getBlockAt(x, y, z).setType(block);
	}
	
	
	/************************************************************************************/
	/*                STATIC UTILITIES BELOW (non-static methods above)                 */
	/************************************************************************************/
	
	
	/**
	 * This will place a block from the DBlock registry into the world based on its internal
	 * ID (i.e., its registry index).
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param block
	 */
	public static void place(World world, int x, int y, int z, int block) {
		if(!isProtectedBlock(world, x, y, z)) 
				registry.get(block).place(world, x, y, z);
	}
	
	
	/**
	 * Turns a string labeling the block into a DBlock and adds it to the registry if 
	 * its not already present.  It will return the new DBlocks registry index for 
	 * use as an internal id.
	 * 
	 * This is for use with mod versions newer that 1.7.
	 * 
	 * 
	 * @param id
	 * @param version
	 * @return
	 * @throws NoSuchElementException
	 */
	public static int add(String id) throws NoSuchElementException {
		DBlock block = new DBlock(id);
		if(!registry.contains(block)) {
			registry.add(block);
		}
		return registry.indexOf(block);
	}
	
	
	/**
	 * Purely a wrapper for block/state placing/setting methods typically found in world,
	 * allowing easier updating when block representation or method signature / location
	 * changes.
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param block
	 */
	public static boolean placeBlock(World world, int x, int y, int z, BlockData block) {
		if(isProtectedBlock(world, x, y, z)) return false;
//		BlockPos pos = new BlockPos(x, y, z);
//		if (MinecraftForge.TERRAIN_GEN_BUS.post(new DLDEvent.PlaceBlock(world, pos, block))) return false;
                world.getBlockAt(x, y, z).setBlockData(block);
//		world.setBlockState(pos, block.getDefaultState());
                
		return true;
	}
        public static boolean placeBlock(World world, int x, int y, int z, Material block) {
		if(isProtectedBlock(world, x, y, z)) return false;
//		BlockPos pos = new BlockPos(x, y, z);
//		if (MinecraftForge.TERRAIN_GEN_BUS.post(new DLDEvent.PlaceBlock(world, pos, block))) return false;
                world.getBlockAt(x, y, z).setType(block);
//		world.setBlockState(pos, block.getDefaultState());
                
		return true;
	}
	
	
	/**
	 * Purely a wrapper for block/state placing/setting methods typically found in world,
	 * allowing easier updating when block representation or method signature / location
	 * changes.
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param block
	 */
//	public static void placeBlock(World world, int x, int y, int z, Block block, int a, int b) {
//		if(isProtectedBlock(world, x, y, z)) return; 
//		world.setBlockState(new BlockPos(x, y, z), block.getStateFromMeta(a));
//	}
	
	
	/**
	 * A wrapper for setting a block to air.
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void deleteBlock(World world, int x, int y, int z) {
		if(isProtectedBlock(world, x, y, z)) return;
//		world.setBlockToAir(new BlockPos(x, y, z));
                world.getBlockAt(x, y, z).setType(air);
	}
	
	
	/**
	 * Almost a wrapper for setting the block to air in world, but will alternately set
	 * blocks to water instead if a dungeons is flooded.
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param flooded
	 */
	public static void deleteBlock(World world, int x, int y, int z, boolean flooded) {
		if(isProtectedBlock(world, x, y, z)) return;
		if(flooded) placeBlock(world, x, y, z, water); 
		else world.getBlockAt(x, y, z).setType(air);
	}
	
	
	/**
	 * Simply a wrapper for placing a chest.
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void placeChest(World world, int x, int y, int z) {
		placeBlock(world, x, y, z, chest);		
	}
	
	
	/**
	 * This will place a spawner and set it to spawn the mob named.
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param mob
	 */
	public static void placeSpawner(World world, int x, int y, int z, EntityType mob) {
		// Place spawner block
//		BlockPos pos = new BlockPos(x, y, z);
//		if (MinecraftForge.TERRAIN_GEN_BUS.post(new DLDEvent.BeforePlaceSpawner(world, pos, mob))) return;
		if(isProtectedBlock(world, x, y, z)) return;
		if(!placeBlock(world, x, y, z, spawner)) return;
                
                Block b = world.getBlockAt(x, y, z);
                CreatureSpawner  sp = (CreatureSpawner ) b.getState();
                sp.setSpawnedType(mob);
                sp.update();
//		TileEntityMobSpawner theSpawner = (TileEntityMobSpawner)world.getTileEntity(pos);
		
		// Set up spawner logic
//		MobSpawnerBaseLogic logic = theSpawner.getSpawnerBaseLogic();		
//		NBTTagCompound spawnData = new NBTTagCompound();
//	    spawnData.setString("id", mob);
//	    logic.setNextSpawnData(new WeightedSpawnerEntity(1, spawnData));
	}
	
	
	/**
	 * True if the block Material is ground, grass, iron, sand, rock, or clay. This is 
	 * used to determine if building under a structure should stop because it has reached
	 * the ground, so true really mean "stop building now."
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static boolean isGroundBlock(World world, int x, int y, int z) {
		Material mat = world.getBlockAt(x, y, z).getType();
		return 	   (mat == Material.GRASS) 
				|| (mat == Material.IRON_BLOCK) 
				|| (mat == Material.DIRT) 
				|| (mat == Material.SAND) 
				|| (mat == Material.STONE) 
				|| (mat == Material.CLAY
				// Failsafe, it can never go into the void, or become an infinite loop
				|| (y < 0));
	}
	
	
	/**
	 * True if the block is one that should not be built over; specifically a chest,
	 * spawner, or any part of the End portal.
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static boolean isProtectedBlock(World world, int x, int y, int z) {
//		int block = Block.getIdFromBlock(world.getBlockState(new BlockPos(x, y, z)).getBlock());
                Material block = world.getBlockAt(x, y, z).getType();
		return (block == chest || block == spawner
				|| block == portal1 || block == portal2);
	}
	
	
	/************************************************************************************/
	/*                         BASIC OVERIDEN METHODS FROM OBJECT                       */
	/************************************************************************************/
	
	
	/**
	 * Returns true if the other object is a DBlock the holds the same block with 
	 * the same meta-data. 
	 */
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof DBlock)) return false; 
		return (block.equals(((DBlock)other).block));
	}
	
	
	/**
	 * Returns a hash code derived from the block id and meta data; it will only produce 
	 * the same hash code if these are both equal, that is, equal hash codes implies equals() 
	 * is true. 
	 */
	@Override
	public int hashCode() {
		return block.hashCode();
	}
}