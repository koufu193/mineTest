package fields.recipe.IO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class AbstractRecipeIO<T> {
    public abstract T read(DataInputStream input) throws IOException;
    public abstract void write(T recipe, DataOutputStream output) throws IOException;
}
