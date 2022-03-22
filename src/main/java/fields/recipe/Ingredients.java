package fields.recipe;

import fields.Slot;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class Ingredients {
    Slot[] slots;
    public Ingredients(Slot[] slots){
        this.slots=slots;
    }

    public Slot[] getSlots() {
        return slots;
    }

    public static Ingredients read(DataInputStream input) throws IOException {
        int count=Util.readVarInt(input);
        Slot[] slots=new Slot[count];
        for(int i=0;i< slots.length;i++){
            slots[i]= Slot.read(input);
        }
        return new Ingredients(slots);
    }
    public static void write(Ingredients ingredients,DataOutputStream output) throws IOException{
        Objects.requireNonNull(ingredients,"ingredientsがnullです");
        Util.writeVarInt(ingredients.getSlots().length,output);
        for(int i=0;i<ingredients.getSlots().length;i++){
            Slot.write(ingredients.getSlots()[i],output);
        }
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "slots=" + Arrays.toString(slots) +
                '}';
    }
}
