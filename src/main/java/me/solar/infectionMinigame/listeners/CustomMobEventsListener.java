package me.solar.infectionMinigame.listeners;

import kr.toxicity.model.api.data.renderer.ModelRenderer;
import kr.toxicity.model.api.tracker.EntityTracker;
import me.solar.apollo.apolloCore.utils.CommonKt;
import me.solar.infectionMinigame.InfectionMinigamePlugin;
import me.solar.infectionMinigame.events.CustomMobSpawnEvent;
import me.solar.infectionMinigame.mobs.CustomMob;
import me.solar.infectionMinigame.mobs.ModelMob;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CustomMobEventsListener implements Listener {

    @EventHandler
    public void onCustomMobSpawn(CustomMobSpawnEvent event) {
        if (event.getCustomMob() == null) {
            return;
        }

        if (!(event.getCustomMob() instanceof ModelMob modelMob)) {
            return;
        }

        String modelName = modelMob.getModelName();
        CustomMob customMob = event.getCustomMob();

        Entity entity = Bukkit.getEntity(customMob.getBukkitEntity().getUniqueId());

        ModelRenderer modelRenderer = InfectionMinigamePlugin.getModelManager().renderer(modelName.toLowerCase().replace(" ", "_"));
        if (modelRenderer != null) {
            EntityTracker tracker = modelRenderer.create(entity);
            for (Player player : entity.getWorld().getPlayers()) {
                tracker.spawn(player);
            }
        } else {
            CommonKt.tellConsole("<red>Model for " + modelName + " not found!");
        }

    }

}
