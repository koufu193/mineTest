package util;

import java.io.IOException;
import java.util.function.Consumer;

public interface Custom<K>{
    void accept(K data) throws IOException;
}
