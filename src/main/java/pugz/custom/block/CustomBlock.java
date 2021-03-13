package pugz.custom.block;

import com.google.gson.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import pugz.custom.util.BlockUtils;
import pugz.custom.util.JsonHelper;
import pugz.custom.util.StringToObject;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.util.*;

/*

TODO:
//Fixes
Fix absorbing

//Nexts
Villager Trades
Honey sliding
Fuel
brewing recipes
anvil recipes
Item properties
Item tooltip
Item expire length
canSustainPlants
transparent

//mixins
hopper_immune (item cannot go in hoppers)
ravager_breakable
vex_blocking

//laters
default BlockStates
 */

public class CustomBlock extends Block {
    public final String registryName;
    public final Item.Properties itemProperties;
    public final FireInfo fireInfo;
    public final float compostChance;
    public final float stickiness;
    public final float bounciness;
    public final OffsetType offsetType;
    public final RenderType renderType;
    public final boolean gravityAffected;
    public final int redstonePower;
    public final boolean placeableOnWater;
    public final float fallDamageFactor;
    public final boolean conduitBase;
    public final int enchantmentBonus;
    public final boolean burning;
    public final boolean climbable;
    public final int exp;
    public final boolean portalFrame;
    public final PathNodeType pathNodeType;
    public final boolean pistonSticky;
    public final boolean extendCollisionVertically;
    //public final int[] beaconColorMultiplier;
    public final boolean transparent;
    public final List<Block> absorbableBlocks;

    public CustomBlock(String registryName, AbstractBlock.Properties properties, Item.Properties itemProperties, FireInfo fireInfo,
                       float compostChance, float stickiness, float bounciness, OffsetType offsetType, RenderType renderType,
                       boolean gravityAffected, int redstonePower, boolean placeableOnWater, float fallDamageFactor,
                       boolean conduitBase, int enchantmentBonus, boolean burning, boolean climbable, int exp,
                       boolean portalFrame, PathNodeType pathNodeType, boolean pistonSticky, boolean extendCollisionVertically,
                       boolean transparent, List<Block> absorbableBlocks) {
        super(properties);
        this.registryName = registryName;
        this.itemProperties = itemProperties;
        this.fireInfo = fireInfo;
        this.compostChance = compostChance;
        this.stickiness = stickiness;
        this.bounciness = bounciness;
        this.offsetType = offsetType;
        this.renderType = renderType;
        this.gravityAffected = gravityAffected;
        this.redstonePower = redstonePower;
        this.placeableOnWater = placeableOnWater;
        this.fallDamageFactor = fallDamageFactor;
        this.conduitBase = conduitBase;
        this.enchantmentBonus = enchantmentBonus;
        this.burning = burning;
        this.climbable = climbable;
        this.exp = exp;
        this.portalFrame = portalFrame;
        this.pathNodeType = pathNodeType;
        this.pistonSticky = pistonSticky;
        this.extendCollisionVertically = extendCollisionVertically;
        this.transparent = transparent;
        this.absorbableBlocks = absorbableBlocks;

        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFireInfo(this, this.fireInfo.flammability, this.fireInfo.encouragement);

        ComposterBlock.CHANCES.put(this.asItem(), this.compostChance);
    }

    @Nonnull
    @Override
    public OffsetType getOffsetType() {
        return this.offsetType;
    }

    @Override
    public boolean isBurning(BlockState state, IBlockReader world, BlockPos pos) {
        return this.burning;
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
        return this.climbable;
    }

    @Override
    public boolean isPortalFrame(BlockState state, IBlockReader world, BlockPos pos) {
        return this.portalFrame;
    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch) {
        return this.exp;
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, IWorldReader world, BlockPos pos) {
        return this.enchantmentBonus;
    }

