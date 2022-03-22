package fields.recipe.IO;

import fields.Identifier;
import fields.recipe.AbstractRecipe;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class AbstractRecipeIO<T extends AbstractRecipe> {
    protected final Identifier type;
    public abstract T read(DataInputStream input) throws IOException;
    public AbstractRecipeIO(){
        this.type=initType();
    }
    protected AbstractRecipeIO(Identifier type){
        this.type=type;
    }
    protected abstract Identifier initType();
    public abstract void write(T recipe, DataOutputStream output) throws IOException;
    public Identifier getType(){return type;}
}
