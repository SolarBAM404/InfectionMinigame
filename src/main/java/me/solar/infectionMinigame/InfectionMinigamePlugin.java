package me.solar.infectionMinigame;

import kr.toxicity.model.api.BetterModel;
import kr.toxicity.model.api.BetterModelPlugin;
import kr.toxicity.model.api.manager.ModelManager;
import lombok.Getter;
import me.solar.apollo.apolloCore.ApolloPlugin;
import me.solar.infectionMinigame.commands.AdminCommand;

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
    }

    @Override
    public void shutdown() {

    }
}
