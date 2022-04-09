package game.entity;

import game.entity.metadata.EntityMetadata;
import game.entity.metadata.ThrowableMetadata;

import java.util.UUID;

public class ThrowableEntity extends Entity<ThrowableMetadata>{
    public ThrowableEntity(UUID uuid, int entityID, ThrowableMetadata metadata) {
        super(uuid, entityID, metadata);
    }
}
