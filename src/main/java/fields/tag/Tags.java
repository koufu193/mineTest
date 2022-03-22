package fields.tag;

import fields.Identifier;
import fields.Tag;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tags {
    Identifier tag_name;
    List<Tag> tags;
    public Tags(Identifier tag_type, List<Tag> tags){
        this.tag_name=tag_type;
        this.tags=tags;
    }
    public Identifier getTag_name() {
        return tag_name;
    }
    public List<Tag> getTags() {
        return tags;
    }
    public static void write(Tags tags, DataOutputStream output) throws IOException {
        Objects.requireNonNull(tags);
        Objects.requireNonNull(output);
        Util.writeString(tags.getTag_name().toString(), StandardCharsets.UTF_8,output);
        for(Tag tag:tags.getTags()){
            Tag.write(tag,output);
        }
    }
    public static Tags read(DataInputStream input) throws IOException{
        Objects.requireNonNull(input);
        Identifier tag_name=Identifier.getInstance(input);
        Tag[] data=new Tag[Util.readVarInt(input)];
        for(int i=0;i<data.length;i++){
            data[i]=Tag.read(input);
        }
        return new Tags(tag_name,List.of(data));
    }

    @Override
    public String toString() {
        return "Tags{" +
                "tag_name=" + tag_name +
                ", tags=" + tags +
                '}';
    }
}
