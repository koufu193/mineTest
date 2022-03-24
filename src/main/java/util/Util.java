package util;

import Packet.PacketValue;
import game.PacketSender;
import org.jetbrains.annotations.NotNull;
import org.xerial.snappy.Snappy;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.zip.Deflater;

public class Util {
    public static int readVarInt(@NotNull DataInputStream input) throws IOException {
        int value = 0;
        int length = 0;
        byte currentByte;
        while (true) {
            currentByte = input.readByte();
            value |= (currentByte & 0x7F) << (length * 7);

            length += 1;
            if (length > 5) {
                throw new RuntimeException("VarIntが大きすぎます");
            }

            if ((currentByte & 0x80) != 0x80) {
                break;
            }
        }
        return value;
    }
    public static int bitmask(int value,int bitmask){
        return value&bitmask;
    }
    public static long readVarLong(@NotNull DataInputStream input) throws IOException {
        long value = 0;
        int length = 0;
        byte currentByte;

        while (true) {
            currentByte = input.readByte();
            value |= (currentByte & 0x7F) << (length * 7);

            length += 1;
            if (length > 10) {
                throw new RuntimeException("VarLongが大きすぎます");
            }

            if ((currentByte & 0x80) != 0x80) {
                break;
            }
        }
        return value;
    }
    public static void writeVarInt(int value,@NotNull DataOutputStream output) throws IOException {
        while (true) {
            if ((value & ~0x7F) == 0) {
                output.writeByte(value);
                return;
            }

            output.writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
    }
    public static void writeVarLong(long value,@NotNull DataOutputStream output) throws IOException{
        while (true) {
            if ((value & ~0x7F) == 0) {
                output.writeLong(value);
                return;
            }

            output.writeLong((value & 0x7F) | 0x80);
            value >>>= 7;
        }
    }
    public static int getVarLength(long value){
        int length=0;
        while (true) {
            if ((value & ~0x7F) == 0) {
                length++;
                return length;
            }
            length++;
            value >>>= 7;
        }
    }
    public static void writeString(@NotNull String string,@NotNull Charset charset,@NotNull DataOutputStream output) throws IOException {
        byte [] bytes = string.getBytes(charset);
        writeVarInt(bytes.length,output);
        output.write(bytes);
    }
    public static String readString(@NotNull DataInputStream input) throws IOException{
        return new String(input.readNBytes(Util.readVarInt(input)));
    }
    public static byte[] getData(@NotNull Custom<DataOutputStream> func) throws IOException {
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();
        DataOutputStream output=new DataOutputStream(buffer);
        func.accept(output);
        return buffer.toByteArray();
    }
    public static void sendPacket(int PacketID,@NotNull byte[] data,@NotNull DataOutputStream output) throws IOException{
        writeVarInt(getVarLength(PacketID)+data.length,output);
        writeVarInt(PacketID,output);
        output.write(data);
    }
    public static void sendPacket(int PacketID,@NotNull byte[] data,@NotNull DataOutputStream output,int Compressed_chunk_size) throws IOException {
        if (Compressed_chunk_size < 0) {
            sendPacket(PacketID, data, output);
            return;
        }
        byte[] packetData = getData(b -> {
            writeVarInt(PacketID, b);
            b.write(data);
        });
        if(packetData.length<Compressed_chunk_size){
            writeVarInt(packetData.length+1,output);
            writeVarInt(0,output);
            writeVarInt(PacketID,output);
            output.write(data);
            return;
        }
        byte[] compressedData=Snappy.compress(packetData);
        writeVarInt(compressedData.length+getVarLength(packetData.length),output);
        writeVarInt(packetData.length, output);
        output.write(compressedData);
    }
    public static void sendPacket(int PacketID,@NotNull List<PacketValue> value,@NotNull DataOutputStream output) throws IOException{
        if(value.contains(null)){
            throw new IllegalArgumentException("List<PakcetValue>にnullが入っています");
        }
        byte[] data=getData(b->{
            for(PacketValue packetValue:value){
                packetValue.TYPE.write(packetValue.value,b);
            }
        });
        sendPacket(PacketID,data,output);
    }
    public static void sendPacket(int PacketID,@NotNull List<PacketValue> value,@NotNull DataOutputStream output,int compressed_chunk_size) throws IOException{
        if(value.contains(null)){
            throw new IllegalArgumentException("List<PakcetValue>にnullが入っています");
        }
        Objects.requireNonNull(output);
        byte[] data=getData(b->{
            for(PacketValue packetValue:value){
                packetValue.TYPE.write(packetValue.value,b);
            }
        });
        sendPacket(PacketID,data,output,compressed_chunk_size);
    }
    public static void sendPacket(int PacketID,@NotNull byte[] data,@NotNull PacketSender sender) throws IOException{
        sendPacket(PacketID,data,sender.getOutput(),sender.compressed_chunk_size);
    }
}
