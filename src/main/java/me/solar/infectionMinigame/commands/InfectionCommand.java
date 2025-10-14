package me.solar.infectionMinigame.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.solar.apolloLibrary.utils.Common;

public class InfectionCommand {

    public static void createCommand() {
        LiteralArgumentBuilder<CommandSourceStack> infection = LiteralArgumentBuilder.literal("infection");
        infection.then(AdminCommand.init());
        Common.registerCommand(infection.build());
    }

}
