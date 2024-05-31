package games.moegirl.sinocraft.sinocore.mixin;

import games.moegirl.sinocraft.sinocore.mixin_interfaces.injectable.ITabItemProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Mixin(Item.Properties.class)
public abstract class ItemPropertiesExtension implements ITabItemProperties {

    @Unique
    private final List<Pair<ResourceKey<CreativeModeTab>, Function<ItemLike, ItemStack>>> sino$tabs = new ArrayList<>();
    @Unique
    private final Map<ResourceKey<CreativeModeTab>, Function<ItemLike, ItemStack>> sino$tabIcons = new HashMap<>();

    @Override
    public Item.Properties sino$tab(ResourceKey<CreativeModeTab> tab) {
        sino$tabs.add(Pair.of(tab, ItemStack::new));
        return sino$getThis();
    }

    @Override
    public Item.Properties sino$tab(ResourceKey<CreativeModeTab> tab, Function<ItemLike, ItemStack> sup) {
        sino$tabs.add(Pair.of(tab, sup));
        return sino$getThis();
    }

    @Override
    public Item.Properties sino$tabIcon(ResourceKey<CreativeModeTab> tab) {
        sino$tabIcons.put(tab, ItemStack::new);
        return sino$getThis();
    }

    @Override
    public Item.Properties sino$tabIcon(ResourceKey<CreativeModeTab> tab, Function<ItemLike, ItemStack> sup) {
        sino$tabIcons.put(tab, sup);
        return sino$getThis();
    }

    @Override
    public List<Pair<ResourceKey<CreativeModeTab>, Function<ItemLike, ItemStack>>> sino$getTabs() {
        return sino$tabs;
    }

    @Override
    public Map<ResourceKey<CreativeModeTab>, Function<ItemLike, ItemStack>> sino$getTabIcon() {
        return sino$tabIcons;
    }

    @Unique
    private Item.Properties sino$getThis() {
        return (Item.Properties) (Object) this;
    }
}
