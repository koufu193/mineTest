package fields.node;

import fields.Identifier;
import fields.Node;
import fields.node.node.NameableNode;
import fields.node.node.nodes.ArgumentNode;
import fields.node.node.nodes.LiteralNode;
import fields.node.node.nodes.RootNode;
import fields.node.properties.AbstractPropertyIO;
import fields.node.properties.PropertyIO;
import org.jetbrains.annotations.NotNull;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class NodeUtil {
    public static void write(@NotNull Node node, @NotNull DataOutputStream output) throws IOException {
        byte flag=node.getFlag();
        output.writeByte(flag);
        Util.writeVarInt(node.getChildren().length,output);
        for(int i:node.getChildren()){
            Util.writeVarInt(i,output);
        }
        if(node.getRedirect_node()!=null){
            Util.writeVarInt(node.getRedirect_node(),output);
        }
        if(node instanceof NameableNode){
            Util.writeString(((NameableNode)node).getName(), StandardCharsets.UTF_8,output);
            if(node instanceof ArgumentNode argumentNode){
                Util.writeString(argumentNode.getPropertyType().toString(),StandardCharsets.UTF_8,output);
                AbstractPropertyIO<? extends Property> abstractPropertyIO=PropertyIO.getPropertyIO(argumentNode.getPropertyType());
                if(abstractPropertyIO!=null){
                    write(abstractPropertyIO,argumentNode.getProperty(),output);
                }
                if(argumentNode.getSuggestion_type()!=null){
                    Util.writeString(argumentNode.getSuggestion_type().toString(),StandardCharsets.UTF_8,output);
                }
            }
        }
    }
    private static <V extends Property> void write(AbstractPropertyIO<V> io,Property value,DataOutputStream output) throws IOException{
        io.write((V)value,output);
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
        Property property=(type==Flag.ARGUMENT? PropertyIO.getProperty(input):null);
        Identifier suggestions_type=(has_suggestion_type?Identifier.getInstance(input):null);
        switch (type){
            case ROOT:
                return new RootNode(type,children,redirect_code,is_executable);
            case LITERAL:
                return new LiteralNode(type,children,name,redirect_code,is_executable);
            case ARGUMENT:
                return new ArgumentNode(type,children,name,redirect_code,is_executable,property,suggestions_type);
        }
        return null;
    }
}
