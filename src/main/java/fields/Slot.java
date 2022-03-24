package fields;

import org.jetbrains.annotations.NotNull;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class Slot {
    boolean Present;
    int Item_ID;
    byte Item_Count;
    NBT nbt;
    public Slot(boolean Present,int Item_ID,byte Item_Count,NBT nbt){
        this.Present=Present;
        this.Item_ID=Item_ID;
        this.Item_Count=Item_Count;
        this.nbt=nbt;
    }
    public byte getItem_Count() {
        return Item_Count;
    }
    public int getItem_ID() {
        return Item_ID;
    }
    public boolean isPresent() {
        return Present;
    }
    public NBT getNbt() {
        return nbt;
    }
    public static Slot read(@NotNull DataInputStream input) throws IOException{
        boolean present=input.readBoolean();
        if(present){
            int Item_ID=Util.readVarInt(input);
            byte Item_Count=input.readByte();
            NBT nbt=NBT.readDataFromDataInputStream(input);
            return new Slot(true,Item_ID,Item_Count,nbt);
        }else{
            return new Slot(false,0,(byte)0,null);
        }
    }
    public static void write(@NotNull Slot slot, @NotNull DataOutputStream output) throws IOException {
        output.writeBoolean(slot.isPresent());
        if(slot.isPresent()){
            Util.writeVarInt(slot.getItem_ID(),output);
            output.write(slot.getItem_Count());
            if(slot.nbt==null){
                output.write(0x00);
            }else{
                slot.getNbt().write(output);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o instanceof Slot slot) {
            return Present == slot.Present && Item_ID == slot.Item_ID && Item_Count == slot.Item_Count && Objects.equals(nbt, slot.nbt);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Slot{" +
                "Present=" + Present +
                ", Item_ID=" + Item_ID +
                ", Item_Count=" + Item_Count +
                ", nbt=" + nbt +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(Present, Item_ID, Item_Count, nbt);
    }
}
