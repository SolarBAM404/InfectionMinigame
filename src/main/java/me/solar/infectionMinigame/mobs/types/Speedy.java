package me.solar.infectionMinigame.mobs.types;

import com.magmaguy.freeminecraftmodels.customentity.DynamicEntity;
import com.magmaguy.freeminecraftmodels.customentity.core.Skeleton;
import com.magmaguy.freeminecraftmodels.customentity.core.components.AnimationComponent;
import com.magmaguy.freeminecraftmodels.dataconverter.FileModelConverter;
import com.magmaguy.freeminecraftmodels.dataconverter.SkeletonBlueprint;
import com.magmaguy.freeminecraftmodels.magmacore.util.Logger;
import me.solar.apolloLibrary.utils.Common;
import me.solar.infectionMinigame.mobs.CustomMob;
import me.solar.infectionMinigame.mobs.ModelMob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Speedy extends ModelMob {

    private DynamicEntity dynamicEntity;
    private SkeletonBlueprint skeletonBlueprint;
    private Skeleton skeleton;
    private AnimationComponent animationComponent;
    private String entityID = "speedy";

    public Speedy(Level level) {
        super(level);

        setSpeed(0.35f);
        setHealth(10f);
        attackDamage = 2;
        AttributeInstance attri = getAttributes().getInstance(Attributes.ATTACK_DAMAGE);
        if (attri != null) {
            attri.setBaseValue(1.5);
        }
    }

    @Override
    protected String getModel() {
        return "speedy";
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

    @Override
    protected void setupNonModelMob() {
        setEquipment(EquipmentSlot.FEET, ItemStack.of(Material.COPPER_BOOTS));
    }

    @Override
    public void move(MoverType type, Vec3 movement) {
        super.move(type, movement);
    }
}
