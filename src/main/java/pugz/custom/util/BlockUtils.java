package pugz.custom.util;

import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Queue;

public class BlockUtils {
    public static void bounce(Entity entity, float bounciness) {
        Vector3d vector3d = entity.getMotion();
        if (vector3d.y < 0.0D) {
            double d0 = entity instanceof LivingEntity ? bounciness : bounciness * 0.8F;
            entity.setMotion(vector3d.x, -vector3d.y * d0, vector3d.z);
        }
    }

    public static void absorb(World worldIn, BlockPos pos, List<Block> soakableBlocks) {
        Queue<Tuple<BlockPos, Integer>> queue = Lists.newLinkedList();
        queue.add(new Tuple<>(pos, 0));
        int i = 0;

        while (!queue.isEmpty()) {
            Tuple<BlockPos, Integer> tuple = queue.poll();
            BlockPos blockpos = tuple.getA();
            int j = tuple.getB();

            for (Direction direction : Direction.values()) {
                BlockPos blockpos1 = blockpos.offset(direction);
                BlockState blockstate = worldIn.getBlockState(blockpos1);
                Material material = blockstate.getMaterial();
                if (soakableBlocks.contains(blockstate.getBlock())) {
                    if (blockstate.getBlock() instanceof IBucketPickupHandler && ((IBucketPickupHandler)blockstate.getBlock()).pickupFluid(worldIn, blockpos1, blockstate) != Fluids.EMPTY) {
                        ++i;
                        if (j < 6) {
                            queue.add(new Tuple<>(blockpos1, j + 1));
                        }
                    } else if (blockstate.getBlock() instanceof FlowingFluidBlock) {
                        worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 3);
                        ++i;
                        if (j < 6) {
                            queue.add(new Tuple<>(blockpos1, j + 1));
                        }
                    } else if (material == Material.OCEAN_PLANT || material == Material.SEA_GRASS) {
                        TileEntity tileentity = blockstate.hasTileEntity() ? worldIn.getTileEntity(blockpos1) : null;
                        Block.spawnDrops(blockstate, worldIn, blockpos1, tileentity);
                        worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 3);
                        ++i;
                        if (j < 6) {
                            queue.add(new Tuple<>(blockpos1, j + 1));
                        }
                    }
                }
            }

            if (i > 64) break;
        }
    }
}