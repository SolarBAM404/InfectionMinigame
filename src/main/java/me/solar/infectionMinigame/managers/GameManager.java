package me.solar.infectionMinigame.managers;

import lombok.Getter;
import me.solar.infectionMinigame.data.GameMap;

import java.util.Map;

public class GameManager {

    public static Map<GameMap, Boolean> gameMapStates;

    public static Map<GameMap, GameManager> gameManagers;

    @Getter
    private GameMap map;

    public GameManager(GameMap map) {
        this.map = map;
    }

}
