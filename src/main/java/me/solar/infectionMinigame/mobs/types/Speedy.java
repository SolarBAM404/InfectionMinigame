package me.solar.infectionMinigame.mobs.types;

import me.solar.infectionMinigame.mobs.CustomMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class Speedy extends CustomMob {

    public Speedy(Level level) {
        super(level);

        setSpeed(0.5f);
        setHealth(10f);
        AttributeInstance attri = getAttributes().getInstance(Attributes.ATTACK_DAMAGE);
        if (attri != null) {
            attri.setBaseValue(1.5);
        }
    }

    @Override
    public void setSpeed(float speed) {
        AttributeInstance movementSpeedAttr = getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeedAttr == null) {
            throw new IllegalStateException("Entity " + this + " does not have a MOVEMENT_SPEED attribute!");
        }
        movementSpeedAttr.setBaseValue(speed);
        super.setSpeed(speed);
    }
}
