package pugz.custom.util;

import net.minecraft.block.Block;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import pugz.custom.CustomMod;
import pugz.custom.block.CustomBlock;

@Mod.EventBusSubscriber(modid = CustomMod.MOD_ID)
public class Events {
    @SubscribeEvent
    public static void onWandererTrades(WandererTradesEvent event) {
        for (Block block : CustomMod.blockManager.BLOCK_REGISTRY.values()) {
            CustomBlock custom = (CustomBlock) block;
            if (custom.merchantTrades != null) custom.merchantTrades.forEach((trade -> {
                event.getGenericTrades().add(trade);
            }));
            if (custom.rareMerchantTrades != null) custom.rareMerchantTrades.forEach((trade -> {
                event.getRareTrades().add(trade);
            }));
        }
    }

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        for (Block block : CustomMod.blockManager.BLOCK_REGISTRY.values()) {
            CustomBlock custom = (CustomBlock) block;
            if (custom.villagerTrades != null) custom.villagerTrades.forEach((trade -> {
                if (trade.profession == event.getType()) {
                    //event.getTrades().put(1, trade);
                }
            }));
        }
    }
}