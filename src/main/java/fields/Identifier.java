package fields;

import util.Util;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Identifier {
    String namespace;
    String thing;
    boolean name_nulled=false;
    public Identifier(String namespace,String thing){
        if(namespace!=null) {
            this.namespace = namespace;
        }else{
            this.namespace="minecraft";
            name_nulled=true;
        }
        this.thing=thing;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getThing() {
        return thing;
    }

    public boolean isName_nulled() {
        return name_nulled;
    }

    @Override
    public String toString() {
        return namespace+":"+ thing;
    }
    public static Identifier getInstance(DataInputStream input) throws IOException {
        int s=Util.readVarInt(input);
        String str=new String(input.readNBytes(s));
        if(str.contains(":")){
            String[] data=str.split(":",2);
            return new Identifier(data[0],data[1]);
        }else{
            return new Identifier(null,str);
        }
    }
}
