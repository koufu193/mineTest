package fields;

import fields.recipe.AbstractRecipe;
import fields.recipe.IO.RecipeIO;
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

    public static Recipe readRecipe(DataInputStream input) throws IOException {
        Identifier RecipeType=Identifier.getInstance(input);
        return new Recipe(RecipeType,Identifier.getInstance(input), RecipeIO.getRecipe(RecipeType,input));
    }
    public Recipe(Identifier Type,Identifier Recipe_ID,AbstractRecipe data){
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
    public static void writeRecipe(Recipe recipe,DataOutputStream output) throws IOException{
        Objects.requireNonNull(recipe,"recipeがnullです");
        Objects.requireNonNull(output,"outputがnullです");
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
