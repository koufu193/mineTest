package fields.recipe;

import fields.Slot;
import util.Util;

import java.io.DataInputStream;
import java.io.IOException;

public class Recipes {
    public static class Crafting_Shapeless extends AbstractRecipe{
        String group;
        Ingredients[] ingredients;
        Slot result;
        public Crafting_Shapeless(String group,Ingredients[] ingredients,Slot result){
            this.group=group;
            this.ingredients=ingredients;
            this.result=result;
        }

        public Slot getResult() {
            return result;
        }

        public String getGroup() {
            return group;
        }

        public Ingredients[] getIngredients() {
            return ingredients;
        }
    }
}
