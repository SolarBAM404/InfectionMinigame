package me.solar.infectionMinigame.listeners;

import me.solar.infectionMinigame.barricades.Barricade;
import me.solar.infectionMinigame.barricades.RepairableBarricade;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Interaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class BarricadeListener implements Listener {

    @EventHandler
    public void onInteractionAttacker(EntityDamageEvent event) {
        if (event.getEntity() instanceof Interaction interaction) {
            if (interaction.hasMetadata("barricade")) {
                Barricade barricade = (Barricade) interaction.getMetadata("barricade").get(0).value();
                if (!(barricade instanceof RepairableBarricade reBarricade)) {
                    return;
                }
                reBarricade.damage(1);
                event.setCancelled(true); // Cancel the event to prevent further damage
            }
        }
    }

    @EventHandler
    public void onEntityClicked(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() instanceof Interaction interaction)) {
            return;
        }
        if (!interaction.hasMetadata("barricade")) {
            return;
        }

        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.OAK_PLANKS) {
            event.getPlayer().sendMessage("<red>You need Oak Planks to repair the barricade!");
            return;
        }

        Barricade barricade = (Barricade) interaction.getMetadata("barricade").get(0).value();
        if (!(barricade instanceof RepairableBarricade reBarricade)) {
            return;
        }

        if (reBarricade.getHealth() >= 5) {
            event.getPlayer().sendMessage("<red>The barricade is already at full health!");
            return;
        }

        reBarricade.repair(1);
        event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
        event.setCancelled(true); // Cancel the event to prevent further interaction
    }

//    private void checkForNearbyBarricades(Interaction interaction) {
//        // This method can be used to check for nearby barricades and perform actions if needed
//        // For example, you could loop through all barricades and check their health or location
//
//        Location interactionLocation = interaction.getLocation();
//        for (Barricade barricade : Barricade.getBarricades()) {
//            if (barricade.getLocation().distance(interactionLocation) < 5) { // Check if within 5 blocks
//                // Perform actions with the barricade, e.g., notify player or update UI
//            }
//        }
//
//    }


}
