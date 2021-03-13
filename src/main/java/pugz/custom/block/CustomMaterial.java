package pugz.custom.block;

import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public class CustomMaterial {
    public final MaterialColor color;
    public final PushReaction pushReaction;
    public final boolean blocksMovement;
    public final boolean flammable;
    public final boolean isLiquid;
    public final boolean isOpaque;
    public final boolean replaceable;
    public final boolean isSolid;

    public CustomMaterial(MaterialColor color, boolean isLiquid, boolean isSolid, boolean blocksMovement, boolean isOpaque, boolean flammable, boolean replaceable, PushReaction pushReaction) {
        this.color = color;
        this.isLiquid = isLiquid;
        this.isSolid = isSolid;
        this.blocksMovement = blocksMovement;
        this.isOpaque = isOpaque;
        this.flammable = flammable;
        this.replaceable = replaceable;
        this.pushReaction = pushReaction;
    }
}