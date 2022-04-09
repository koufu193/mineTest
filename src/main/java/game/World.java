package game;

import fields.Identifier;
import game.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class World {
    protected Identifier name;
    protected List<Entity<?>> entities=new ArrayList<>();
    public World(@NotNull Identifier name){
        this.name=name;
    }
    public Identifier getName() {
        return name;
    }
    public List<Entity<?>> getEntities() {
        return entities;
    }
    public @Nullable Entity<?> getEntity(int entityID){
        for(Entity entity:entities){
            if(entity.getEntityID()==entityID){
                return entity;
            }
        }
        return null;
    }
}
