package me.solar.infectionMinigame.data;

import me.solar.apolloLibrary.config.ConfigFormat;
import me.solar.apolloLibrary.config.ConfigKey;
import me.solar.apolloLibrary.config.FormatType;

import java.util.ArrayList;
import java.util.List;

@ConfigFormat(FormatType.YAML)
public class StaticMapConfig {

    @ConfigKey(value = "maps", comment = "The list of game maps, DO NOT EDIT UNLESS YOU KNOW WHAT YOUR DOING")
    public static List<GameMap> MAPS = new ArrayList<>();
}
