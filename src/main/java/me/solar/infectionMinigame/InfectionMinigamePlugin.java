package me.solar.infectionMinigame;

import com.magmaguy.freeminecraftmodels.FreeMinecraftModels;
import lombok.Getter;
import me.solar.apolloLibrary.core.ApolloPlugin;
import me.solar.apolloLibrary.utils.Common;
import me.solar.infectionMinigame.commands.InfectionCommand;
import me.solar.infectionMinigame.listeners.BarricadeListener;
import me.solar.infectionMinigame.listeners.CustomMobEventsListener;
import me.solar.infectionMinigame.listeners.PickupListener;
import me.solar.infectionMinigame.tools.ArenaSelectionTool;
import me.solar.infectionMinigame.tools.RepairableBarricadeTool;

public class InfectionMinigamePlugin extends ApolloPlugin {

    @Getter
    private static InfectionMinigamePlugin instance;

    @Getter
    private static boolean fmmLoaded = false;

    @Override
    public void startup() {
        instance = this;
        setInstance(this);
        InfectionCommand.createCommand();

        try {
            getPlugin(FreeMinecraftModels.class);
            fmmLoaded = true;
        } catch (Exception e) {
            Common.log("<red>FreeMinecraftModels not found!");
            Common.log("<red>Disabling FMM mobs....");

        }

        Common.registerListener(this, new CustomMobEventsListener());
        Common.registerListener(this, new BarricadeListener());
        Common.registerListener(this, new PickupListener());

        new ArenaSelectionTool().register();
        new RepairableBarricadeTool().register();

        ConfigManager.ARENAS.load();
    }

    @Override
    public void onReload() {
        ConfigManager.ARENAS.load();
    }

    @Override
    public void shutdown() {

    }
}
