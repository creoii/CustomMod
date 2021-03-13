package pugz.custom;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import pugz.custom.block.CustomBlock;
import pugz.custom.util.BlockManager;

@Mod(CustomMod.MOD_ID)
@Mod.EventBusSubscriber(modid = CustomMod.MOD_ID)
public class CustomMod {
    public static final String MOD_ID = "custom";
    public static final String DATA_PATH = "./data/Custom/";
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public CustomMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        BlockManager manager = new BlockManager();

        for (Block block : manager.registeredBlocks.values()) {
            CustomBlock custom = (CustomBlock) block;
            BLOCKS.register(custom.registryName, () -> custom);
            ITEMS.register(custom.registryName, () -> new BlockItem(custom, custom.itemProperties));
        }

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            eventBus.addListener(EventPriority.LOWEST, this::clientSetup);
        });
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        for (Block block : BlockManager.registeredBlocks.values()) {
            CustomBlock custom = (CustomBlock) block;
            if (custom.renderType != RenderType.getSolid()) RenderTypeLookup.setRenderLayer(custom, custom.renderType);
        }
    }
}