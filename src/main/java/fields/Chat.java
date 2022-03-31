package fields;

import util.Util;

import java.io.DataInputStream;
import java.io.IOException;

public class Chat {
    private String json;
    public Chat(String json){
        this.json=json;
    }
    @Override
    public String toString() {
        return json;
    }
    public static Chat read(DataInputStream input) throws IOException{
        return new Chat(Util.readString(input));
    }
}
