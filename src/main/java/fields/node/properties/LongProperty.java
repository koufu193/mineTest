package fields.node.properties;

import fields.Identifier;
import org.jetbrains.annotations.NotNull;

public class LongProperty extends MinMaxProperty<Long>{
    public LongProperty(@NotNull Long min, @NotNull Long max) {
        super(min, max);
    }

    @Override
    protected @NotNull Identifier initType() {
        return new Identifier("brigadier","long");
    }
}
