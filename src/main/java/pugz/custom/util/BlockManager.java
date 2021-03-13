package pugz.custom.util;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import pugz.custom.CustomMod;
import pugz.custom.block.CustomBlock;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class BlockManager {
    private static final Gson GSON_INSTANCE = getGsonBuilder().create();
    public static Map<ResourceLocation, Block> registeredBlocks = ImmutableMap.of();

    public BlockManager() {
        File data = new File(CustomMod.DATA_PATH + "blocks/");
        ImmutableMap.Builder<ResourceLocation, Block> builder = ImmutableMap.builder();

        if (!data.exists()) data.mkdirs();

        for (File file : data.listFiles()) {
            if (file.getName().endsWith(".json")) {
                String name = file.getName();
                try {
                    Reader reader = Files.newBufferedReader(Paths.get(data + "/" + name));
                    CustomBlock block = GSON_INSTANCE.fromJson(reader, CustomBlock.class);
                    builder.put(new ResourceLocation(CustomMod.MOD_ID, name.replace( ".json", "")), block);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        registeredBlocks = builder.build();
    }

    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder().registerTypeAdapter(CustomBlock.class, new CustomBlock.Serializer());
    }
}