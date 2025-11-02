package me.solar.infectionMinigame;

import me.solar.apolloLibrary.config.StaticConfigHandler;
import me.solar.infectionMinigame.data.StaticMapConfig;

import java.io.IOException;
import java.nio.file.Path;

public enum ConfigManager {

    ARENAS(Path.of(String.valueOf(InfectionMinigamePlugin.getInstance().getDataFolder()), "maps.yaml"), StaticMapConfig.class),;

    private Path path;
    private Class<?> clazz;

    ConfigManager(Path path, Class<?> clazz) {
        this.path = path;
        this.clazz = clazz;
    }

    public void save() {
        try {
            StaticConfigHandler.saveConfig(clazz, path);
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() {
        try {
            StaticConfigHandler.loadConfig(clazz, path);
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
