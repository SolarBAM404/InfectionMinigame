package me.solar.infectionMinigame.tools;

import lombok.Getter;
import me.solar.apolloLibrary.tools.RegionSelectorTool;
import me.solar.apolloLibrary.utils.Common;
import me.solar.apolloLibrary.utils.ItemStackUtils;
import me.solar.apolloLibrary.world.CuboidRegion;
import me.solar.infectionMinigame.menus.RepairableBarricadeMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class RepairableBarricadeTool extends RegionSelectorTool {

    @Getter
    private static final RepairableBarricadeTool instance = new RepairableBarricadeTool();

    @Override
    public ItemStack getItem() {
        return ItemStackUtils.createItemStack(Material.COPPER_AXE, "Repairable Barricade Tool");
    }

    @Override
    protected void onRightClick(PlayerInteractEvent event, Map<String, Object> playerMap, CuboidRegion region, Location interactionPoint) {
        if (event.getPlayer().isSneaking()) {
            RepairableBarricadeMenu.INSTANCE.open(event.getPlayer());
            return;
        }

        super.onRightClick(event, playerMap, region, interactionPoint);
    }
}
