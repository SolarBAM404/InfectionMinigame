package me.solar.infectionMinigame;

import kr.toxicity.model.api.BetterModel;
import kr.toxicity.model.api.BetterModelPlugin;
import kr.toxicity.model.api.manager.ModelManager;
import lombok.Getter;
import me.solar.apolloLibrary.core.ApolloPlugin;
import me.solar.apolloLibrary.utils.Common;
import me.solar.infectionMinigame.commands.InfectionCommand;
import me.solar.infectionMinigame.listeners.BarricadeListener;
import me.solar.infectionMinigame.listeners.CustomMobEventsListener;
import me.solar.infectionMinigame.listeners.PickupListener;

public class InfectionMinigamePlugin extends ApolloPlugin {

    @Getter
    private static InfectionMinigamePlugin instance;

    @Getter
    private static BetterModelPlugin betterModel;

    @Getter
    private static ModelManager modelManager;

    @Override
    public void startup() {
        instance = this;
        setInstance(this);
        InfectionCommand.createCommand();
        betterModel = BetterModel.plugin();
        modelManager = betterModel.modelManager();

        Common.registerListener(this, new CustomMobEventsListener());
        Common.registerListener(this, new BarricadeListener());
        Common.registerListener(this, new PickupListener());

    }

    @Override
    public void onReload() {

    }

    @Override
    public void shutdown() {

    }
}
