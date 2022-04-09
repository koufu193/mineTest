package game.entity;

import game.entity.metadata.LivingEntityMetadata;

import java.util.UUID;

public class LivingEntity<T extends LivingEntityMetadata> extends Entity<T>{
    public LivingEntity(UUID uuid, int entityID, T metadata) {
        super(uuid, entityID, metadata);
    }
}
