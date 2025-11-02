package me.solar.infectionMinigame.tools;

import lombok.Getter;
import me.solar.apolloLibrary.tools.RegionSelectorTool;
import me.solar.apolloLibrary.utils.Common;
import me.solar.apolloLibrary.utils.ItemStackUtils;
import me.solar.apolloLibrary.utils.PlayerUtils;
import me.solar.apolloLibrary.world.CuboidRegion;
import me.solar.infectionMinigame.data.GameMap;
import me.solar.infectionMinigame.dialogs.Dialogs;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ArenaSelectionTool extends RegionSelectorTool {

    @Getter
    private static final ArenaSelectionTool instance = new ArenaSelectionTool();

    @Override
    public ItemStack getItem() {
        return ItemStackUtils.createItemStack(Material.NETHER_STAR, "Arena Selection Tool");
    }

    @Override
    protected void onShiftRightClick(PlayerInteractEvent event, Map<String, Object> playerMap, CuboidRegion region, Location interactionPoint) {
        region.unvisualize();
        GameMap map = new GameMap();
        map.setRegion(region);
        GameMap.getEditorCache().put((event.getPlayer().getUniqueId()), map);
        event.getPlayer().showDialog(Dialogs.NewMapDialog(map));
    }

    @Override
    protected void onShiftLeftClick(PlayerInteractEvent event, Map<String, Object> playerMap, CuboidRegion region, Location interactionPoint) {
        region.unvisualize();
        GameMap.getEditorCache().remove(event.getPlayer().getUniqueId());
        Common.tell(event.getPlayer(), "Cleared editing cache!");
    }
}
