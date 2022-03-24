package fields.node;

import fields.Identifier;

public abstract class Property {
    protected abstract Identifier initType();
    final Identifier type;
    public Property(){
        this.type=initType();
    }
    public final Identifier getType(){
        return type;
    }
}
