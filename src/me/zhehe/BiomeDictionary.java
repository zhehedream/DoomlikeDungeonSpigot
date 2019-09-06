/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.zhehe;

/*
 * Minecraft Forge
 * Copyright (c) 2016-2018.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */


//import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.*;
//import java.util.stream.Collectors;
//import javax.annotation.Nonnull;
import org.bukkit.block.Biome;
import static me.zhehe.BiomeDictionary.Type.*;

public class BiomeDictionary
{
    private static final boolean DEBUG = false;
    
    private static final Set<Type> dict = new HashSet<>();

    public static final class Type
    {

        private static final Map<String, Type> byName = new HashMap<String, Type>();
        private static Collection<Type> allTypes = Collections.unmodifiableCollection(byName.values());

        /*Temperature-based tags. Specifying neither implies a biome is temperate*/
        public static final Type HOT = new Type("HOT");
        public static final Type COLD = new Type("COLD");

        /*Tags specifying the amount of vegetation a biome has. Specifying neither implies a biome to have moderate amounts*/
        public static final Type SPARSE = new Type("SPARSE");
        public static final Type DENSE = new Type("DENSE");

        /*Tags specifying how moist a biome is. Specifying neither implies the biome as having moderate humidity*/
        public static final Type WET = new Type("WET");
        public static final Type DRY = new Type("DRY");

        /*Tree-based tags, SAVANNA refers to dry, desert-like trees (Such as Acacia), CONIFEROUS refers to snowy trees (Such as Spruce) and JUNGLE refers to jungle trees.
         * Specifying no tag implies a biome has temperate trees (Such as Oak)*/
        public static final Type SAVANNA = new Type("SAVANNA");
        public static final Type CONIFEROUS = new Type("CONIFEROUS");
        public static final Type JUNGLE = new Type("JUNGLE");

        /*Tags specifying the nature of a biome*/
        public static final Type SPOOKY = new Type("SPOOKY");
        public static final Type DEAD = new Type("DEAD");
        public static final Type LUSH = new Type("LUSH");
        public static final Type NETHER = new Type("NETHER");
        public static final Type END = new Type("END");
        public static final Type MUSHROOM = new Type("MUSHROOM");
        public static final Type MAGICAL = new Type("MAGICAL");
        public static final Type RARE = new Type("RARE");

        public static final Type OCEAN = new Type("OCEAN");
        public static final Type RIVER = new Type("RIVER");
        /**
         * A general tag for all water-based biomes. Shown as present if OCEAN or RIVER are.
         **/
        public static final Type WATER = new Type("WATER", OCEAN, RIVER);

        /*Generic types which a biome can be*/
        public static final Type MESA = new Type("MESA");
        public static final Type FOREST = new Type("FOREST");
        public static final Type PLAINS = new Type("PLAINS");
        public static final Type MOUNTAIN = new Type("MOUNTAIN");
        public static final Type HILLS = new Type("HILLS");
        public static final Type SWAMP = new Type("SWAMP");
        public static final Type SANDY = new Type("SANDY");
        public static final Type SNOWY = new Type("SNOWY");
        public static final Type WASTELAND = new Type("WASTELAND");
        public static final Type BEACH = new Type("BEACH");
        public static final Type VOID = new Type("VOID");

        private final String name;
        private final List<Type> subTypes;
        private final Set<Biome> biomes = new HashSet<Biome>();
//        private final Set<Biome> biomesUn = Collections.unmodifiableSet(biomes);

        private Type(String name, Type... subTypes)
        {
            this.name = name;
            this.subTypes = ImmutableList.copyOf(subTypes);

            byName.put(name, this);
        }
        

        private boolean hasSubTypes()
        {
            return !subTypes.isEmpty();
        }

        /**
         * Gets the name for this type.
         */
        public String getName()
        {
            return name;
        }

        public String toString()
        {
            return name;
        }

        /**
         * Retrieves a Type instance by name,
         * if one does not exist already it creates one.
         * This can be used as intermediate measure for modders to
         * add their own Biome types.
         * <p>
         * There are <i>no</i> naming conventions besides:
         * <ul><li><b>Must</b> be all upper case (enforced by name.toUpper())</li>
         * <li><b>No</b> Special characters. {Unenforced, just don't be a pain, if it becomes a issue I WILL
         * make this RTE with no worry about backwards compatibility}</li></ul>
         * <p>
         * Note: For performance sake, the return value of this function SHOULD be cached.
         * Two calls with the same name SHOULD return the same value.
         *
         * @param name The name of this Type
         * @return An instance of Type for this name.
         */
        public static Type getType(String name, Type... subTypes)
        {
            name = name.toUpperCase();
            Type t = byName.get(name);
            if (t == null)
            {
                t = new Type(name, subTypes);
            }
            return t;
        }
        
