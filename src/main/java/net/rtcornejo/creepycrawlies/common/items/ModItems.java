package net.rtcornejo.creepycrawlies.common.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.rtcornejo.creepycrawlies.CreepyCrawliesMod;

public class ModItems {
    public static final DeferredRegister<Item>ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CreepyCrawliesMod.MOD_ID);

    public static final RegistryObject<Item> WORMSTICK = ITEMS.register("wormstick",
            () -> new WormstickItem(new Item.Properties()));
    public static final RegistryObject<Item> WORM = ITEMS.register("worm",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
