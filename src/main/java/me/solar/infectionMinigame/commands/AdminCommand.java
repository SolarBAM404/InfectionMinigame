package me.solar.infectionMinigame.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import kr.toxicity.model.api.data.renderer.ModelRenderer;
import kr.toxicity.model.api.tracker.EntityTracker;
import me.solar.apollo.apolloCore.commands.ApolloCommands;
import me.solar.apollo.apolloCore.utils.CommonKt;
import me.solar.infectionMinigame.InfectionMinigamePlugin;
import me.solar.infectionMinigame.barricades.Barricade;
import me.solar.infectionMinigame.mobs.CustomMob;
import me.solar.infectionMinigame.mobs.MobList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class AdminCommand {

    public static void init() {
        LiteralArgumentBuilder<CommandSourceStack> node = ApolloCommands.createCommand("admin");
        node.then(testMobSpawningCmd());
        node.then(testModelMobSpawningCmd());
        node.then(testCustomMobModelSpawningCmd());
        node.then(testBarrierCmd());
        LiteralCommandNode<CommandSourceStack> commandNode = node.build();
        ApolloCommands.registerCommand(commandNode);
    }

    public static LiteralArgumentBuilder<CommandSourceStack> testMobSpawningCmd() {
        LiteralArgumentBuilder<CommandSourceStack> node = ApolloCommands.createCommand("testmob");
//        node.executes(context -> {
//             CustomMob mob = new CustomMob((Level) context.getSource().getLocation().getWorld());
//             mob.spawn(context.getSource().getLocation());
//            return 1; // Return success
//        });
        return node;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> testModelMobSpawningCmd() {
        LiteralArgumentBuilder<CommandSourceStack> node = ApolloCommands.createCommand("testmodelmob");
        node.executes(context -> {
            ModelRenderer demonKnight = InfectionMinigamePlugin.getBetterModel().modelManager().renderer("demon_knight");
            if (demonKnight == null) {
                CommonKt.tell((Player) context.getSource(), "§cDemon Knight model not found!");
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
        return ApolloCommands.createCommand("testcustommob")
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
                                CommonKt.tell((Player) context.getSource(), "§cMob not found: " + mobName);
                                return 0; // Return failure
                            }
                            mob.spawn(context.getSource().getLocation());
                            return 1; // Return success
                        }))
                .executes(context -> {
                    CommonKt.tell((Player) context.getSource(), "§cUsage: /admin testcustommob <mob_name>");
                    CommonKt.tell((Player) context.getSource(), "§cAvailable mobs: " + String.join(", ", MobList.getMobNames()));
                    return 0; // Return failure
                });
    }

    public static LiteralArgumentBuilder<CommandSourceStack> testBarrierCmd() {
        LiteralArgumentBuilder<CommandSourceStack> node = ApolloCommands.createCommand("testbarrier");
        node.executes(context -> {
            Player player = (Player) context.getSource().getExecutor();
            Barricade barricade = new Barricade(5, 10);
            barricade.spawn(player.getLocation().getBlock().getLocation().add(.5, .5, .5));
            return 1; // Return success
        });
        return node;
    }

}
