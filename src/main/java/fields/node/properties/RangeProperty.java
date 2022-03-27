package fields.node.properties;

import fields.Identifier;
import fields.node.Property;
import org.jetbrains.annotations.NotNull;

public class RangeProperty extends Property {
    private boolean allow_decimal_values;
    public RangeProperty(boolean allow_decimal_values){
        super();
        this.allow_decimal_values=allow_decimal_values;
    }
    public boolean isAllow_decimal_values() {
        return allow_decimal_values;
    }
    public void setAllow_decimal_values(boolean allow_decimal_values) {
        this.allow_decimal_values = allow_decimal_values;
    }
    @Override
    protected @NotNull Identifier initType() {
        return new Identifier("minecraft","range");
    }
}
