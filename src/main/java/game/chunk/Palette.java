package game.chunk;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Palette {
    public Palette(){

    }
    public abstract void write(@NotNull DataOutputStream output) throws IOException;
    public abstract void read(@NotNull DataInputStream input) throws IOException;
}
