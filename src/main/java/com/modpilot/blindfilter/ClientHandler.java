package com.modpilot.blindfilter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Method;

@Mod.EventBusSubscriber(modid = "blindfilter", value = Dist.CLIENT)
public class ClientHandler {
    private static final ResourceLocation EDGE_SHADER =
            new ResourceLocation("blindfilter", "shaders/post/edge.json");

    // GameRenderer#loadEffect is private in 1.20.1 (SRG name m_109128_)
    private static final Method LOAD_EFFECT = ObfuscationReflectionHelper.findMethod(
            GameRenderer.class, "m_109128_", ResourceLocation.class);

    private static boolean active = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getInstance();

        boolean want = false;
        if (mc.player != null) {
            ItemStack head = mc.player.getItemBySlot(EquipmentSlot.HEAD);
            want = !head.isEmpty() && EnchantmentHelper.getItemEnchantmentLevel(
                    BlindFilterMod.CURSE_OF_BLINDNESS.get(), head) > 0;
        }

        if (want && (!active || mc.gameRenderer.currentEffect() == null)) {
            try {
                LOAD_EFFECT.invoke(mc.gameRenderer, EDGE_SHADER);
                active = true;
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        } else if (!want && active) {
            mc.gameRenderer.shutdownEffect();
            active = false;
        }
    }
}