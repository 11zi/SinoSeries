package games.moegirl.sinocraft.sinofoundation.block.entity;

import games.moegirl.sinocraft.sinocore.blockentity.BaseChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author luqin2007
 */
public class JujubeChestEntity extends BaseChestBlockEntity {

    public JujubeChestEntity(BlockPos pos, BlockState state) {
        super(SFDBlockEntities.JUJUBE_CHEST.get(), pos, state);
    }
}
