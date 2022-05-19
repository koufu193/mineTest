package game.chunk;

import org.jetbrains.annotations.NotNull;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IndirectPalette extends Palette{
    private int[] value;
    public IndirectPalette(int[] value){
        this.value=value;
    }

    public int[] getValue() {
        return value;
    }

    public void setValue(int[] value) {
        this.value = value;
    }

    @Override
    public void write(@NotNull DataOutputStream output) throws IOException {
        Util.writeVarInt(value.length,output);
        for(int v:value){
            Util.writeVarInt(v,output);
        }
    }

    @Override
    public void read(@NotNull DataInputStream input) throws IOException {
        value=new int[Util.readVarInt(input)];
        for(int i=0;i<value.length;i++){
            value[i]=Util.readVarInt(input);
        }
    }
}
