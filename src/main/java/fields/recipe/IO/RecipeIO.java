package fields.recipe.IO;

import fields.Slot;
import fields.recipe.Ingredients;
import fields.recipe.Recipes;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RecipeIO {
    public static class Crafting_ShapelessIO extends AbstractRecipeIO<Recipes.Crafting_Shapeless>{

        @Override
        public Recipes.Crafting_Shapeless read(DataInputStream input) throws IOException {
            String group=Util.readString(input);
            int len=Util.readVarInt(input);
            Ingredients[] ingredients=new Ingredients[len];
            for(int i=0;i<len;i++){
                ingredients[i]=Ingredients.read(input);
            }
            Slot result=Slot.read(input);
            return new Recipes.Crafting_Shapeless(group,ingredients,result);
        }
        @Override
        public void write(Recipes.Crafting_Shapeless recipe, DataOutputStream output) throws IOException {
            Util.writeString(recipe.getGroup(), StandardCharsets.UTF_8,output);
            Util.writeVarInt(recipe.getIngredients().length,output);
            for(Ingredients ingredients:recipe.getIngredients()){
                Ingredients.write(ingredients,output);
            }
            Slot.write(recipe.getResult(),output);
        }
    }
}
