package me.solar.infectionMinigame.events;

import lombok.Getter;
import me.solar.infectionMinigame.mobs.CustomMob;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CustomMobSpawnEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final CustomMob customMob;

    public CustomMobSpawnEvent(CustomMob customMob) {
        this.customMob = customMob;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
