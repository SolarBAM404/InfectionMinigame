package me.solar.infectionMinigame.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import kr.toxicity.model.api.data.renderer.ModelRenderer;
import kr.toxicity.model.api.tracker.EntityTracker;
import me.solar.apolloLibrary.utils.Common;
import me.solar.apolloLibrary.world.CuboidRegion;
import me.solar.infectionMinigame.InfectionMinigamePlugin;
import me.solar.infectionMinigame.barricades.PickupItem;
import me.solar.infectionMinigame.barricades.RepairableBarricade;
import me.solar.infectionMinigame.barricades.items.DroppableItems;
import me.solar.infectionMinigame.mobs.MobList;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class AdminCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> init() {
        LiteralArgumentBuilder<CommandSourceStack> node = LiteralArgumentBuilder.literal("admin");
        node.then(reloadCmd());
        node.then(testModelMobSpawningCmd());
        node.then(testCustomMobModelSpawningCmd());
        node.then(testBarrierCmd());
        node.then(testItemDropCmd());
        return node;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> reloadCmd() {
        LiteralArgumentBuilder<CommandSourceStack> node = LiteralArgumentBuilder.literal("reload");
        node.executes((ctx) -> {
            InfectionMinigamePlugin.getInstance().reloadConfig();
            Common.tell(ctx.getSource().getExecutor(), "<green>Config reloaded!");
            return 1;
        });
        return node;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> testModelMobSpawningCmd() {
        LiteralArgumentBuilder<CommandSourceStack> node = LiteralArgumentBuilder.literal("testmodelmob");
        node.executes(context -> {
            ModelRenderer demonKnight = InfectionMinigamePlugin.getBetterModel().modelManager().renderer("demon_knight");
            if (demonKnight == null) {
                Common.tell((Player) context.getSource(), "<red>Demon Knight model not found!");
                return 0; // Return failure
            }
            Entity entity = context.getSource().getExecutor().getWorld().spawnEntity(context.getSource().getLocation(), EntityType.ZOMBIE);
            EntityTracker tracker = demonKnight.create(entity);
            tracker.spawn((Player) context.getSource().getExecutor());
            return 1; // Return success
        });
        return node;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> testCustomMobModelSpawningCmd() {
        return Commands.literal("testcustommob")
                .then(Commands.argument("name", StringArgumentType.greedyString())
                        .suggests((context, builder) -> {
                            Arrays.stream(MobList.getMobNames()).filter(name -> name.toLowerCase().startsWith(builder.getRemaining().toLowerCase()))
                                    .forEach(builder::suggest);
                            return builder.buildFuture();
                        })
                        .executes(context -> {
                            String mobName = StringArgumentType.getString(context, "name");
                            MobList mob = Arrays.stream(MobList.values())
                                    .filter(m -> m.getName().equalsIgnoreCase(mobName))
                                    .findFirst()
                                    .orElse(null);
                            if (mob == null) {
                                Common.tell((Player) context.getSource(), "<red>Mob not found: " + mobName);
                                return 0; // Return failure
                            }
                            mob.spawn(context.getSource().getLocation());
                            return 1; // Return success
                        }))
                .executes(context -> {
                    Common.tell((Player) context.getSource(), "<red>Usage: /admin testcustommob <mob_name>");
                    Common.tell((Player) context.getSource(), "<red>Available mobs: " + String.join(", ", MobList.getMobNames()));
                    return 0; // Return failure
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

}
