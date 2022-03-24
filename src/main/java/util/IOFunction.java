package util;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface IOFunction<K>{
    void write(@NotNull K value,@NotNull DataOutputStream output) throws IOException;
    K read(@NotNull DataInputStream input) throws IOException;
    int getLength(@NotNull K value);
}
