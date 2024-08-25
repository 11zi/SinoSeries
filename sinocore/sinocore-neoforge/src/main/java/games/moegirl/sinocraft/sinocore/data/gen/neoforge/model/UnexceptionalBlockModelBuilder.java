package games.moegirl.sinocraft.sinocore.data.gen.neoforge.model;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author qyl27
 */
public class UnexceptionalBlockModelBuilder extends BlockModelBuilder {

    public final List<Pair<String, ResourceLocation>> notExistingTexture = new ArrayList<>();

    public UnexceptionalBlockModelBuilder(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper) {
        super(outputLocation, existingFileHelper);
    }

    @Override
    public BlockModelBuilder texture(String key, ResourceLocation texture) {
        try {
            return super.texture(key, texture);
        } catch (IllegalArgumentException e) {
            notExistingTexture.add(Pair.of(key, texture));
            textures.put(key, texture.toString());
            return this;
        }
    }

    public boolean isEmpty() {
        return notExistingTexture.isEmpty();
    }

    public void forEach(Consumer<? super Pair<String, ResourceLocation>> action) {
        notExistingTexture.forEach(action);
    }
}
