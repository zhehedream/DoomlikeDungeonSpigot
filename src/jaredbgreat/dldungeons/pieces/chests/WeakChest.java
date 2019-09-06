package jaredbgreat.dldungeons.pieces.chests;

/* 
 * Doomlike Dungeons by is licensed the MIT License
 * Copyright (c) 2014-2018 Jared Blackburn
 */	

//import jaredbgreat.dldungeons.ConfigHandler;
import jaredbgreat.dldungeons.builder.DBlock;

import java.util.Random;
import org.bukkit.Location;

//import net.minecraft.tileentity.TileEntityChest;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Represents a weak / junk chest found in rooms without spawners and containing
 * small amounts of starter materials.
 * 
 * @author Jared Blackburn
 *
 */
public class WeakChest extends BasicChest {
	
	
	public WeakChest(int x, int y, int z, LootCategory category) {
		super(x, y, z, 0, category);		
	}
	
	@Override
	public void place(World world, int x, int y, int z, Random random) {
		Location pos = new Location(world, x, y, z);
		Block contents = world.getBlockAt(pos);
                Block block = world.getBlockAt(pos);
		if(block.getType() != DBlock.chest) {
			System.err.println("[DLDUNGEONS] ERROR! Trying to put loot into non-chest at " 
									+ x + ", " + y + ", " + z + " (basic chest).");
			return;
		}
		if(random.nextBoolean()) {
			if(random.nextBoolean()) fillChest(contents, LootType.GEAR, random);
			else fillChest(contents, LootType.HEAL, random);
		} else {
			fillChest(contents, LootType.GEAR, random);
			fillChest(contents, LootType.HEAL, random);
		}
	}

}
