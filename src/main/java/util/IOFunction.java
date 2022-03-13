package util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface IOFunction<K>{
    void write(K value, DataOutputStream output) throws IOException;
    K read(DataInputStream input) throws IOException;
    int getLength(K value);
}
