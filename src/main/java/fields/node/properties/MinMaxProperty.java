package fields.node.properties;

import fields.Identifier;
import fields.node.Property;
import org.jetbrains.annotations.NotNull;

public abstract class MinMaxProperty<T> extends Property {
    private T min;
    private T max;
    public MinMaxProperty(@NotNull T min, @NotNull T max) {
        super();
        this.min=min;
        this.max=max;
    }
    public T getMax() {
        return max;
    }
    public T getMin() {
        return min;
    }
    public void setMax(T max) {
        this.max = max;
    }

    public void setMin(T min) {
        this.min = min;
    }
}
