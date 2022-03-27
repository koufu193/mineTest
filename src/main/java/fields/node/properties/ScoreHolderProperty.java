package fields.node.properties;

import fields.Identifier;
import fields.node.Property;
import org.jetbrains.annotations.NotNull;

public class ScoreHolderProperty extends Property {
    private boolean allow_multiple;
    public ScoreHolderProperty(boolean allow_multiple){
        super();
        this.allow_multiple=allow_multiple;
    }
    public boolean isAllow_multiple() {
        return allow_multiple;
    }
    public void setAllow_multiple(boolean allow_multiple) {
        this.allow_multiple = allow_multiple;
    }
    @Override
    protected @NotNull Identifier initType() {
        return new Identifier("minecraft","score_holder");
    }
}
