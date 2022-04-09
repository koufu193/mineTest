package game.entity;

import game.entity.metadata.EntityMetadata;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Entity<T extends EntityMetadata>{
    protected T metadata;
    protected int EntityID;
    protected final UUID uuid;
    public Entity(UUID uuid,int entityID,T metadata){
        this.uuid=uuid;
        this.EntityID=entityID;
        this.metadata=metadata;
    }
    public int getEntityID() {
        return EntityID;
    }
    public T getMetadata() {
        return metadata;
    }
    public void setMetadata(@NotNull T metadata) {
        this.metadata = metadata;
    }
    public UUID getUUID() {
        return uuid;
    }
}
