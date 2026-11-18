package com.orangeplane.arsenal;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ModNetwork {
    private static final String PROTOCOL = "1";
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(OrangePlaneArsenal.MODID, "main"),
        () -> PROTOCOL, NetworkRegistry.acceptMissingOr(PROTOCOL), NetworkRegistry.acceptMissingOr(PROTOCOL));

    public static void register() {
        CHANNEL.messageBuilder(SpawnChestPacket.class, 0)
            .encoder((msg, buf) -> {})
            .decoder(buf -> new SpawnChestPacket())
            .consumerMainThread(SpawnChestPacket::handle)
            .add();
    }

    public static void requestChest() {
        CHANNEL.sendToServer(new SpawnChestPacket());
    }

    public static class SpawnChestPacket {
        static void handle(SpawnChestPacket msg, Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context ctx = supplier.get();
            ServerPlayer player = ctx.getSender();
            if (player != null) {
                ctx.enqueueWork(() -> {
                    if (!"OrangePlane".equalsIgnoreCase(player.getGameProfile().getName())) return;
                    Direction facing = player.getDirection();
                    BlockPos pos = player.blockPosition().relative(facing);
                    if (!player.serverLevel().getBlockState(pos).canBeReplaced()) return;
                    player.serverLevel().setBlock(pos, Blocks.CHEST.defaultBlockState(), 3);
                    if (player.serverLevel().getBlockEntity(pos) instanceof ChestBlockEntity chest) {
                        chest.getPersistentData().putString("op_owner", player.getUUID().toString());
                        fill(chest);
                        chest.setChanged();
                    }
                });
            }
            ctx.setPacketHandled(true);
        }

        private static void fill(ChestBlockEntity chest) {
            ItemStack helmet = enchanted(Items.NETHERITE_HELMET, new int[][]{
                {0,4},{1,3},{2,1},{3,1},{4,255}
            });
            ItemStack chestplate = enchanted(Items.NETHERITE_CHESTPLATE, new int[][]{{0,4},{1,3},{2,1},{3,1},{4,255}});
            ItemStack leggings = enchanted(Items.NETHERITE_LEGGINGS, new int[][]{{0,4},{1,3},{2,1},{3,1},{4,255}});
            ItemStack boots = enchanted(Items.NETHERITE_BOOTS, new int[][]{{0,4},{1,3},{2,1},{3,1},{4,255}});
            chest.setItem(0, helmet); chest.setItem(1, chestplate);
            chest.setItem(2, leggings); chest.setItem(3, boots);

            ItemStack bow = enchanted(Items.BOW, new int[][]{{5,255},{6,1},{7,1},{8,255},{4,255}});
            chest.setItem(4, bow);
            ItemStack sword = enchanted(Items.NETHERITE_SWORD, new int[][]{{9,255},{10,3},{11,2},{4,255}});
            sword.getOrCreateTag().putBoolean("Unbreakable", true);
            chest.setItem(5, sword);

            ItemStack rod = enchanted(Items.FISHING_ROD, new int[][]{{12,255},{13,255},{4,255},{14,1}});
            rod.getOrCreateTag().putBoolean("Unbreakable", true);
            chest.setItem(6, rod);

            ItemStack pick = enchanted(Items.NETHERITE_PICKAXE, new int[][]{{15,255},{4,255},{14,1}});
            ItemStack axe = enchanted(Items.NETHERITE_AXE, new int[][]{{15,255},{4,255},{14,1}});
            ItemStack shovel = enchanted(Items.NETHERITE_SHOVEL, new int[][]{{15,255},{4,255},{14,1}});
            pick.getOrCreateTag().putBoolean("Unbreakable", true);
            axe.getOrCreateTag().putBoolean("Unbreakable", true);
            shovel.getOrCreateTag().putBoolean("Unbreakable", true);
            chest.setItem(7, pick); chest.setItem(8, axe); chest.setItem(9, shovel);

            ItemStack shield = new ItemStack(Items.SHIELD);
            ench(shield, Enchantments.UNBREAKING, 255);
            ench(shield, Enchantments.MENDING, 1);
            shield.getOrCreateTag().putBoolean("Unbreakable", true);
            chest.setItem(10, shield);
            chest.setItem(11, new ItemStack(Items.ARROW, 64));
        }

        /**
         * ItemStack.enchant() en 1.20.1 convierte el nivel a byte (máx. 127),
         * así que 255 se guardaba como nivel 0. Aquí se escribe el NBT directo.
         */
        private static void ench(ItemStack stack, Enchantment enchantment, int level) {
            CompoundTag tag = stack.getOrCreateTag();
            if (!tag.contains("Enchantments", Tag.TAG_LIST)) tag.put("Enchantments", new ListTag());
            ListTag list = tag.getList("Enchantments", Tag.TAG_COMPOUND);
            list.add(EnchantmentHelper.storeEnchantment(EnchantmentHelper.getEnchantmentId(enchantment), level));
        }

        private static ItemStack enchanted(net.minecraft.world.item.Item item, int[][] enchants) {
            ItemStack stack = new ItemStack(item);
            for (int[] pair : enchants) {
                switch (pair[0]) {
                    case 0 -> ench(stack, Enchantments.ALL_DAMAGE_PROTECTION, pair[1]);
                    case 1 -> ench(stack, Enchantments.UNBREAKING, pair[1]);
                    case 2 -> ench(stack, Enchantments.MENDING, pair[1]);
                    case 3 -> ench(stack, Enchantments.THORNS, pair[1]);
                    case 4 -> stack.getOrCreateTag().putBoolean("Unbreakable", true);
                    case 5 -> ench(stack, Enchantments.POWER_ARROWS, pair[1]);
                    case 6 -> ench(stack, Enchantments.INFINITY_ARROWS, pair[1]);
                    case 7 -> ench(stack, Enchantments.FLAMING_ARROWS, pair[1]);
                    case 8 -> ench(stack, Enchantments.PUNCH_ARROWS, pair[1]);
                    case 9 -> ench(stack, Enchantments.SHARPNESS, pair[1]);
                    case 10 -> ench(stack, Enchantments.MOB_LOOTING, pair[1]);
                    case 11 -> ench(stack, Enchantments.FIRE_ASPECT, pair[1]);
                    case 12 -> ench(stack, Enchantments.FISHING_LUCK, pair[1]);
                    case 13 -> ench(stack, Enchantments.FISHING_SPEED, pair[1]);
                    case 14 -> ench(stack, Enchantments.MENDING, pair[1]);
                    case 15 -> ench(stack, Enchantments.BLOCK_EFFICIENCY, pair[1]);
                }
            }
            return stack;
        }
    }
}
