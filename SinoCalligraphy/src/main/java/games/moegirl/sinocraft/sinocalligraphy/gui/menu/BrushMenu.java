package games.moegirl.sinocraft.sinocalligraphy.gui.menu;

import games.moegirl.sinocraft.sinocalligraphy.gui.SCAMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class BrushMenu extends AbstractContainerMenu {
    protected Inventory playerInv;
    protected Player player;
    protected ItemStack brush;

    public BrushMenu(int id, Inventory playerInv, Player player, ItemStack brush) {
        super(SCAMenus.BRUSH.get(), id);

        this.playerInv = playerInv;
        this.player = player;
        this.brush = brush;
    }

    public BrushMenu(int id, Inventory playerInv, FriendlyByteBuf buf) {
        this(id, playerInv, playerInv.player, buf.readItem());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        // Todo: qyl27.
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        // Todo: qyl27.
        return false;
    }
}