        /**
         * @return An unmodifiable collection of all current biome types.
         */
        public static Collection<Type> getAll()
        {
            return allTypes;
        }
    }

//    private static final Map<ResourceLocation, BiomeInfo> biomeInfoMap = new HashMap<ResourceLocation, BiomeInfo>();

    public static boolean hasType(Biome biome, Type type) {
        return type.biomes.contains(biome);
    }
    
    private static class BiomeInfo
    {

        private final Set<Type> types = new HashSet<Type>();
        private final Set<Type> typesUn = Collections.unmodifiableSet(this.types);

    }

    static
    {
        registerVanillaBiomes();
    }

    /**
     * Adds the given types to the biome.
     *
     */
    public static void addTypes(Biome biome, Type... types)
    {
        for (Type type : types)
        {
            type.biomes.add(biome);
            dict.add(type);
        }

    }
    
    public static Set<Type> getTypes(Biome biome) {
        Set<Type> res = new HashSet<>();
        for(Type type : dict) {
            if(type.biomes.contains(biome)) res.add(type);
        }
        return res;
    }



    private static void registerVanillaBiomes()
    {
        addTypes(Biome.OCEAN,                            OCEAN                                                   );
        addTypes(Biome.WARM_OCEAN,                            OCEAN                                                   );
        addTypes(Biome.LUKEWARM_OCEAN,                            OCEAN                                                   );
        addTypes(Biome.DEEP_LUKEWARM_OCEAN,                            OCEAN                                                   );
        addTypes(Biome.DEEP_OCEAN,                            OCEAN                                                   );
        addTypes(Biome.COLD_OCEAN,                            OCEAN                                                   );
        addTypes(Biome.DEEP_COLD_OCEAN,                            OCEAN                                                   );

        addTypes(Biome.PLAINS,                           PLAINS                                                  );
        addTypes(Biome.DESERT,                           HOT,      DRY,        SANDY                             );
        addTypes(Biome.MOUNTAINS,                    MOUNTAIN, HILLS                                         );
        addTypes(Biome.FOREST,                           FOREST                                                  );
        addTypes(Biome.TAIGA,                            COLD,     CONIFEROUS, FOREST                            );
        addTypes(Biome.SWAMP,                        WET,      SWAMP                                         );
        addTypes(Biome.RIVER,                            RIVER                                                   );
        addTypes(Biome.NETHER,                             HOT,      DRY,        NETHER                            );
        addTypes(Biome.THE_END,                              COLD,     DRY,        END                               );
        addTypes(Biome.SMALL_END_ISLANDS,                              COLD,     DRY,        END                               );
        addTypes(Biome.END_MIDLANDS,                              COLD,     DRY,        END                               );
        addTypes(Biome.END_HIGHLANDS,                              COLD,     DRY,        END                               );
        addTypes(Biome.END_BARRENS,                              COLD,     DRY,        END                               );
        addTypes(Biome.FROZEN_OCEAN,                     COLD,     OCEAN,      SNOWY                             );
        addTypes(Biome.DEEP_FROZEN_OCEAN,                     COLD,     OCEAN,      SNOWY                             );

        addTypes(Biome.FROZEN_RIVER,                     COLD,     RIVER,      SNOWY                             );
        addTypes(Biome.SNOWY_TUNDRA,                       COLD,     SNOWY,      WASTELAND                         );
        addTypes(Biome.SNOWY_MOUNTAINS,                    COLD,     SNOWY,      MOUNTAIN                          );
        addTypes(Biome.MUSHROOM_FIELDS,                  MUSHROOM, RARE                                          );
        addTypes(Biome.MUSHROOM_FIELD_SHORE,            MUSHROOM, BEACH,      RARE                              );
        addTypes(Biome.BEACH,                            BEACH                                                   );
        addTypes(Biome.DESERT_HILLS,                     HOT,      DRY,        SANDY,    HILLS                   );
        addTypes(Biome.WOODED_HILLS,                     FOREST,   HILLS                                         );
        addTypes(Biome.TAIGA_HILLS,                      COLD,     CONIFEROUS, FOREST,   HILLS                   );
        addTypes(Biome.MOUNTAIN_EDGE,               MOUNTAIN                                                );
        addTypes(Biome.JUNGLE,                           HOT,      WET,        DENSE,    JUNGLE                  );
        addTypes(Biome.JUNGLE_HILLS,                     HOT,      WET,        DENSE,    JUNGLE,   HILLS         );
        addTypes(Biome.JUNGLE_EDGE,                      HOT,      WET,        JUNGLE,   FOREST,   RARE          );
        addTypes(Biome.DEEP_OCEAN,                       OCEAN                                                   );
        addTypes(Biome.STONE_SHORE,                      BEACH                                                   );
        addTypes(Biome.SNOWY_BEACH,                       COLD,     BEACH,      SNOWY                             );
        addTypes(Biome.BIRCH_FOREST,                     FOREST                                                  );
        addTypes(Biome.BIRCH_FOREST_HILLS,               FOREST,   HILLS                                         );
        addTypes(Biome.DARK_FOREST,                    SPOOKY,   DENSE,      FOREST                            );
        addTypes(Biome.SNOWY_TAIGA,                       COLD,     CONIFEROUS, FOREST,   SNOWY                   );
        addTypes(Biome.SNOWY_TAIGA_HILLS,                 COLD,     CONIFEROUS, FOREST,   SNOWY,    HILLS         );
        addTypes(Biome.GIANT_TREE_TAIGA,                    COLD,     CONIFEROUS, FOREST                            );
        addTypes(Biome.GIANT_TREE_TAIGA_HILLS,              COLD,     CONIFEROUS, FOREST,   HILLS                   );
        addTypes(Biome.WOODED_MOUNTAINS,         MOUNTAIN, FOREST,     SPARSE                            );
        addTypes(Biome.SAVANNA,                          HOT,      SAVANNA,    PLAINS,   SPARSE                  );
        addTypes(Biome.SAVANNA_PLATEAU,                  HOT,      SAVANNA,    PLAINS,   SPARSE,   RARE          );
        addTypes(Biome.BADLANDS,                             MESA,     SANDY,      DRY                               );
        addTypes(Biome.WOODED_BADLANDS_PLATEAU,                        MESA,     SANDY,      DRY,      SPARSE                  );
        addTypes(Biome.BADLANDS_PLATEAU,                  MESA,     SANDY,      DRY                               );
        addTypes(Biome.THE_VOID,                             VOID                                                    );
        addTypes(Biome.SUNFLOWER_PLAINS,                   PLAINS,   RARE                                          );
        addTypes(Biome.DESERT_LAKES,                   HOT,      DRY,        SANDY,    RARE                    );
        addTypes(Biome.GRAVELLY_MOUNTAINS,            MOUNTAIN, SPARSE,     RARE                              );
        addTypes(Biome.FLOWER_FOREST,                   FOREST,   HILLS,      RARE                              );
        addTypes(Biome.TAIGA_MOUNTAINS,                    COLD,     CONIFEROUS, FOREST,   MOUNTAIN, RARE          );
        addTypes(Biome.SWAMP_HILLS,                WET,      SWAMP,      HILLS,    RARE                    );
        addTypes(Biome.ICE_SPIKES,                COLD,     SNOWY,      HILLS,    RARE                    );
        addTypes(Biome.MODIFIED_JUNGLE,                   HOT,      WET,        DENSE,    JUNGLE,   MOUNTAIN, RARE);
        addTypes(Biome.MODIFIED_JUNGLE_EDGE,              HOT,      SPARSE,     JUNGLE,   HILLS,    RARE          );
        addTypes(Biome.TALL_BIRCH_FOREST,             FOREST,   DENSE,      HILLS,    RARE                    );
        addTypes(Biome.TALL_BIRCH_HILLS,       FOREST,   DENSE,      MOUNTAIN, RARE                    );
        addTypes(Biome.DARK_FOREST_HILLS,            SPOOKY,   DENSE,      FOREST,   MOUNTAIN, RARE          );
        addTypes(Biome.SNOWY_TAIGA_MOUNTAINS,               COLD,     CONIFEROUS, FOREST,   SNOWY,    MOUNTAIN, RARE);
        addTypes(Biome.GIANT_SPRUCE_TAIGA,            DENSE,    FOREST,     RARE                              );
        addTypes(Biome.GIANT_SPRUCE_TAIGA_HILLS,      DENSE,    FOREST,     HILLS,    RARE                    );
        addTypes(Biome.MODIFIED_GRAVELLY_MOUNTAINS, MOUNTAIN, SPARSE,     RARE                              );
        addTypes(Biome.SHATTERED_SAVANNA,                  HOT,      DRY,        SPARSE,   SAVANNA,  MOUNTAIN, RARE);
        addTypes(Biome.SHATTERED_SAVANNA_PLATEAU,             HOT,      DRY,        SPARSE,   SAVANNA,  HILLS,    RARE);
        addTypes(Biome.ERODED_BADLANDS,                     HOT,      DRY,        SPARSE,   MOUNTAIN, RARE          );
        addTypes(Biome.MODIFIED_WOODED_BADLANDS_PLATEAU,                HOT,      DRY,        SPARSE,   HILLS,    RARE          );
        addTypes(Biome.MODIFIED_BADLANDS_PLATEAU,          HOT,      DRY,        SPARSE,   MOUNTAIN, RARE          );

    }
}