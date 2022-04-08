package game.entity;

import game.entity.metadata.EntityMetadata;
import org.jetbrains.annotations.NotNull;

public class Entity {
    protected EntityMetadata metadata;
    private int EntityID;

    public int getEntityID() {
        return EntityID;
    }
    public EntityMetadata getMetadata() {
        return metadata;
    }
    public void setMetadata(@NotNull EntityMetadata metadata) {
        this.metadata = metadata;
    }
}
