package com.cadenkoehl.minecraft2D.entities;

public class DamageSource {

    public static final DamageSource VOID = new DamageSource("{entity} fell into the void");

    private final String deathMessage;

    private DamageSource(String deathMessage) {
        this.deathMessage = deathMessage;
    }

    public String getDeathMessage(LivingEntity entity) {
        return deathMessage.replace("{entity}", entity.getDisplayName());
    }
    public String getDeathMessage(LivingEntity entity, LivingEntity killer) {
        return deathMessage.replace("{entity}", entity.getDisplayName().replace("{killer}", killer.getDisplayName()));
    }
}
