package util;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface PropertyIOFunction<T>{
    T read(@NotNull DataInputStream input) throws IOException;
    void write(@NotNull T value, @NotNull DataOutputStream output) throws IOException;
}
