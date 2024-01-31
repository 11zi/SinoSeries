package games.moegirl.sinocraft.sinocore.registry.forge;

import games.moegirl.sinocraft.sinocore.SinoCorePlatform;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.registry.IDataProviderRegister;
import games.moegirl.sinocraft.sinocore.util.Reference;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ForgeDataProviderRegisterImpl implements IDataProviderRegister, Consumer<GatherDataEvent> {

    private final List<Function<IDataGenContext, DataProvider>> providers = new ArrayList<>();

    public ForgeDataProviderRegisterImpl() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this);
    }

    @Override
    public <T extends DataProvider> Supplier<T> put(Function<IDataGenContext, ? extends T> builder) {
        Reference<T> reference = new Reference<>();
        providers.add(ctx -> {
            T provider = builder.apply(ctx);
            reference.set(provider);
            return provider;
        });
        return reference;
    }

    @Override
    public void accept(GatherDataEvent event) {
        IDataGenContext context = SinoCorePlatform.buildDataGeneratorContext(event, event.getLookupProvider());
        DataGenerator generator = event.getGenerator();
        providers.forEach(builder -> generator.addProvider(true, builder.apply(context)));
    }
}
