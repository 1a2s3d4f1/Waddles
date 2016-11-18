package com.girafi.waddles;

import biomesoplenty.api.biome.BOPBiomes;
import com.girafi.waddles.entity.EntityAdeliePenguin;
import com.girafi.waddles.proxy.CommonProxy;
import com.girafi.waddles.utils.ConfigurationHandler;
import com.girafi.waddles.utils.Reference;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.io.File;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY_CLASS)
public class Waddles {
    @Mod.Instance(Reference.MOD_ID)
    public static Waddles instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_ClASS)
    public static CommonProxy proxy;

    public static final ResourceLocation LOOT_ENTITIES_PENGUIN_FISH = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/penguin"));

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "Waddles.cfg"));
        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
        proxy.registerRenders();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ResourceLocation resourceLocation = new ResourceLocation(Reference.MOD_ID, "adelie_penguin");
        EntityRegistry.registerModEntity(resourceLocation, EntityAdeliePenguin.class, resourceLocation.toString(), 0, Waddles.instance, 64, 1, true, 0x000000, 0xFFFFFF);
        this.addPenguinSpawn(Biomes.ICE_PLAINS, Biomes.MUTATED_ICE_FLATS, Biomes.ICE_MOUNTAINS, Biomes.FROZEN_OCEAN, Biomes.COLD_BEACH);
        if (Reference.IS_BOP_LOADED) {
            if (BOPBiomes.glacier.isPresent()) {
                addPenguinSpawn(BOPBiomes.glacier.get());
            }
            if (BOPBiomes.cold_desert.isPresent()) {
                addPenguinSpawn(BOPBiomes.cold_desert.get());
            }
            if (BOPBiomes.gravel_beach.isPresent()) {
                addPenguinSpawn(BOPBiomes.gravel_beach.get());
            }
        }
    }

    private void addPenguinSpawn(Biome... biomes) {
        EntityRegistry.addSpawn(EntityAdeliePenguin.class, 2, 2, 5, EnumCreatureType.CREATURE, biomes);
    }
}