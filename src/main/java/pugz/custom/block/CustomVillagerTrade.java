package pugz.custom.block;

import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;

import javax.annotation.Nullable;
import java.util.Random;

public class CustomVillagerTrade implements VillagerTrades.ITrade {
    public final VillagerProfession profession;
    private final int emeraldCount;
    private final ItemStack sellingItem;
    private final int sellingItemCount;
    private final int maxUses;
    private final int xpValue;
    private final float priceMultiplier;

    public CustomVillagerTrade(VillagerProfession profession, int emeraldCount, Item sellingItem, int sellingItemCount, int maxUses, int xpValue, float priceMultiplier) {
        this.profession = profession;
        this.emeraldCount = emeraldCount;
        this.sellingItem = new ItemStack(sellingItem);
        this.sellingItemCount = sellingItemCount;
        this.maxUses = maxUses;
        this.xpValue = xpValue;
        this.priceMultiplier = priceMultiplier;
    }

    @Nullable
    @Override
    public MerchantOffer getOffer(Entity trader, Random rand) {
        return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCount), new ItemStack(this.sellingItem.getItem(), this.sellingItemCount), this.maxUses, this.xpValue, this.priceMultiplier);
    }
}