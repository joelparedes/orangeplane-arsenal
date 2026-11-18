package com.orangeplane.arsenal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OrangePlaneArsenal.MODID)
public class ProtectionEvents {
    private static boolean isForeignOwnedChest(BlockEntity be, net.minecraft.world.entity.player.Player player) {
        if (be == null || !be.getPersistentData().contains("op_owner")) return false;
        String owner = be.getPersistentData().getString("op_owner");
        return !owner.equals(player.getUUID().toString());
    }

    @SubscribeEvent
    public static void rightClick(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) return;
        BlockPos pos = event.getPos();
        if (isForeignOwnedChest(event.getLevel().getBlockEntity(pos), event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void breakBlock(BlockEvent.BreakEvent event) {
        if (isForeignOwnedChest(event.getLevel().getBlockEntity(event.getPos()), event.getPlayer())) {
            event.setCanceled(true);
        }
    }
}
