package Packet;

import fields.*;
import util.Data;
import fields.node.NodeUtil;
import fields.tag.Tags;
import org.jetbrains.annotations.NotNull;
import util.IOFunction;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PacketFieldType<K>{
    public static final PacketFieldType<Integer> VARINT=new PacketFieldType<>("VARINT",1,5,new IOFunction<>() {
        @Override
        public void write(@NotNull Integer value, @NotNull DataOutputStream output) throws IOException {
            Util.writeVarInt(value, output);
        }

        @Override
        public Integer read(@NotNull DataInputStream input) throws IOException {
            return Util.readVarInt(input);
        }

        @Override
        public int getLength(@NotNull Integer value) {
            return Util.getVarLength(value);
        }
    });
    public static final PacketFieldType<UUID> UUID=new PacketFieldType<>("UUID",16,16, new IOFunction<>() {
        @Override
        public void write(java.util.@NotNull UUID value, @NotNull DataOutputStream output) throws IOException {
            output.writeLong(value.getMostSignificantBits());
            output.writeLong(value.getLeastSignificantBits());
        }

        @Override
        public java.util.UUID read(@NotNull DataInputStream input) throws IOException {
            return new UUID(input.readLong(), input.readLong());
        }

        @Override
        public int getLength(java.util.@NotNull UUID value) {
            return 16;
        }
    });
    public static final PacketFieldType<String> STRING=new PacketFieldType<>("STRING",1,131071, new IOFunction<>() {
        @Override
        public void write(@NotNull String value, @NotNull DataOutputStream output) throws IOException {
            Util.writeString(value, StandardCharsets.UTF_8,output);
        }

        @Override
        public String read(@NotNull DataInputStream input) throws IOException {
            return Util.readString(input);
        }

        @Override
        public int getLength(@NotNull String value) {
            return Util.getVarLength(value.length())+value.getBytes(StandardCharsets.UTF_8).length;
        }
    });
    public static final PacketFieldType<Long> LONG=new PacketFieldType<>("LONG",64,64, new IOFunction<>() {
        @Override
        public void write(@NotNull Long value, @NotNull DataOutputStream output) throws IOException {
            output.writeLong(value);
        }

        @Override
        public Long read(@NotNull DataInputStream input) throws IOException {
            return input.readLong();
        }

        @Override
        public int getLength(@NotNull Long value) {
            return 64;
        }
    });
    public static final PacketFieldType<Chat> CHAT=new PacketFieldType<>("CHAT",1,1048579, new IOFunction<>() {
        @Override
        public void write(@NotNull Chat value, @NotNull DataOutputStream output) throws IOException {
            Util.writeString(value.toString(),StandardCharsets.UTF_8,output);
        }

        @Override
        public Chat read(@NotNull DataInputStream input) throws IOException {
            return Chat.read(input);
        }

        @Override
        public int getLength(@NotNull Chat value) {
            return Util.getVarLength(value.toString().length())+value.toString().getBytes(StandardCharsets.UTF_8).length;
        }
    });
    public static final PacketFieldType<Integer> INT=new PacketFieldType<>("INT", 32, 32, new IOFunction<>() {
        @Override
        public void write(@NotNull Integer value, @NotNull DataOutputStream output) throws IOException {
            output.write(value);
        }

        @Override
        public Integer read(@NotNull DataInputStream input) throws IOException {
            return input.readInt();
        }

        @Override
        public int getLength(@NotNull Integer value) {
            return 32;
        }
    });
    public static final PacketFieldType<Boolean> BOOLEAN=new PacketFieldType<>("BOOLEAN", 1, 1, new IOFunction<>() {
        @Override
        public void write(@NotNull Boolean value, @NotNull DataOutputStream output) throws IOException {
            output.writeBoolean(value);
        }

        @Override
        public Boolean read(@NotNull DataInputStream input) throws IOException {
            return input.readBoolean();
        }

        @Override
        public int getLength(@NotNull Boolean value) {
            return 1;
        }
    });
    public static final PacketFieldType<fields.NBT> NBT=new PacketFieldType<>("NBT", 0, -1, new IOFunction<>() {
        @Override
        public void write(@NotNull NBT value, @NotNull DataOutputStream output) throws IOException {
            value.write(output);
        }

        @Override
        public NBT read(@NotNull DataInputStream input) throws IOException {
            return fields.NBT.readDataFromDataInputStream(input);
        }

        @Override
        public int getLength(@NotNull NBT value){
            return -1;
        }
    });
    public static final PacketFieldType<Identifier> IDENTIFIER=new PacketFieldType<>("IDENTIFIER",1,131071, new IOFunction<>() {
        @Override
        public void write(@NotNull Identifier value, @NotNull DataOutputStream output) throws IOException {
            Util.writeString(value.toString(), StandardCharsets.UTF_8,output);
        }
        @Override
        public Identifier read(@NotNull DataInputStream input) throws IOException {
            return Identifier.getInstance(input);
        }

        @Override
        public int getLength(@NotNull Identifier value) {
            return Util.getVarLength(value.toString().length())+value.toString().getBytes(StandardCharsets.UTF_8).length;
        }
    });
    public static final PacketFieldType<Array<Identifier>> ARRAY_OF_IDENTIFIER=Array.getPacketFieldType(IDENTIFIER);
    public static final PacketFieldType<Integer> UNSIGNEDBYTE=new PacketFieldType<>("UNSIGNEDBYTE", 1, 1, new IOFunction<>() {
        @Override
        public void write(@NotNull Integer value, @NotNull DataOutputStream output) throws IOException {
            output.writeByte(value);
        }

        @Override
        public Integer read(@NotNull DataInputStream input) throws IOException {
            return input.readUnsignedByte();
        }

        @Override
        public int getLength(@NotNull Integer value) {
            return 1;
        }
    });
    public static final PacketFieldType<Byte> BYTE=new PacketFieldType<>("BYTE", 1, 1, new IOFunction<>() {
        @Override
        public void write(@NotNull Byte value, @NotNull DataOutputStream output) throws IOException {
            output.writeByte(value);
        }

        @Override
        public Byte read(@NotNull DataInputStream input) throws IOException {
            return input.readByte();
        }

        @Override
        public int getLength(@NotNull Byte value) {
            return 1;
        }
    });
    public static final PacketFieldType<byte[]> ARRAY_OF_BYTE=new PacketFieldType<>("ARRAY_OF_BYTE", 0, -1, new IOFunction<>() {
        @Override
        public void write(byte @NotNull [] value, @NotNull DataOutputStream output) throws IOException {
            output.write(value);
        }

        @Override
        public byte[] read(@NotNull DataInputStream input) throws IOException {
            return input.readNBytes(Data.size);
        }

        @Override
        public int getLength(byte @NotNull [] value) {
            return value.length*8;
        }
    });
    public static final PacketFieldType<Float> FLOAT=new PacketFieldType<>("FLOAT", 32, 32, new IOFunction<>() {
        @Override
        public void write(@NotNull Float value, @NotNull DataOutputStream output) throws IOException {
            output.writeFloat(value);
        }

        @Override
        public Float read(@NotNull DataInputStream input) throws IOException {
            return input.readFloat();
        }

        @Override
        public int getLength(@NotNull Float value) {
            return 32;
        }
    });
    public static final PacketFieldType<Recipe> RECIPE=new PacketFieldType<>("RECIPE", 0, -1, new IOFunction<>() {
        @Override
        public void write(@NotNull Recipe value, @NotNull DataOutputStream output) throws IOException {
            Recipe.writeRecipe(value,output);
        }

        @Override
        public Recipe read(@NotNull DataInputStream input) throws IOException {
            return Recipe.readRecipe(input);

        }

        @Override
        public int getLength(@NotNull Recipe value) {
            return 0;
        }
    });
    public static final PacketFieldType<Array<Recipe>> ARRAY_OF_RECIPE=Array.getPacketFieldType(RECIPE);
    public static final PacketFieldType<Tag> TAG=new PacketFieldType<>("TAG", 0, -1, new IOFunction<>() {
        @Override
        public void write(@NotNull Tag value, @NotNull DataOutputStream output) throws IOException {
            Tag.write(value, output);
        }

        @Override
        public Tag read(@NotNull DataInputStream input) throws IOException {
            return Tag.read(input);
        }

        @Override
        public int getLength(@NotNull Tag value) {
            return 0;
        }
    });
    public static final PacketFieldType<Tags> TAGS=new PacketFieldType<>("TAGS", 0, -1, new IOFunction<>() {
        @Override
        public void write(@NotNull Tags value, @NotNull DataOutputStream output) throws IOException {
            Tags.write(value, output);
        }

        @Override
        public Tags read(@NotNull DataInputStream input) throws IOException {
            return Tags.read(input);
        }

        @Override
        public int getLength(@NotNull Tags value) {
            return 0;
        }
    });
    public static final PacketFieldType<Node> NODE=new PacketFieldType<>("NODE", 0, -1, new IOFunction<Node>() {
        @Override
        public void write(@NotNull Node value, @NotNull DataOutputStream output) throws IOException {
            NodeUtil.write(value,output);
        }

        @Override
        public Node read(@NotNull DataInputStream input) throws IOException {
            return NodeUtil.read(input);
        }

        @Override
        public int getLength(@NotNull Node value) {
            return 0;
        }
    });
    public static final PacketFieldType<Array<Node>> ARRAY_OF_NODE=Array.getPacketFieldType(NODE);
    private final IOFunction<K> ioFunction;
    public final String name;
    public final int max;
    public final int min;
    public PacketFieldType(@NotNull String name, int min, int max,@NotNull IOFunction<K> ioFunction){
        this.ioFunction=ioFunction;
        this.name=name;
        this.max=max;
        this.min=min;
    }
    public void write(@NotNull K value,@NotNull DataOutputStream output) throws IOException {
        ioFunction.write(value,output);
    }
    public K read(@NotNull DataInputStream input) throws IOException{
        return ioFunction.read(input);
    }
    public int getLength(@NotNull K value){
        return ioFunction.getLength(value);
    }

    public IOFunction<K> getIOFunction() {
        return ioFunction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return name.equals(((PacketFieldType<?>)o).name);
    }

    @Override
    public String toString() {
        return "PacketFieldType="+name;
    }
}
