package me.solar.infectionMinigame.menus;

import me.solar.apolloLibrary.menus.Menu;
import me.solar.apolloLibrary.menus.MenuAction;
import me.solar.apolloLibrary.menus.MenuEvent;
import me.solar.apolloLibrary.menus.MenuItem;
import me.solar.apolloLibrary.utils.Common;
import me.solar.apolloLibrary.utils.ItemStackUtils;
import me.solar.infectionMinigame.data.GameMap;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RepairableBarricadeMenu extends Menu {

    public static final RepairableBarricadeMenu INSTANCE = new RepairableBarricadeMenu();

    protected RepairableBarricadeMenu() {
        super(Component.text("Repair Barricade"), 18);
    }

    @Override
    protected void initialize() {

        /// Create Barricade
        MenuItem createBarricade = new MenuItem(ItemStackUtils.createItemStack(Material.PAPER, "Create Barricade"));
        createBarricade.withOnClick(event -> {
            event.player().closeInventory();
        });
        setItem(4, createBarricade);


    }
}
