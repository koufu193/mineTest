package fields;

import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class Tag {
    Identifier tag_name;
    int[] entries;
    public Tag(Identifier tag_name,int[] entries){
        Objects.requireNonNull(tag_name);
        Objects.requireNonNull(entries);
        this.tag_name=tag_name;
        this.entries=entries;
    }
    public Identifier getTag_name() {
        return tag_name;
    }
    public int[] getEntries() {
        return entries;
    }
    public Tag(Identifier tag_name){
        this(tag_name,new int[0]);
    }
    public static void write(Tag tag, DataOutputStream output) throws IOException {
        Objects.requireNonNull(tag);
        Objects.requireNonNull(output);
        Util.writeString(tag.getTag_name().toString(), StandardCharsets.UTF_8,output);
        Util.writeVarInt(tag.getEntries().length,output);
        for(int entry:tag.getEntries()){
            Util.writeVarInt(entry,output);
        }
    }
    public static Tag read(DataInputStream input) throws IOException{
        Objects.requireNonNull(input);
        Identifier tag_name=Identifier.getInstance(input);
        int[] entries=new int[Util.readVarInt(input)];
        for(int i=0;i<entries.length;i++){
            entries[i]=Util.readVarInt(input);
        }
        return new Tag(tag_name,entries);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tag_name=" + tag_name +
                ", entries=" + Arrays.toString(entries) +
                '}';
    }
}
