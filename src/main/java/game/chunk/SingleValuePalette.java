package game.chunk;

import org.jetbrains.annotations.NotNull;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SingleValuePalette extends Palette{
    private int value;
    public SingleValuePalette(int value){
        this.value=value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void write(@NotNull DataOutputStream output) throws IOException {
        Util.writeVarInt(value,output);
    }

    @Override
    public void read(@NotNull DataInputStream input) throws IOException {
        this.value=Util.readVarInt(input);
    }
}
