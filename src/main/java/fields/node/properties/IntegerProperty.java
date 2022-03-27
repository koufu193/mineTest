package fields.node.properties;

import fields.Identifier;
import org.jetbrains.annotations.NotNull;

public class IntegerProperty extends MinMaxProperty<Integer>{
    public IntegerProperty(@NotNull Integer min, @NotNull Integer max) {
        super(min, max);
    }
    @Override
    protected @NotNull Identifier initType() {
        return new Identifier("brigadier","double");
    }
}
