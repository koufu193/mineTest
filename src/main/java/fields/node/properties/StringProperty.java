package fields.node.properties;

import fields.Identifier;
import fields.node.Property;
import org.jetbrains.annotations.NotNull;

public class StringProperty extends Property {
    public static final int SINGLE_WORD=0;
    public static final int QUOTABLE_PHRASE=1;
    public static final int GREEDY_PHRASE=2;
    private int type;
    public StringProperty(int type){
        super();
        this.type=type;
    }

    @NotNull
    public int getStringType() {
        return type;
    }

    public void setStringType(int type) {
        this.type = type;
    }

    @Override
    protected @NotNull Identifier initType() {
        return new Identifier("brigadier","string");
    }
}
