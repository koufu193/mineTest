package fields;

import java.io.DataInputStream;
import java.io.IOException;

public class Recipe {
    Identifier Type;
    Identifier Recipe_ID;

    public static Recipe readRecipe(DataInputStream input) throws IOException {
        Recipe recipe=new Recipe(Identifier.getInstance(input),Identifier.getInstance(input));
        return recipe;
    }
    public Recipe(Identifier Type,Identifier Recipe_ID){
        this.Type=Type;
        this.Recipe_ID=Recipe_ID;
    }
}
