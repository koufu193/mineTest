package fields.node.properties;

import fields.Identifier;
import fields.node.Property;
import org.jetbrains.annotations.NotNull;

public class DoubleProperty extends MinMaxProperty<Double>{
    public DoubleProperty(@NotNull Double min, @NotNull Double max) {
        super(min, max);
    }

    @Override
    protected @NotNull Identifier initType() {
        return new Identifier("brigadier","double");
    }
}
