package jaredbgreat.dldungeons.pieces.chests;

//import net.minecraft.item.ItemStack;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class LootResult {
	final ItemStack result;
	final int level;
	
	LootResult(ItemStack item, int l) {
		result = item;
		level = l;
                
                //TODO: this is not a good place for portion...
                if(item.getType() == Material.POTION) {
                    PotionMeta meta = (PotionMeta) item.getItemMeta();
                    if(meta != null) {
                        meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2), true);
                        meta.addCustomEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100, 2), true);
                        result.setItemMeta(meta);
                    }
                }
	}
	
	
	public ItemStack getLoot() {
		return result;
	}
	
	
	public int getLevel() {
		return level;
	}
}
