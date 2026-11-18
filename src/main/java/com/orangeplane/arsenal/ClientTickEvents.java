package com.orangeplane.arsenal;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OrangePlaneArsenal.MODID, value = Dist.CLIENT)
public class ClientTickEvents {
    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            while (KeyRegistration.SPAWN_CHEST.consumeClick()) ModNetwork.requestChest();
        }
    }
}
