package fields.node.properties;

import fields.Identifier;
import fields.node.Property;
import org.jetbrains.annotations.NotNull;

public class FloatProperty extends MinMaxProperty<Float>{
    public FloatProperty(@NotNull Float min, @NotNull Float max) {
        super(min, max);
    }
    @Override
    protected @NotNull Identifier initType() {
        return new Identifier("brigadier","float");
    }
}
