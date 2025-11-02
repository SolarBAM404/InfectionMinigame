package me.solar.infectionMinigame.mobs;

import com.magmaguy.freeminecraftmodels.customentity.DynamicEntity;
import lombok.Getter;
import me.solar.infectionMinigame.InfectionMinigamePlugin;
import net.minecraft.world.level.Level;
import org.bukkit.craftbukkit.entity.CraftEntity;

public abstract class ModelMob extends CustomMob {

    @Getter
    private DynamicEntity dynamicEntity;

    protected ModelMob(Level level) {
        super(level);
    }

    @Override
    protected void setupMob() {
        if (!InfectionMinigamePlugin.isFmmLoaded()) return;

        System.out.println("Setting up model mob");
        dynamicEntity = DynamicEntity.create(getModel(), getBukkitLivingEntity());
        playAnimation("idle", true, false);
    }

    protected abstract String getModel();

    @Override
    public void playAnimation(String animationName, boolean blendAnimation, boolean loop) {
        if (!InfectionMinigamePlugin.isFmmLoaded()) super.playAnimation(animationName, blendAnimation, loop);
        if (dynamicEntity == null) return;
        dynamicEntity.playAnimation(animationName, blendAnimation, loop);
    }

}
