package game.entity;

import game.entity.metadata.EntityMetadata;
import game.entity.metadata.ThrowableMetadata;

public class ThrowableEntity extends Entity{
    protected ThrowableMetadata metadata;
    @Override
    public ThrowableMetadata getMetadata() {
        return metadata;
    }
}
