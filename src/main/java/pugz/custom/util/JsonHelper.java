package pugz.custom.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.*;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ForgeRegistries;
import pugz.custom.block.*;

public class JsonHelper {
    public static CustomMaterial getMaterial(JsonObject json) {
        String color = JSONUtils.getString(json, "color");
        String pushReaction = JSONUtils.getString(json, "push_reaction");
        boolean blocksMovement = JSONUtils.getBoolean(json, "blocks_movement");
        boolean flammable = JSONUtils.getBoolean(json, "flammable");
        boolean isLiquid = JSONUtils.getBoolean(json, "liquid");
        boolean isOpaque = JSONUtils.getBoolean(json, "opaque");
        boolean replaceable = JSONUtils.getBoolean(json, "replaceable");
        boolean isSolid = JSONUtils.getBoolean(json, "solid");

        return new CustomMaterial(StringToObject.materialColor(color), isLiquid, isSolid, blocksMovement, isOpaque, flammable, replaceable, StringToObject.pushReaction(pushReaction));
    }

    public static CustomProperty getProperty(JsonObject json) {
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

        return new CustomProperty(StringToObject.soundType(soundType), lightLevel, blastResistance, hardness, requiresTool, ticksRandomly, slipperiness, speedFactor, jumpFactor, isAir, harvestLevel, ToolType.get(harvestTool), allowSpawn, postProcessing, emissiveRendering, variableOpacity);
    }

    public static CustomItemProperty getItemProperties(JsonObject json) {
        String itemGroup = JSONUtils.getString(json, "item_group");
        ItemGroup itemGroup1 = ItemGroup.SEARCH;
        for (ItemGroup group : ItemGroup.GROUPS) {
            if (itemGroup.equals(group.tabLabel)) itemGroup1 = group;
        }

        int maxStackSize = JSONUtils.getInt(json, "max_stack_size");
        int defaultMaxDamage = JSONUtils.getInt(json, "max_damage");
        Rarity rarity = StringToObject.rarity(JSONUtils.getString(json, "rarity"));
        Food food = StringToObject.food(JSONUtils.getString(json, "food"));
        boolean fireImmune = JSONUtils.getBoolean(json, "fire_immune");
        boolean noRepair = JSONUtils.getBoolean(json, "no_repair");

        return new CustomItemProperty(itemGroup1, maxStackSize, defaultMaxDamage, rarity, food, fireImmune, noRepair);
    }

    public static CustomBlock.FireInfo getFireInfo(JsonObject json) {
        int flammability = JSONUtils.getInt(json, "flammability");
        int encouragement = JSONUtils.getInt(json, "encouragement");
        return new CustomBlock.FireInfo(flammability, encouragement);
    }

    public static CustomVillagerTrade getVillagerTrade(JsonElement json) {
        String profession = JSONUtils.getString(json, "profession");
        int emeraldCount = JSONUtils.getInt(json, "emerald_count");
        Item sellingItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getString(json, "selling_item")));
        int sellingItemCount = JSONUtils.getInt(json, "selling_item_count");
        int maxUses = JSONUtils.getInt(json, "max_uses");
        int xpValue = JSONUtils.getInt(json, "xp_value");
        float priceMultiplier = JSONUtils.getFloat(json, "price_multiplier");
        return new CustomVillagerTrade(StringToObject.profession(profession), emeraldCount, sellingItem, sellingItemCount, maxUses, xpValue, priceMultiplier);
    }
}