package game.entity;

import game.entity.metadata.PlayerMetadata;

import java.util.UUID;

public class Player extends LivingEntity<PlayerMetadata>{
    public Player(UUID uuid, int entityID, PlayerMetadata metadata) {
        super(uuid, entityID, metadata);
    }
}
