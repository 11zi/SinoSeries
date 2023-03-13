package games.moegirl.sinocraft.sinocore.mixin;

import games.moegirl.sinocraft.sinocore.Unrealized;
import games.moegirl.sinocraft.sinocore.old.IBlockProperties;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.Properties.class)
@Unrealized
public abstract class MixinBlockProperties implements IBlockProperties {

    @Override
    @Accessor
    public abstract float getDestroyTime();

    @Override
    @Accessor
    public abstract float getExplosionResistance();
}