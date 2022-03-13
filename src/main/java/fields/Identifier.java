package fields;

import util.Util;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Identifier {
    String namespace;
    String thing;
    public Identifier(String namespace,String thing){
        this.namespace=namespace;
        this.thing=thing;
    }
    @Override
    public String toString() {
        return namespace+":"+ thing;
    }
    public static Identifier getInstance(DataInputStream input) throws IOException {
        int s=Util.readVarInt(input);
        System.out.println(s);
        String str=new String(input.readNBytes(s));
        if(str.contains(":")){
            String[] data=str.split(":",2);
            return new Identifier(data[0],data[1]);
        }else{
            return new Identifier("minecraft;",str);
        }
    }
}
