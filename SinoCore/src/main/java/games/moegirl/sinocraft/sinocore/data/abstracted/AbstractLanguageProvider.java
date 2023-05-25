package games.moegirl.sinocraft.sinocore.data.abstracted;

import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Map;
import java.util.TreeMap;

public abstract class AbstractLanguageProvider extends LanguageProvider {
    protected final String modid;
    protected final String locale;
    // 保存一份副本，用于后期校验
    protected final Map<String, String> data;

    public AbstractLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);

        this.modid = modid;
        this.locale = locale;

        this.data = new TreeMap<>();
    }

    @Override
    protected void addTranslations() {
        addTreeTranslates();
        translate();
    }

    public abstract void translate();

    protected void addTreeTranslates() {
        var trees = TreeRegistry.getRegistry().get(modid);

        if (trees != null && !trees.isEmpty()) {
            for (var tree : trees) {
                tree.getTranslator().addTranslatesForLocale(locale, tree, this);
            }
        }
    }

    /**
     * 对 CreativeTab 的支持
     *
     * @param tab       tab
     * @param translate 名称
     */
    protected void addTab(ResourceLocation tab, String translate) {
        add("tab." + modid + "." + tab.getPath(), translate);
    }

    /**
     * 校验某名称是否被添加
     * @param languageId 被校验语言名称
     * @param type 语言类型
     */
    protected void verifyKey(String languageId, String type) {
        if (!data.containsKey(languageId)) {
            LOGGER.warn("Not found {} language {} ( {} )", locale, languageId, type);
        }
    }

    @Override
    public void add(String key, String value) {
        super.add(key, value);
        data.put(key, value);
    }
}
