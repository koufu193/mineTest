package Packet;

import util.Data;
import game.PacketSender;
import org.jetbrains.annotations.NotNull;
import util.Util;

import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class PacketData {
    int PacketID;
    public List<PacketValue> data=new ArrayList<>();
    static Inflater inflater=new Inflater();
    public PacketData(@NotNull PacketInfo info){
        this.PacketID=info.PacketID;
        for(int i=0;i<info.types.length;i++){
            data.add(new PacketValue(info.types[i],null));
        }
    }
    public List<PacketValue> getData() {
        return data;
    }

    public int getPacketID() {
        return PacketID;
    }

    @Override
    public String toString() {
        return "PacketData{" +
                "PacketID=" + PacketID +
                ", data=" + data +
                '}';
    }

    public static PacketData fromInputStream(@NotNull DataInputStream input,@NotNull PacketType.Sender sender,@NotNull PacketType.PacketState state) throws IOException {
        //DataInputStreamからパケットに変換処理
        int length=Util.readVarInt(input);
        int PacketID=Util.readVarInt(input);
        length-=Util.getVarLength(PacketID);
        return getPacketDataFromData(length,PacketID,input,sender,state);
    }
    public static PacketData fromInputStream(@NotNull PacketSender packetSender,@NotNull PacketType.Sender sender) throws IOException{
        return fromInputStream(packetSender.getInput(),sender,packetSender.state,packetSender.compressed_chunk_size);
    }
    public static PacketData fromInputStream(@NotNull DataInputStream input,@NotNull PacketType.Sender sender,@NotNull PacketType.PacketState state, int compressed_chunk_size) throws IOException {
        if(compressed_chunk_size<0){
            return fromInputStream(input,sender,state);
        }
        int all_length=Util.readVarInt(input);
        int data_size=Util.readVarInt(input);
        PacketData data=null;
        all_length-=Util.getVarLength(data_size);
        if(data_size<=0){
            int PacketID=Util.readVarInt(input);
            data=getPacketDataFromData(all_length-Util.getVarLength(PacketID),PacketID,input,sender,state);
        }else{
            int finalAll_length = all_length;
            byte[] bytes=Util.getData(b->{
                for(int i = 0; i< finalAll_length; i++){
                    b.write(input.readByte());
                }
            });
            inflater.setInput(bytes);
            byte[] uncompressedData=new byte[data_size];
            int len=0;
            try {
                len = inflater.inflate(uncompressedData);
            }catch (DataFormatException e){
                e.printStackTrace();
            }
            inflater.reset();
            try(DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(uncompressedData))){
                int packetID=Util.readVarInt(dataInput);
                data = getPacketDataFromData(uncompressedData.length-Util.getVarLength(packetID),packetID, dataInput, sender, state);
            }

        }
        return data;
    }
    private static PacketData getPacketDataFromData(int size,int PacketID,@NotNull DataInputStream input,@NotNull PacketType.Sender sender,@NotNull PacketType.PacketState state) throws IOException{
        PacketInfo info=PacketType.getPacketDatFromPacketStatus(state,sender).get(PacketID);
        Data.setPacket_max(size);
        if(info!=null) {
            PacketData data=new PacketData(info);
            PacketFieldType<?>[] types = info.types;
            for (int i = 0; i < types.length; i++) {
                data.getData().get(i).value = types[i].read(input);
            }
            data.getData().removeIf(b->b.value==null);
            return data;
        }
        System.out.println("What is "+PacketID);
        return null;
    }
}
