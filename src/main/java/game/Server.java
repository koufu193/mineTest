package game;

import fields.Identifier;
import game.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Server {
    protected String name;
    protected int port;
    protected Set<World> worlds=new HashSet<>();
    public Server(String name,int port){
        this.name=name;
        this.port=port;
    }

    public Set<World> getWorlds() {
        return worlds;
    }
    public @Nullable World getWorld(Identifier name){
        for(World world:worlds){
            if(world.getName().equals(name)){
                return world;
            }
        }
        return null;
    }
    public @Nullable Entity getEntity(@NotNull UUID uuid){
        for(World world:getWorlds()){
            for(Entity e:world.getEntities()){
                if(e.getUUID().equals(uuid)){
                    return e;
                }
            }
        }
        return null;
    }
}
