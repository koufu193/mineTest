package game.chunk;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DirectPalette extends Palette{
    public static DirectPalette DEFAULT=new DirectPalette();
    @Override
    public void write(@NotNull DataOutputStream output) {
    }

    @Override
    public void read(@NotNull DataInputStream input){

    }
}
