package me.solar.infectionMinigame.events;

import lombok.Getter;
import me.solar.infectionMinigame.barricades.Barricade;
import me.solar.infectionMinigame.mobs.CustomMob;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BarricadeDestoryedEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final Barricade barricade;

    public BarricadeDestoryedEvent(Barricade barricade) {
        this.barricade = barricade;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
