package com.orangeplane.arsenal;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(OrangePlaneArsenal.MODID)
public class OrangePlaneArsenal {
    public static final String MODID = "orangeplane_arsenal";
    public OrangePlaneArsenal() {
        ModNetwork.register();
        ModItems.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
