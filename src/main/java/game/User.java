package game;

import java.util.UUID;

public class User {
    UUID uuid;
    String name;
    public User(UUID uuid,String name){
        this.uuid=uuid;
        this.name=name;
    }
    public String getName() {
        return name;
    }
    public UUID getUUID() {
        return uuid;
    }
}
