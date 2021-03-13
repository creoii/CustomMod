package pugz.custom.block;

import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

import java.util.HashMap;
import java.util.Map;

public class CustomMaterial {
    public static final Map<String, CustomMaterial> MATERIAL_REGISTRY = new HashMap<>();

    public final String name;
    public final MaterialColor color;
    public final PushReaction pushReaction;
    public final boolean blocksMovement;
    public final boolean flammable;
    public final boolean isLiquid;
    public final boolean isOpaque;
    public final boolean replaceable;
    public final boolean isSolid;

    private CustomMaterial(String name, MaterialColor color, boolean isLiquid, boolean isSolid, boolean blocksMovement, boolean isOpaque, boolean flammable, boolean replaceable, PushReaction pushReaction) {
        this.name = name;
        this.color = color;
        this.isLiquid = isLiquid;
        this.isSolid = isSolid;
        this.blocksMovement = blocksMovement;
        this.isOpaque = isOpaque;
        this.flammable = flammable;
        this.replaceable = replaceable;
        this.pushReaction = pushReaction;
    }

    public static void registerMaterial(String name, MaterialColor color, boolean isLiquid, boolean isSolid, boolean blocksMovement, boolean isOpaque, boolean flammable, boolean replaceable, PushReaction pushReaction) {
        MATERIAL_REGISTRY.put(name, new CustomMaterial(name, color, isLiquid, isSolid, blocksMovement, isOpaque, flammable, replaceable, pushReaction));
    }
}