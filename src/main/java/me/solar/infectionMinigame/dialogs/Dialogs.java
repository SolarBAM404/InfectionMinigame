package me.solar.infectionMinigame.dialogs;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import me.solar.apolloLibrary.utils.Common;
import me.solar.apolloLibrary.world.CuboidRegion;
import me.solar.infectionMinigame.data.GameMap;
import me.solar.infectionMinigame.tools.ArenaSelectionTool;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.List;

public class Dialogs {

    public static Dialog NewMapDialog(GameMap map) {
        return Dialog.create(builder -> builder.empty()
                .base(DialogBase.builder(Common.component("Map Editor: " + map.getName()))
                        .inputs(List.of(
                                DialogInput.text("name", Common.component("Change Map Name"))
                                        .width(150)
                                        .build(),
                                DialogInput.numberRange("minPlayer", Common.component("Minimum amount of Players for this map"), 1f, 20f)
                                        .width(300)
                                        .step(1f)
                                        .initial(map.getMinPlayers() == 0 ? 1f : map.getMinPlayers())
                                        .build(),
                                DialogInput.numberRange("maxPlayers", Common.component("Maximum amount of Players for this map"), 1f, 20f)
                                        .width(300)
                                        .step(1f)
                                        .initial(map.getMaxPlayers() == 0 ? 1f : map.getMaxPlayers())
                                        .build()

                        ))
                        .build())
                .type(DialogType.confirmation(
                        ActionButton.create(
                                Common.component("<#AEFFC1>Confirm"),
                                Common.component("<gray>Click to save changes"),
                                100,
                                DialogAction.customClick((view, audience) -> {
                                            if (!(audience instanceof Player player)) return;
                                            String newName = view.getText("name");
                                            int minPlayers = view.getFloat("minPlayer").intValue();
                                            int maxPlayers = view.getFloat("maxPlayers").intValue();

                                            if (newName != null && !newName.isBlank()) {
                                                map.setName(newName);
                                            }
                                            map.setMinPlayers(minPlayers);
                                            map.setMaxPlayers(maxPlayers);
                                            map.save();
                                            Common.tell(audience, "<green> Map changes have been saved.");
                                        },
                                        ClickCallback.Options.builder()
                                                .uses(1)
                                                .build()
                                )
                        ),
                        ActionButton.create(
                                Common.component("<#FFA0B1>Cancel"),
                                Common.component("<gray>Click to cancel changes"),
                                100,
                                null
                        )
                )));
    }

    public static Dialog EditorDialog(GameMap map) {
        return Dialog.create(builder -> builder.empty()
                .base(DialogBase.builder(Common.component("Map Editor: " + map.getName()))
                        .inputs(List.of(
                                DialogInput.text("name", Common.component("Change Map Name"))
                                        .initial(map.getName())
                                        .width(150)
                                        .build(),
                                DialogInput.bool("newRegion", Common.component("Refresh region?"))
                                        .initial(false)
                                        .build(),
                                DialogInput.numberRange("minPlayer", Common.component("Minimum amount of Players for this map"), 1f, 20f)
                                        .width(300)
                                        .step(1f)
                                        .initial(map.getMinPlayers() == 0 ? 1f : map.getMinPlayers())
                                        .build(),
                                DialogInput.numberRange("maxPlayers", Common.component("Maximum amount of Players for this map"), 1f, 20f)
                                        .width(300)
                                        .step(1f)
                                        .initial(map.getMaxPlayers() == 0 ? 1f : map.getMaxPlayers())
                                        .build()

                        ))
                        .build())
                .type(DialogType.confirmation(
                        ActionButton.create(
                                Common.component("<#AEFFC1>Confirm"),
                                Common.component("<gray>Click to save changes"),
                                100,
                                DialogAction.customClick((view, audience) -> {
                                    if (!(audience instanceof Player player)) return;
                                    String newName = view.getText("name");
                                    boolean newRegion = Boolean.TRUE.equals(view.getBoolean("newRegion"));
                                    int minPlayers = view.getFloat("minPlayer").intValue();
                                    int maxPlayers = view.getFloat("maxPlayers").intValue();

                                    if (newName != null && !newName.isBlank()) {
                                        map.setName(newName);
                                    }
                                    map.setMinPlayers(minPlayers);
                                    map.setMaxPlayers(maxPlayers);
                                    if (newRegion) {
                                        CuboidRegion region = ArenaSelectionTool.getCuboidRegion(player);
                                        map.setRegion(region);
                                    }
                                    map.save();
                                    Common.tell(audience, "<green> Map changes have been saved.");
                                },
                                ClickCallback.Options.builder()
                                    .uses(1)
                                    .build()
                                )
                        ),
                        ActionButton.create(
                                Common.component("<#FFA0B1>Cancel"),
                                Common.component("<gray>Click to cancel changes"),
                                100,
                                null
                        )
                )));
    };

}
