package me.solar.infectionMinigame;

import kr.toxicity.model.api.BetterModel;
import kr.toxicity.model.api.BetterModelPlugin;
import kr.toxicity.model.api.manager.ModelManager;
import lombok.Getter;
import me.solar.apollo.apolloCore.ApolloPlugin;
import me.solar.infectionMinigame.commands.AdminCommand;
import me.solar.infectionMinigame.listeners.BarricadeListener;
import me.solar.infectionMinigame.listeners.CustomMobEventsListener;

public class InfectionMinigamePlugin extends ApolloPlugin {

    @Getter
    private static InfectionMinigamePlugin instance;

    @Getter
    private static BetterModelPlugin betterModel;

    @Getter
    private static ModelManager modelManager;

    @Override
    public void start() {
        instance = this;
        AdminCommand.init();
        betterModel = BetterModel.plugin();
        modelManager = betterModel.modelManager();

        getServer().getPluginManager().registerEvents(new CustomMobEventsListener(), this);
        getServer().getPluginManager().registerEvents(new BarricadeListener(), this);
    }

    @Override
    public void shutdown() {

    }
}
