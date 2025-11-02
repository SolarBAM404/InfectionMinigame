package me.solar.infectionMinigame.listeners;

import me.solar.infectionMinigame.events.CustomMobSpawnEvent;
import me.solar.infectionMinigame.mobs.CustomMob;
import me.solar.infectionMinigame.mobs.ModelMob;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CustomMobEventsListener implements Listener {

    @EventHandler
    public void onCustomMobSpawn(CustomMobSpawnEvent event) {
        if (event.getCustomMob() == null) {
            return;
        }

//        if (!(event.getCustomMob() instanceof ModelMob modelMob)) {
//            return;
//        }

        CustomMob customMob = event.getCustomMob();

        Entity entity = Bukkit.getEntity(customMob.getBukkitEntity().getUniqueId());

//        ModelRenderer modelRenderer = InfectionMinigamePlugin.getModelManager().renderer(modelName.toLowerCase().replace(" ", "_"));
//        if (modelRenderer != null) {
//            EntityTracker tracker = modelRenderer.create(entity);
//            for (Player player : entity.getWorld().getPlayers()) {
//                tracker.spawn(player);
//            }
//        } else {
//            Common.log("<red>Model for " + modelName + " not found!");
//        }

    }

}
