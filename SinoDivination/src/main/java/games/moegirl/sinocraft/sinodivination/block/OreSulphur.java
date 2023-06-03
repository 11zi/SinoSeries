package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootTable;

public class OreSulphur extends DropExperienceBlock implements ILootableBlock {

    public OreSulphur() {
        super(Properties.of(Material.STONE)
                .requiresCorrectToolForDrops()
                .explosionResistance(3.0F), UniformInt.of(2, 5));
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables helper) {
        return helper.createOreDrops(this, SDItems.SULPHUR.get(), 1, 2);
    }
}