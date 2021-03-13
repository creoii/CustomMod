package pugz.custom.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import java.util.HashMap;
import java.util.Map;

public class CustomProperty {
    public static final Map<String, CustomProperty> PROPERTY_REGISTRY = new HashMap<>();

    public final String name;
    public final SoundType soundType;
    public final int lightLevel;
    public final float hardness;
    public final float blastResistance;
    public final boolean requiresTool;
    public final boolean ticksRandomly;
    public final float slipperiness;
    public final float speedFactor;
    public final float jumpFactor;
    public final boolean isAir;
    public final int harvestLevel;
    public final ToolType harvestTool;
    public final boolean dontAllowSpawn;
    public final boolean postProcessing;
    public final boolean emissiveRendering;
    public final boolean variableOpacity;

    public CustomProperty(String name, SoundType soundType, int lightLevel, float hardness, float blastResistance,
                            boolean requiresTool, boolean ticksRandomly, float slipperiness, float speedFactor, float jumpFactor,
                            boolean isAir, int harvestLevel, ToolType harvestTool, boolean dontAllowSpawn,
                            boolean postProcessing, boolean emissiveRendering, boolean variableOpacity) {
        this.name = name;
        this.soundType = soundType;
        this.lightLevel = lightLevel;
        this.hardness = hardness;
        this.blastResistance = blastResistance;
        this.requiresTool = requiresTool;
        this.ticksRandomly = ticksRandomly;
        this.slipperiness = slipperiness;
        this.speedFactor = speedFactor;
        this.jumpFactor = jumpFactor;
        this.isAir = isAir;
        this.harvestLevel = harvestLevel;
        this.harvestTool = harvestTool;
        this.dontAllowSpawn = dontAllowSpawn;
        this.postProcessing = postProcessing;
        this.emissiveRendering = emissiveRendering;
        this.variableOpacity = variableOpacity;
    }

    public static void registerProperty(String name, SoundType soundType, int lightLevel, float hardness, float blastResistance, boolean requiresTool, boolean ticksRandomly, float slipperiness, float speedFactor, float jumpFactor, boolean isAir, int harvestLevel, ToolType harvestTool, boolean dontAllowSpawn, boolean postProcessing, boolean emissiveRendering, boolean variableOpacity) {
        PROPERTY_REGISTRY.put(name, new CustomProperty(name, soundType, lightLevel, hardness, blastResistance, requiresTool, ticksRandomly, slipperiness, speedFactor, jumpFactor, isAir, harvestLevel, harvestTool, dontAllowSpawn, postProcessing, emissiveRendering, variableOpacity));
    }

    public static boolean needsPostProcessing(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    public static Boolean neverAllowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
        return false;
    }
}