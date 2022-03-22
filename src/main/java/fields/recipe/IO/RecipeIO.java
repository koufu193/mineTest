package fields.recipe.IO;

import fields.Identifier;
import fields.Slot;
import fields.recipe.AbstractRecipe;
import fields.recipe.Ingredients;
import fields.recipe.Recipes;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RecipeIO {
    static Map<Identifier,AbstractRecipeIO<?>> recipes=new HashMap<>();
    public static AbstractRecipe getRecipe(Identifier type,DataInputStream input) throws IOException{
        AbstractRecipeIO<?> recipe=recipes.get(type);
        return recipe==null?null:recipe.read(input);
    }
    public static void writeRecipe(AbstractRecipe recipe,Identifier recipeType,DataOutputStream output) throws IOException{
        Objects.requireNonNull(output,"outputがnullです");
        if(recipe!=null){
            AbstractRecipeIO<?> recipeIO=recipes.get(recipeType);
            if(recipeIO!=null){
                if(!write(recipe,recipeIO,output)){
                    throw  new IllegalArgumentException("recipeとrecipeTypeの組み合わせが不適切です");
                }
            }
        }
    }
    private static <T extends AbstractRecipe> boolean write(AbstractRecipe recipe,AbstractRecipeIO<T> io,DataOutputStream output) throws IOException{
        T t=null;
        if(check(recipe,t)) {
            io.write((T)recipe,output);
            return true;
        }
        return false;
    }
    private static <T> boolean check(Object obj,T... t){
        Objects.requireNonNull(obj);
        Objects.requireNonNull(t);
        try {
            obj.getClass().asSubclass(t.getClass().getComponentType());
            return true;
        }catch (ClassCastException e){
            return false;
        }
    }
    private static void register(AbstractRecipeIO<?> recipeType){
        recipes.put(recipeType.getType(),recipeType);
    }
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
        protected Identifier initType() {
            return new Identifier("minecraft","crafting_shapeless");
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
    public static class Crafting_ShapedIO extends AbstractRecipeIO<Recipes.Crafting_Shaped>{
        @Override
        public Recipes.Crafting_Shaped read(DataInputStream input) throws IOException {
            int width=Util.readVarInt(input);
            int height=Util.readVarInt(input);
            String group=Util.readString(input);
            Ingredients[] ingredients=new Ingredients[width*height];
            for(int i=0;i<ingredients.length;i++){
                ingredients[i]=Ingredients.read(input);
            }
            return new Recipes.Crafting_Shaped(width,height,group,ingredients,Slot.read(input));
        }
        @Override
        protected Identifier initType() {
            return new Identifier("minecraft","crafting_shaped");
        }
        @Override
        public void write(Recipes.Crafting_Shaped recipe, DataOutputStream output) throws IOException {
            Util.writeVarInt(recipe.getWidth(),output);
            Util.writeVarInt(recipe.getHeight(),output);
            Util.writeString(recipe.getGroup(),StandardCharsets.UTF_8,output);
            for(Ingredients ingredients:recipe.getIngredients()) {
                Ingredients.write(ingredients,output);
            }
            Slot.write(recipe.getResult(),output);
        }
    }
    public static class Cookable_ItemIO extends AbstractRecipeIO<Recipes.Cookable_Item>{
        public Cookable_ItemIO(Identifier type){
            super(type);
        }
        @Override
        public Recipes.Cookable_Item read(DataInputStream input) throws IOException {
            return new Recipes.Cookable_Item(Util.readString(input),Ingredients.read(input),Slot.read(input),input.readFloat(),Util.readVarInt(input));
        }
        @Override
        protected Identifier initType() {
            return null;
        }
        @Override
        public void write(Recipes.Cookable_Item recipe, DataOutputStream output) throws IOException {
            Util.writeString(recipe.getGroup(),StandardCharsets.UTF_8,output);
            Ingredients.write(recipe.getIngredient(),output);
            Slot.write(recipe.getResult(),output);
            output.writeFloat(recipe.getExperience());
            Util.writeVarInt(recipe.getCooking_time(),output);
        }
    }
    public static class StonecuttingIO extends AbstractRecipeIO<Recipes.Stonecutting>{
        @Override
        public Recipes.Stonecutting read(DataInputStream input) throws IOException {
            return new Recipes.Stonecutting(Util.readString(input),Ingredients.read(input),Slot.read(input));
        }
        @Override
        protected Identifier initType() {
            return new Identifier("minecraft","stonecutting");
        }
        @Override
        public void write(Recipes.Stonecutting recipe, DataOutputStream output) throws IOException {
            Util.writeString(recipe.getGroup(),StandardCharsets.UTF_8,output);
            Ingredients.write(recipe.getIngredients(),output);
            Slot.write(recipe.getResult(),output);
        }
    }
    public static class SmithingIO extends AbstractRecipeIO<Recipes.Smithing>{
        @Override
        public Recipes.Smithing read(DataInputStream input) throws IOException {
            return new Recipes.Smithing(Ingredients.read(input),Ingredients.read(input),Slot.read(input));
        }
        @Override
        protected Identifier initType() {
            return new Identifier("minecraft","smithing");
        }
        @Override
        public void write(Recipes.Smithing recipe, DataOutputStream output) throws IOException {
            Ingredients.write(recipe.getBase(),output);
            Ingredients.write(recipe.getAddition(),output);
            Slot.write(recipe.getResult(),output);
        }
    }
    static {
        register(new Crafting_ShapelessIO());
        register(new Crafting_ShapedIO());
        register(new Cookable_ItemIO(new Identifier("minecraft","smelting")));
        register(new Cookable_ItemIO(new Identifier("minecraft","blasting")));
        register(new Cookable_ItemIO(new Identifier("minecraft","smoking")));
        register(new Cookable_ItemIO(new Identifier("minecraft","campfire_cooking")));
        register(new StonecuttingIO());
        register(new SmithingIO());
    }
}
