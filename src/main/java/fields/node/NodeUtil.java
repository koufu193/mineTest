package fields.node;

import fields.Identifier;
import fields.Node;
import fields.node.node.nodes.LiteralNode;
import org.jetbrains.annotations.NotNull;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NodeUtil {
    public static void write(@NotNull Node node, @NotNull DataOutputStream output) throws IOException {
    }
    public static Node read(@NotNull DataInputStream input) throws IOException{
        byte flag=input.readByte();
        Flag type=Flag.getFlag(Util.bitmask(flag,0x03));
        if(type==null){
            throw new RuntimeException("typeがnullです。 flag:"+flag);
        }
        boolean is_executable=(Util.bitmask(flag,0x04)==0x04);
        boolean has_redirect=(Util.bitmask(flag,0x08)==0x08);
        boolean has_suggestion_type=false;
        if(type==Flag.ARGUMENT){
            has_suggestion_type=(Util.bitmask(flag,0x10)==0x10);
        }
        int[] children=new int[Util.readVarInt(input)];
        for(int i=0;i<children.length;i++){
            children[i]=Util.readVarInt(input);
        }
        int redirect_code=-1;
        if(has_redirect){
            redirect_code=Util.readVarInt(input);
        }
        String name=null;
        if(type==Flag.LITERAL||type==Flag.ARGUMENT){
            name=Util.readString(input);
        }
        Identifier parser=Identifier.getInstance(input);
        //Propertiesの読み込み
        *\
        //Propertiesの読み込み終了
        Identifier suggestions_type=(has_suggestion_type?Identifier.getInstance(input):null);
    }
}
