package com.orangeplane.arsenal;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = OrangePlaneArsenal.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyRegistration {
    public static final KeyMapping SPAWN_CHEST = new KeyMapping(
        "key.orangeplane_arsenal.spawn_chest", InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_K, "key.categories.orangeplane_arsenal");

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        event.register(SPAWN_CHEST);
    }
}
