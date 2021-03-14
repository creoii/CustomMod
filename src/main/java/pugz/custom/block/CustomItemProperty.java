package pugz.custom.block;

import net.minecraft.item.Food;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class CustomItemProperty {

    public final ItemGroup group;
    public final int maxStackSize;
    public final int maxDamage;
    public final Rarity rarity;
    public final Food food;
    public final boolean immuneToFire;
    public final boolean noRepair;

    public CustomItemProperty(ItemGroup group, int maxStackSize, int maxDamage, Rarity rarity, Food food, boolean immuneToFire, boolean noRepair) {
        this.group = group;
        this.maxStackSize = maxStackSize;
        this.maxDamage = maxDamage;
        this.rarity = rarity;
        this.food = food;
        this.immuneToFire = immuneToFire;
        this.noRepair = noRepair;
    }
}