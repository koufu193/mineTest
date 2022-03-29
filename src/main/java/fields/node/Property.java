package fields.node;

import fields.Identifier;
import org.jetbrains.annotations.NotNull;

public abstract class Property {
    protected abstract @NotNull Identifier initType();
    private final Identifier type;
    public Property(){
        this.type=initType();
    }
    /**
     * タイプを取得する
     * @return タイプ
     */
    public final @NotNull Identifier getType(){
        return type;
    }

    @Override
    public String toString() {
        return "Property{" +
                "type=" + type +
                '}';
    }
}