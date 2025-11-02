package me.solar.infectionMinigame.menus;

import me.solar.apolloLibrary.menus.Menu;
import me.solar.apolloLibrary.menus.MenuAction;
import me.solar.apolloLibrary.menus.MenuEvent;
import me.solar.apolloLibrary.menus.MenuItem;
import me.solar.apolloLibrary.utils.Common;
import me.solar.apolloLibrary.utils.ItemStackUtils;
import me.solar.infectionMinigame.data.GameMap;
import me.solar.infectionMinigame.dialogs.Dialogs;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EditorMenu extends Menu {

    private GameMap editorMap;

    public EditorMenu(GameMap editorMap) {
        super("Editor Menu", 54);
        this.editorMap = editorMap;
    }

    @Override
    protected void initialize() {
        MenuItem mapName = new MenuItem(ItemStackUtils.createItemStack(Material.PAPER, editorMap.getName()));
        setItem(4, mapName);

        String region = "<yellow>" +editorMap.getRegion().getPointA().getBlock().getLocation().toVector() + " <green>to <yellow>" + editorMap.getRegion().getPointB().getBlock().getLocation().toVector() + ", <green> in world<yellow>: " + editorMap.getRegion().getWorld().getName();
        MenuItem mapRegion = new MenuItem(ItemStackUtils.createItemStack(Material.MAP, "Map Region", "Region: " + region));
        mapRegion.withOnClick(event -> Common.tell(event.player(), "<red>Map region changing coming soon"));
        setItem(13, mapRegion);

        MenuItem editValues = new MenuItem(ItemStackUtils.createItemStack(Material.COMPASS, "<no-italic>Edit Values", "Edit the values of the map"));
        editValues.withOnClick(event -> {
            event.player().showDialog(Dialogs.EditorDialog(editorMap));
        });
        setItem(40, editValues);

        MenuItem saveMap = new MenuItem(ItemStackUtils.createItemStack(Material.WRITABLE_BOOK, "Save Map", "Save the current map"));
        saveMap.withOnClick(event -> {
            editorMap.save();
            Common.tell(event.player(), "<green>Map saved!");
        });
        setItem(49, saveMap);
    }
}
