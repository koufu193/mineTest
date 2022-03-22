package fields.recipe;

import fields.Identifier;
import fields.Slot;
import util.Util;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

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

        @Override
        public String toString() {
            return "Crafting_Shapeless{" +
                    "group='" + group + '\'' +
                    ", ingredients=" + Arrays.toString(ingredients) +
                    ", result=" + result +
                    '}';
        }
    }
    public static class Crafting_Shaped extends AbstractRecipe{
        int width;
        int height;
        String group;
        Ingredients[] ingredients;
        Slot result;
        public Crafting_Shaped(int width,int height,String group,Ingredients[] ingredients,Slot result){
            this.width=width;
            this.height=height;
            this.group=group;
            this.ingredients=ingredients;
            this.result=result;
        }
        public int getWidth() {
            return width;
        }
        public int getHeight() {
            return height;
        }
        public Ingredients[] getIngredients() {
            return ingredients;
        }
        public Slot getResult() {
            return result;
        }
        public String getGroup() {
            return group;
        }

        @Override
        public String toString() {
            return "Crafting_Shaped{" +
                    "width=" + width +
                    ", height=" + height +
                    ", group='" + group + '\'' +
                    ", ingredients=" + Arrays.toString(ingredients) +
                    ", result=" + result +
                    '}';
        }
    }
    public static class Cookable_Item extends AbstractRecipe{
        String group;
        Ingredients ingredient;
        Slot result;
        float experience;
        int cooking_time;
        public Cookable_Item(String group,Ingredients ingredient,Slot result,float experience,int cooking_time){
            this.group=group;
            this.ingredient=ingredient;
            this.result=result;
            this.experience=experience;
            this.cooking_time=cooking_time;
        }
        public Slot getResult() {
            return result;
        }
        public String getGroup() {
            return group;
        }
        public float getExperience() {
            return experience;
        }
        public Ingredients getIngredient() {
            return ingredient;
        }
        public int getCooking_time() {
            return cooking_time;
        }

        @Override
        public String toString() {
            return "Cookable_Item{" +
                    "group='" + group + '\'' +
                    ", ingredient=" + ingredient +
                    ", result=" + result +
                    ", experience=" + experience +
                    ", cooking_time=" + cooking_time +
                    '}';
        }
    }
    public static class Stonecutting extends AbstractRecipe{
        String group;
        Ingredients ingredients;
        Slot result;
        public Stonecutting(String group,Ingredients ingredients,Slot result){
            this.group=group;
            this.ingredients=ingredients;
            this.result=result;
        }
        public Slot getResult() {
            return result;
        }
        public Ingredients getIngredients() {
            return ingredients;
        }
        public String getGroup() {
            return group;
        }

        @Override
        public String toString() {
            return "Stonecutting{" +
                    "group='" + group + '\'' +
                    ", ingredients=" + ingredients +
                    ", result=" + result +
                    '}';
        }
    }
    public static class Smithing extends  AbstractRecipe{
        Ingredients base;
        Ingredients addition;
        Slot result;
        public Smithing(Ingredients base,Ingredients addition,Slot result){
            this.base=base;
            this.addition=addition;
            this.result=result;
        }
        public Slot getResult() {
            return result;
        }
        public Ingredients getAddition() {
            return addition;
        }
        public Ingredients getBase() {
            return base;
        }

        @Override
        public String toString() {
            return "Smithing{" +
                    "base=" + base +
                    ", addition=" + addition +
                    ", result=" + result +
                    '}';
        }
    }
}
