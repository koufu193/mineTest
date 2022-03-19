package Packet;

import fields.Array;
import fields.Chat;
import fields.Identifier;
import fields.NBT;
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
        public void write(Integer value, DataOutputStream output) throws IOException {
            Util.writeVarInt(value, output);
        }

        @Override
        public Integer read(DataInputStream input) throws IOException {
            return Util.readVarInt(input);
        }

        @Override
        public int getLength(Integer value) {
            return Util.getVarLength(value);
        }
    });
    public static final PacketFieldType<UUID> UUID=new PacketFieldType<>("UUID",16,16, new IOFunction<>() {
        @Override
        public void write(java.util.UUID value, DataOutputStream output) throws IOException {
            output.writeLong(value.getMostSignificantBits());
            output.writeLong(value.getLeastSignificantBits());
        }

        @Override
        public java.util.UUID read(DataInputStream input) throws IOException {
            return new UUID(input.readLong(), input.readLong());
        }

        @Override
        public int getLength(java.util.UUID value) {
            return 16;
        }
    });
    public static final PacketFieldType<String> STRING=new PacketFieldType<>("STRING",1,131071, new IOFunction<>() {
        @Override
        public void write(String value, DataOutputStream output) throws IOException {
            Util.writeString(value, StandardCharsets.UTF_8,output);
        }

        @Override
        public String read(DataInputStream input) throws IOException {
            return Util.readString(input);
        }

        @Override
        public int getLength(String value) {
            return Util.getVarLength(value.length())+value.getBytes(StandardCharsets.UTF_8).length;
        }
    });
    public static final PacketFieldType<Long> LONG=new PacketFieldType<>("LONG",64,64, new IOFunction<>() {
        @Override
        public void write(Long value, DataOutputStream output) throws IOException {
            output.writeLong(value);
        }

        @Override
        public Long read(DataInputStream input) throws IOException {
            return input.readLong();
        }

        @Override
        public int getLength(Long value) {
            return 64;
        }
    });
    public static final PacketFieldType<Chat> CHAT=new PacketFieldType<>("CHAT",1,1048579, new IOFunction<>() {
        @Override
        public void write(Chat value, DataOutputStream output) throws IOException {
            Util.writeString(value.toString(),StandardCharsets.UTF_8,output);
        }

        @Override
        public Chat read(DataInputStream input) throws IOException {
            return new Chat(Util.readString(input));
        }

        @Override
        public int getLength(Chat value) {
            return Util.getVarLength(value.toString().length())+value.toString().getBytes(StandardCharsets.UTF_8).length;
        }
    });
    public static final PacketFieldType<Integer> INT=new PacketFieldType<>("INT", 32, 32, new IOFunction<>() {
        @Override
        public void write(Integer value, DataOutputStream output) throws IOException {
            output.write(value);
        }

        @Override
        public Integer read(DataInputStream input) throws IOException {
            return input.readInt();
        }

        @Override
        public int getLength(Integer value) {
            return 32;
        }
    });
    public static final PacketFieldType<Boolean> BOOLEAN=new PacketFieldType<>("BOOLEAN", 1, 1, new IOFunction<>() {
        @Override
        public void write(Boolean value, DataOutputStream output) throws IOException {
            output.writeBoolean(value);
        }

        @Override
        public Boolean read(DataInputStream input) throws IOException {
            return input.readBoolean();
        }

        @Override
        public int getLength(Boolean value) {
            return 1;
        }
    });
    public static final PacketFieldType<fields.NBT> NBT=new PacketFieldType<>("NBT", 0, -1, new IOFunction<>() {
        @Override
        public void write(NBT value, DataOutputStream output) throws IOException {
            value.write(output);
        }

        @Override
        public NBT read(DataInputStream input) throws IOException {
            return fields.NBT.readDataFromDataInputStream(input);
        }

        @Override
        public int getLength(NBT value){
            return -1;
        }
    });
    public static final PacketFieldType<Identifier> IDENTIFIER=new PacketFieldType<>("IDENTIFIER",1,131071, new IOFunction<>() {
        @Override
        public void write(Identifier value, DataOutputStream output) throws IOException {
            Util.writeString(value.toString(), StandardCharsets.UTF_8,output);
        }
        @Override
        public Identifier read(DataInputStream input) throws IOException {
            return Identifier.getInstance(input);
        }

        @Override
        public int getLength(Identifier value) {
            return Util.getVarLength(value.toString().length())+value.toString().getBytes(StandardCharsets.UTF_8).length;
        }
    });
    public static final PacketFieldType<Array<Identifier>> ARRAY_OF_IDENTIFIER=Array.getPacketFieldType(IDENTIFIER);
    public static final PacketFieldType<Integer> UNSIGNEDBYTE=new PacketFieldType<>("UNSIGNEDBYTE", 1, 1, new IOFunction<>() {
        @Override
        public void write(Integer value, DataOutputStream output) throws IOException {
            output.writeByte(value);
        }

        @Override
        public Integer read(DataInputStream input) throws IOException {
            return input.readUnsignedByte();
        }

        @Override
        public int getLength(Integer value) {
            return 1;
        }
    });
    public static final PacketFieldType<Byte> BYTE=new PacketFieldType<>("BYTE", 1, 1, new IOFunction<>() {
        @Override
        public void write(Byte value, DataOutputStream output) throws IOException {
            output.writeByte(value);
        }

        @Override
        public Byte read(DataInputStream input) throws IOException {
            return input.readByte();
        }

        @Override
        public int getLength(Byte value) {
            return 1;
        }
    });
    private IOFunction<K> ioFunction;
    public final String name;
    public final int max;
    public final int min;
    public PacketFieldType(String name,int min,int max,IOFunction<K> ioFunction){
        this.ioFunction=ioFunction;
        this.name=name;
        this.max=max;
        this.min=min;
    }
    public void write(K value,DataOutputStream output) throws IOException {
        ioFunction.write(value,output);
    }
    public K read(DataInputStream input) throws IOException{
        return ioFunction.read(input);
    }
    public int getLength(K value){
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
