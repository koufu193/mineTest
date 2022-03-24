package fields;

import fields.recipe.AbstractRecipe;
import fields.recipe.IO.RecipeIO;
import org.jetbrains.annotations.NotNull;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Recipe {
    Identifier Type;
    Identifier Recipe_ID;
    AbstractRecipe data;

    public static Recipe readRecipe(@NotNull DataInputStream input) throws IOException {
        Identifier RecipeType=Identifier.getInstance(input);
        return new Recipe(RecipeType,Identifier.getInstance(input), RecipeIO.getRecipe(RecipeType,input));
    }
    public Recipe(@NotNull Identifier Type,@NotNull Identifier Recipe_ID,AbstractRecipe data){
        this.Type=Type;
        this.Recipe_ID=Recipe_ID;
        this.data=data;
    }
    public Identifier getRecipe_ID() {
        return Recipe_ID;
    }
    public AbstractRecipe getData() {
        return data;
    }
    public Identifier getType() {
        return Type;
    }
    public static void writeRecipe(@NotNull Recipe recipe,@NotNull DataOutputStream output) throws IOException{
        Util.writeString(recipe.getType().toString(), StandardCharsets.UTF_8,output);
        Util.writeString(recipe.getRecipe_ID().toString(),StandardCharsets.UTF_8,output);
        RecipeIO.writeRecipe(recipe.getData(),recipe.getType(),output);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "Type=" + Type +
                ", Recipe_ID=" + Recipe_ID +
                ", data=" + data +
                '}';
    }
}
