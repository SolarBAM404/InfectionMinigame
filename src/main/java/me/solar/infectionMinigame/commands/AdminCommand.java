package me.solar.infectionMinigame.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import kr.toxicity.model.api.data.renderer.ModelRenderer;
import kr.toxicity.model.api.tracker.EntityTracker;
import me.solar.apollo.apolloCore.commands.ApolloCommands;
import me.solar.apollo.apolloCore.utils.CommonKt;
import me.solar.infectionMinigame.InfectionMinigamePlugin;
import me.solar.infectionMinigame.mobs.CustomMob;
import me.solar.infectionMinigame.mobs.MobList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class AdminCommand {

    public static void init() {
        LiteralArgumentBuilder<CommandSourceStack> node = ApolloCommands.createCommand("admin");
        node.then(testMobSpawningCmd());
        node.then(testModelMobSpawningCmd());
        node.then(testCustomMobModelSpawningCmd());
        LiteralCommandNode<CommandSourceStack> commandNode = node.build();
        ApolloCommands.registerCommand(commandNode);
    }

    public static LiteralArgumentBuilder<CommandSourceStack> testMobSpawningCmd() {
        LiteralArgumentBuilder<CommandSourceStack> node = ApolloCommands.createCommand("testmob");
        node.executes(context -> {
             CustomMob mob = new CustomMob("Zombie", EntityType.ZOMBIE);
             mob.spawn(context.getSource().getLocation());
            return 1; // Return success
        });
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
        LiteralArgumentBuilder<CommandSourceStack> node = ApolloCommands.createCommand("testcustommob");
        node.executes(context -> {
            MobList.DEMON_KNIGHT.spawn(context.getSource().getLocation());
            return 1; // Return success
        });
        return node;
    }

}
