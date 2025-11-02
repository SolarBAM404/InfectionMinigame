package me.solar.infectionMinigame.menus;

import lombok.Getter;
import me.solar.apolloLibrary.menus.Menu;
import me.solar.apolloLibrary.menus.MenuItem;
import me.solar.apolloLibrary.utils.ItemStackUtils;
import me.solar.infectionMinigame.ConfigManager;
import me.solar.infectionMinigame.data.GameMap;
import me.solar.infectionMinigame.data.StaticMapConfig;
import org.bukkit.Material;

public class MapMenu extends Menu {

    @Getter
    private static final MapMenu instance = new MapMenu();

    private MapMenu() {
        super("Game Maps", 54);
    }

    @Override
    protected void initialize() {
        int index = 0;

        for (GameMap map : StaticMapConfig.MAPS) {
            MenuItem item = new MenuItem(ItemStackUtils.createItemStack(Material.OXIDIZED_LIGHTNING_ROD, map.getName(), "Click to edit this map"));
            item.withOnClick(event -> {
               EditorMenu menu = new EditorMenu(map);
               menu.open(event.player());
            });
            setItem(index, item);
            index += 2;
        }
    }
}
