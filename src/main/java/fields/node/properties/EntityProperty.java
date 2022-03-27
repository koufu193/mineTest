package fields.node.properties;

import fields.Identifier;
import fields.node.Property;
import org.jetbrains.annotations.NotNull;

public class EntityProperty extends Property {
    private boolean allow_only_single_entity_player;
    private boolean allow_only_players;
    public EntityProperty(boolean allow_only_single_entity_player,boolean allow_only_players){
        super();
        this.allow_only_single_entity_player=allow_only_single_entity_player;
        this.allow_only_players=allow_only_players;
    }
    public boolean isAllow_only_players() {
        return allow_only_players;
    }
    public boolean isAllow_only_single_entity_player() {
        return allow_only_single_entity_player;
    }
    public void setAllow_only_players(boolean allow_only_players) {
        this.allow_only_players = allow_only_players;
    }
    public void setAllow_only_single_entity_player(boolean allow_only_single_entity_player) {
        this.allow_only_single_entity_player = allow_only_single_entity_player;
    }
    @Override
    protected @NotNull Identifier initType() {
        return new Identifier("minecraft","entity");
    }
}
