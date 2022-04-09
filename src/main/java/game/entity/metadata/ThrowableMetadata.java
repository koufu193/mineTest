package game.entity.metadata;

import fields.Slot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ThrowableMetadata extends EntityMetadata{
    protected Slot item=null;
    public @Nullable Slot getItem() {
        return item;
    }
    @Override
    public void read(int index, @NotNull DataInputStream input) throws IOException {
        if(index==8){
            this.item=Slot.read(input);
        }else{
            super.read(index, input);
        }
    }
    @Override
    public void write(@NotNull DataOutputStream output) throws IOException {
        super.write(output);
        if(item!=null){
            Slot.write(item,output);
        }
    }

    @Override
    public String toString() {
        return "ThrowableMetadata{" +
                "item=" + item +
                "} " + super.toString();
    }
}
