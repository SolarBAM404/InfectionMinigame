package me.solar.infectionMinigame.commands;

import com.magmaguy.freeminecraftmodels.customentity.DynamicEntity;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.solar.apolloLibrary.utils.Common;
import me.solar.apolloLibrary.world.CuboidRegion;
import me.solar.infectionMinigame.InfectionMinigamePlugin;
import me.solar.infectionMinigame.barricades.LockedDoor;
import me.solar.infectionMinigame.barricades.PickupItem;
import me.solar.infectionMinigame.barricades.RepairableBarricade;
import me.solar.infectionMinigame.barricades.items.DroppableItems;
import me.solar.infectionMinigame.data.GameMap;
import me.solar.infectionMinigame.dialogs.Dialogs;
import me.solar.infectionMinigame.menus.MapMenu;
import me.solar.infectionMinigame.mobs.MobList;
import me.solar.infectionMinigame.tools.ArenaSelectionTool;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicReference;

public class AdminCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> init() {
        LiteralArgumentBuilder<CommandSourceStack> node = LiteralArgumentBuilder.literal("admin");
        node.then(reloadCmd());
        node.then(testModelMobSpawningCmd());
        node.then(testAnimation());
        node.then(testBarrierCmd());
        node.then(testItemDropCmd());
        node.then(testLockedDoorCmd());
        node.then(createNewMap());
        node.then(editorMenu());
        node.then(mapsMenu());
        return node;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> reloadCmd() {
        LiteralArgumentBuilder<CommandSourceStack> node = LiteralArgumentBuilder.literal("reload");
        node.executes((ctx) -> {
            InfectionMinigamePlugin.getInstance().reload();
            Common.tell(ctx.getSource().getExecutor(), "<green>Config reloaded!");
            return 1;
        });
        return node;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> testModelMobSpawningCmd() {
        LiteralArgumentBuilder<CommandSourceStack> node = LiteralArgumentBuilder.literal("testmodelmob");
        node.executes(context -> {
            MobList.SPEEDY.spawn(context.getSource().getLocation());
            return 1; // Return success
        });
        return node;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> testAnimation() {
        return Commands.literal("testAnimation")
                .executes(context -> {
                    if (context.getSource().getExecutor() == null || !(context.getSource().getExecutor() instanceof Player player)) {
                        return 0;
                    }


                    AtomicReference<DynamicEntity> entity = new AtomicReference<>();
                    player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 5, 5, 5).forEach(e -> {
                        if (DynamicEntity.isDynamicEntity(e)) {
                            entity.set(DynamicEntity.getDynamicEntity(e));
                        }
                    });

                    if (entity.get() == null) {
                        Common.tell(player, "<red>No entity found!");
                        return 0;
                    }

                    DynamicEntity dynamicEntity = entity.get();
                    dynamicEntity.playAnimation("attack", false, true);
                    return 1;
                });
    }

    public static LiteralArgumentBuilder<CommandSourceStack> testBarrierCmd() {
        LiteralArgumentBuilder<CommandSourceStack> node = LiteralArgumentBuilder.literal("testbarrier");
        node.executes(context -> {
            Player player = (Player) context.getSource().getExecutor();
            if (player == null) {
                Common.tell(context.getSource().getExecutor(), "<red>You must be a player to use this command!");
                return 0;
            }

            Location location = player.getLocation().getBlock().getLocation();
            location.setYaw(player.getLocation().getYaw());
            CuboidRegion region = new CuboidRegion(location, location.clone().add(2, 2, 0));
            RepairableBarricade barricade = new RepairableBarricade(region);
            barricade.spawn();
            return 1; // Return success
        });
        node.then(Commands.argument("x", IntegerArgumentType.integer()).then(Commands.argument("y", IntegerArgumentType.integer())
                .executes((context -> {
                    Player player = (Player) context.getSource().getExecutor();
                    if (player == null) {
                        Common.tell(context.getSource().getExecutor(), "<red>You must be a player to use this command!");
                        return 0;
                    }

                    int x = IntegerArgumentType.getInteger(context, "x");
                    int y = IntegerArgumentType.getInteger(context, "y");

                    Location location = player.getLocation().getBlock().getLocation();
                    location.setYaw(player.getLocation().getYaw());
                    CuboidRegion region = new CuboidRegion(location.clone(), location.clone().add(x, y, 0));
                    RepairableBarricade barricade = new RepairableBarricade(region);
                    barricade.spawn();
                    barricade.setYaw(location.getYaw());
                    Common.tell(context.getSource().getExecutor(), "<green>Barricade spawned at your location with size (" + x + ", " + y + ")");
                    Common.tell(context.getSource().getExecutor(), "<green>Region: " + region.getPointA().toVector() + " <green>to <yellow>" + region.getPointB().toVector());
                    return 1;
                }))));
        return node;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> testItemDropCmd() {
        LiteralArgumentBuilder<CommandSourceStack> node = LiteralArgumentBuilder.literal("testitemdrop");
        node.executes(context -> {
            PickupItem drop = DroppableItems.SPEED_POWER_UP.createDrop(context.getSource().getLocation().add(5, 1, 5), 100);
            drop.spawn();
            return 1;
        });
        return node;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> testLockedDoorCmd() {
        LiteralArgumentBuilder<CommandSourceStack> node = LiteralArgumentBuilder.literal("testlockeddoor");
        node.executes(context -> {
            LockedDoor door = new LockedDoor(new CuboidRegion(context.getSource().getLocation(), context.getSource().getLocation().add(2, 2, 0)));
            door.spawn();
            return 1;
        });
        return node;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> createNewMap() {
        LiteralArgumentBuilder<CommandSourceStack> node = LiteralArgumentBuilder.literal("createmap");
        node.executes(context -> {
            Entity executor = context.getSource().getExecutor();
            if (!(executor instanceof Player player)) {
                Common.tell(context.getSource().getExecutor(), "<red>You must be a player to use this command!");
                return 0;
            }

            ArenaSelectionTool.getInstance().giveIfHasnt(player);
            Common.tell(player, "<green>You may now start setting up your map!");
            return 1;
        });
        return node;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> editorMenu() {
        LiteralArgumentBuilder<CommandSourceStack> node = LiteralArgumentBuilder.literal("editor");
        node.executes(context -> {
            if (context.getSource().getExecutor() == null) {
                throw new IllegalArgumentException("CommandSourceStack cannot be null!");
            }

            if (!(context.getSource().getExecutor() instanceof Player player)) {
                Common.tell(context.getSource().getExecutor(), "<red>You must be a player to use this command!");
                return 0;
            }

            GameMap editorMap = GameMap.getEditorCache().get(context.getSource().getExecutor().getUniqueId());
            if (editorMap == null) {
                Common.tell(context.getSource().getExecutor(), "<red>You must be in an editor menu to use this command!");
                return 0;
            }

//            new EditorMenu(editorMap).open((Player) context.getSource().getExecutor());
            player.showDialog(Dialogs.EditorDialog(editorMap));
            return 1;
        });
        return node;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> mapsMenu() {
        LiteralArgumentBuilder<CommandSourceStack> node = LiteralArgumentBuilder.literal("maps");
        node.executes(context -> {
            if (context.getSource().getExecutor() == null || !(context.getSource().getExecutor() instanceof Player player)) {
                Common.tell(context.getSource().getExecutor(), "<red>You must be a player to use this command!");
                return 0;
            }

            MapMenu.getInstance().open(player);
            return 1;
        });
        return node;
    }

}