    @Override
    public float[] getBeaconColorMultiplier(BlockState state, IWorldReader world, BlockPos pos, BlockPos beaconPos) {
        return null;
    }

    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, MobEntity entity) {
        return this.pathNodeType;
    }

    @Override
    public boolean isStickyBlock(BlockState state) {
        return this.pistonSticky;
    }

    @Override
    public boolean collisionExtendsVertically(BlockState state, IBlockReader world, BlockPos pos, Entity collidingEntity) {
        return this.extendCollisionVertically;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isTransparent(BlockState state) {
        return this.transparent;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canProvidePower(BlockState state) {
        return this.redstonePower > 0;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return this.redstonePower;
    }

    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (entityIn.isSuppressingBounce() && this.bounciness > 0.0F) {
            super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
        } else {
            entityIn.onLivingFall(fallDistance, this.bounciness > 0.0F ? 0.0F : this.fallDamageFactor);
        }
    }

    @Override
    public boolean isConduitFrame(BlockState state, IWorldReader world, BlockPos pos, BlockPos conduit) {
        return this.conduitBase;
    }

    @SuppressWarnings("deprecation")
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (this.gravityAffected) worldIn.getPendingBlockTicks().scheduleTick(pos, this, 2);
        if (!oldState.isIn(state.getBlock())) {
            if (!this.absorbableBlocks.isEmpty()) BlockUtils.absorb(worldIn, pos, this.absorbableBlocks);
        }
    }

    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!this.absorbableBlocks.isEmpty()) BlockUtils.absorb(worldIn, pos, this.absorbableBlocks);
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (this.gravityAffected) worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 2);
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (this.gravityAffected) {
            if (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
                FallingBlockEntity fallingblockentity = new FallingBlockEntity(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
                worldIn.addEntity(fallingblockentity);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static boolean canFallThrough(BlockState state) {
        Material material = state.getMaterial();
        return state.isAir() || state.isIn(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
    }

    @SuppressWarnings("deprecation")
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (this.placeableOnWater) {
            FluidState fluidstate = worldIn.getFluidState(pos);
            FluidState fluidstate1 = worldIn.getFluidState(pos.up());
            return (fluidstate.getFluid() == Fluids.WATER || state.getMaterial() == Material.ICE) && fluidstate1.getFluid() == Fluids.EMPTY;
        } else return super.isValidPosition(state, worldIn, pos);
    }

    public void onLanded(IBlockReader worldIn, Entity entityIn) {
        if (entityIn.isSuppressingBounce()) {
            super.onLanded(worldIn, entityIn);
        } else {
            BlockUtils.bounce(entityIn, this.bounciness);
        }
    }

    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (this.stickiness > 0.0F) {
            double d0 = Math.abs(entityIn.getMotion().y);
            if (d0 < 0.1D && !entityIn.isSteppingCarefully()) {
                double d1 = 0.4D + d0 * this.stickiness;
                entityIn.setMotion(entityIn.getMotion().mul(d1, 1.0D, d1));
            }
        }
        super.onEntityWalk(worldIn, pos, entityIn);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(this.asItem());
    }

    public static class Serializer implements JsonDeserializer<CustomBlock>, JsonSerializer<CustomBlock> {
        @Override
        public CustomBlock deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = JSONUtils.getJsonObject(element, "custom block");

            String registryName = JSONUtils.getString(json, "registry_name");

            Material material;

            if (JSONUtils.isString(json, "material")) {
                material = StringToObject.material(JSONUtils.getString(json, "material"));
            } else {
                CustomMaterial customMaterial = JsonHelper.getMaterial(JSONUtils.getJsonObject(json, "material"));
                Material.Builder material$builder = new Material.Builder(customMaterial.color);

                material$builder.pushReaction = customMaterial.pushReaction;
                if (customMaterial.blocksMovement) material$builder.doesNotBlockMovement();
                if (customMaterial.flammable) material$builder.flammable();
                if (customMaterial.isLiquid) material$builder.liquid();
                if (!customMaterial.isOpaque) material$builder.notOpaque();
                if (customMaterial.replaceable) material$builder.replaceable();
                if (!customMaterial.isSolid) material$builder.notSolid();

                material = material$builder.build();
            }

            AbstractBlock.Properties properties = AbstractBlock.Properties.create(material);

            if (JSONUtils.isString(json, "properties")) {
                properties = AbstractBlock.Properties.from(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JSONUtils.getString(json, "properties"))));
            } else {
                CustomProperty customProperty = JsonHelper.getProperty(JSONUtils.getJsonObject(json, "properties"));
                properties.hardnessAndResistance(customProperty.hardness, customProperty.blastResistance);
                properties.harvestLevel(customProperty.harvestLevel);
                properties.harvestTool(customProperty.harvestTool);
                properties.jumpFactor(customProperty.jumpFactor);
                properties.speedFactor(customProperty.speedFactor);
                properties.slipperiness(customProperty.slipperiness);
                properties.sound(customProperty.soundType);
                properties.setLightLevel((state) -> {
                    return customProperty.lightLevel;
                });
                if (customProperty.dontAllowSpawn)  properties.setAllowsSpawn(CustomProperty::neverAllowSpawn);
                if (customProperty.emissiveRendering) properties.setEmmisiveRendering(CustomProperty::needsPostProcessing);
                if (customProperty.postProcessing) properties.setNeedsPostProcessing(CustomProperty::needsPostProcessing);
                if (customProperty.requiresTool) properties.setRequiresTool();
            }

            Item.Properties itemProperties = new Item.Properties();
            CustomItemProperty customItemProperty = JsonHelper.getItemProperties(JSONUtils.getJsonObject(json, "item_properties"));
            itemProperties.group(customItemProperty.group);
            itemProperties.defaultMaxDamage(customItemProperty.maxDamage);
            itemProperties.maxStackSize(customItemProperty.maxStackSize);
            itemProperties.rarity(customItemProperty.rarity);
            itemProperties.food(customItemProperty.food);
            if (customItemProperty.immuneToFire)  itemProperties.isImmuneToFire();
            if (customItemProperty.noRepair) itemProperties.setNoRepair();

            FireInfo fireInfo = JsonHelper.getFireInfo(JSONUtils.getJsonObject(json, "fire_info"));

            float compostChance = JSONUtils.getFloat(json, "compost_chance");
            float stickiness = JSONUtils.getFloat(json, "stickiness");
            float bounciness = JSONUtils.getFloat(json, "bounciness");
            String offsetType = JSONUtils.getString(json, "offset_type");
            String renderType = JSONUtils.getString(json, "render_type");
            boolean gravityAffected = JSONUtils.getBoolean(json, "gravity_affected");
            int redstonePower = JSONUtils.getInt(json, "redstone_power");
            boolean placeableOnWater = JSONUtils.getBoolean(json, "placeable_on_water");
            float fallDamageFactor = JSONUtils.getFloat(json, "fall_damage_factor");
            boolean conduitBase = JSONUtils.getBoolean(json, "conduit_base");
            int enchantmentBonus = JSONUtils.getInt(json, "enchantment_bonus");
            boolean burning = JSONUtils.getBoolean(json, "burning");
            boolean climbable = JSONUtils.getBoolean(json, "climbable");
            int exp = JSONUtils.getInt(json, "exp");
            boolean portalFrame = JSONUtils.getBoolean(json, "portal_frame");
            String pathNodeType = JSONUtils.getString(json, "path_node_type");
            boolean pistonSticky = JSONUtils.getBoolean(json, "piston_sticky");
            boolean extendCollisionVertically = JSONUtils.getBoolean(json, "extend_collision_vertically");
            boolean transparent = JSONUtils.getBoolean(json, "transparent");

            List<Block> absorbableBlocks = new ArrayList<Block>();
            JsonArray absorbableBlocksArray = JSONUtils.getJsonArray(json, "absorbable_blocks");
            for (JsonElement e : absorbableBlocksArray) {
                absorbableBlocks.add(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(e.getAsString())));
            }

            return new CustomBlock(registryName, properties, itemProperties, fireInfo, compostChance, stickiness, bounciness, StringToObject.offsetType(offsetType), StringToObject.renderType(renderType), gravityAffected, redstonePower, placeableOnWater, fallDamageFactor, conduitBase, enchantmentBonus, burning, climbable, exp, portalFrame, StringToObject.pathNodeType(pathNodeType), pistonSticky, extendCollisionVertically, transparent, absorbableBlocks);
        }

        @Override
        public JsonElement serialize(CustomBlock block, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();

            json.add("registry_name", context.serialize(block.getRegistryName()));
            json.add("material", context.serialize(block.material));
            json.add("properties", context.serialize(block.properties));
            json.add("item_properties", context.serialize(block.material));

            return json;
        }
    }

    public static class FireInfo {
        public final int flammability;
        public final int encouragement;

        public FireInfo(int flammability, int encouragement) {
            this.flammability = flammability;
            this.encouragement = encouragement;
        }
    }
}