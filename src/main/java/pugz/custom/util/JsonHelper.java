package pugz.custom.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.JSONUtils;
import net.minecraftforge.common.ToolType;
import pugz.custom.block.CustomBlock;
import pugz.custom.block.CustomMaterial;
import pugz.custom.block.CustomProperty;

public class JsonHelper {
    public static CustomMaterial getMaterial(JsonObject json) {
        String name = JSONUtils.getString(json, "name");
        String color = JSONUtils.getString(json, "color");
        String pushReaction = JSONUtils.getString(json, "push_reaction");
        boolean blocksMovement = JSONUtils.getBoolean(json, "blocks_movement");
        boolean flammable = JSONUtils.getBoolean(json, "flammable");
        boolean isLiquid = JSONUtils.getBoolean(json, "liquid");
        boolean isOpaque = JSONUtils.getBoolean(json, "opaque");
        boolean replaceable = JSONUtils.getBoolean(json, "replaceable");
        boolean isSolid = JSONUtils.getBoolean(json, "solid");

        CustomMaterial.registerMaterial(name, StringToObject.materialColor(color), isLiquid, isSolid, blocksMovement, isOpaque, flammable, replaceable, StringToObject.pushReaction(pushReaction));
        CustomMaterial material = CustomMaterial.MATERIAL_REGISTRY.get(name);

        if (material == null) throw new JsonSyntaxException("Unknown material '" + name + "'");
        else return material;
    }

    public static CustomProperty getProperty(JsonObject json) {
        String name = JSONUtils.getString(json, "name");
        String soundType = JSONUtils.getString(json, "sound");
        int lightLevel = JSONUtils.getInt(json, "light");
        float hardness = JSONUtils.getFloat(json, "hardness");
        float blastResistance = JSONUtils.getFloat(json, "blast_resistance");
        boolean requiresTool = JSONUtils.getBoolean(json, "requires_tool_to_break");
        boolean ticksRandomly = JSONUtils.getBoolean(json, "random_ticks");
        float slipperiness = JSONUtils.getFloat(json, "slipperiness");
        float speedFactor = JSONUtils.getFloat(json, "speed_factor");
        float jumpFactor = JSONUtils.getFloat(json, "jump_factor");
        boolean isAir = JSONUtils.getBoolean(json, "air");
        int harvestLevel = JSONUtils.getInt(json, "harvest_level");
        String harvestTool = JSONUtils.getString(json, "harvest_tool");
        boolean allowSpawn = JSONUtils.getBoolean(json, "allow_spawn");
        boolean postProcessing = JSONUtils.getBoolean(json, "post_processing");
        boolean emissiveRendering = JSONUtils.getBoolean(json, "emissive_rendering");
        boolean variableOpacity = JSONUtils.getBoolean(json, "variable_opacity");

        CustomProperty.registerProperty(name, StringToObject.soundType(soundType), lightLevel, blastResistance, hardness, requiresTool, ticksRandomly, slipperiness, speedFactor, jumpFactor, isAir, harvestLevel, ToolType.get(harvestTool), allowSpawn, postProcessing, emissiveRendering, variableOpacity);
        CustomProperty property = CustomProperty.PROPERTY_REGISTRY.get(name);

        if (property == null) throw new JsonSyntaxException("Unknown property '" + name + "'");
        else return property;
    }

    public static CustomBlock.FireInfo getFireInfo(JsonObject json) {
        int flammability = JSONUtils.getInt(json, "flammability");
        int encouragement = JSONUtils.getInt(json, "flammability");
        return new CustomBlock.FireInfo(flammability, encouragement);
    }
}