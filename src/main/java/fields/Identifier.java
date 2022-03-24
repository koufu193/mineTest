package fields;

import org.jetbrains.annotations.NotNull;
import util.Util;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class Identifier {
    String namespace;
    String thing;
    boolean name_nulled=false;
    public Identifier(String namespace,@NotNull String thing){
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
    public static Identifier getInstance(@NotNull DataInputStream input) throws IOException {
        int s=Util.readVarInt(input);
        String str=new String(input.readNBytes(s));
        if(str.contains(":")){
            String[] data=str.split(":",2);
            return new Identifier(data[0],data[1]);
        }else{
            return new Identifier(null,str);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return namespace.equals(that.namespace) && thing.equals(that.thing);
    }

    @Override
    public int hashCode() {
        return namespace.hashCode()+thing.hashCode();
    }
}
