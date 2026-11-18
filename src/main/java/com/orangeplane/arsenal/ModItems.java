package com.orangeplane.arsenal;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// Reserved for future custom items; all gear currently uses vanilla item types.
public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, OrangePlaneArsenal.MODID);
    public static void register(IEventBus bus) { ITEMS.register(bus); }
}
