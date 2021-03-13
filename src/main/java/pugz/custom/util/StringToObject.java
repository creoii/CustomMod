package pugz.custom.util;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;

public class StringToObject {
    public static Material material(String str) {
        switch (str) {
            default:
                return Material.IRON;
        }
    }

    public static MaterialColor materialColor(String str) {
        switch (str) {
            default:
                return MaterialColor.ADOBE;
        }
    }

    public static PushReaction pushReaction(String str) {
        switch (str) {
            default:
            case "normal":
                return PushReaction.NORMAL;
            case "destroy":
                return PushReaction.DESTROY;
            case "block":
                return PushReaction.BLOCK;
            case "ignore":
                return PushReaction.IGNORE;
            case "push_only":
                return PushReaction.PUSH_ONLY;
        }
    }

    public static SoundType soundType(String str) {
        switch (str) {
            default:
                return SoundType.ANCIENT_DEBRIS;
        }
    }

    public static AbstractBlock.OffsetType offsetType(String str) {
        switch (str) {
            default:
                return AbstractBlock.OffsetType.NONE;
        }
    }

    public static RenderType renderType(String str) {
        switch (str) {
            default:
                return RenderType.getSolid();
        }
    }
}