package com.modpilot.blindfilter;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.entity.EquipmentSlot;

@Mod("blindfilter")
public class BlindFilterMod {
    public static final String MODID = "blindfilter";
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MODID);

    public static final RegistryObject<Enchantment> CURSE_OF_BLINDNESS = ENCHANTMENTS.register("curse_of_contrast", 
        () -> new BlindnessCurseEnchantment());

    public BlindFilterMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ENCHANTMENTS.register(bus);
    }

    public static class BlindnessCurseEnchantment extends Enchantment {
        public BlindnessCurseEnchantment() {
            super(Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
        }

        @Override
        public int getMinCost(int level) {
            return 25;
        }

        @Override
        public int getMaxCost(int level) {
            return 50;
        }

        @Override
        public boolean isTreasureOnly() {
            return true;
        }

        @Override
        public boolean isCurse() {
            return true;
        }
    }
}