package Packet;

import fields.Array;
import fields.array.Data;
import org.jetbrains.annotations.NotNull;
import util.IOFunction;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public class PacketType {
    public static class Login{
        public static class Client extends PacketNameTemplate{
            public static final HashMap<Integer,PacketInfo> Packets=new HashMap<>();
            public static final PacketInfo Set_Compression=new PacketInfo(0x03,"Set_Compression",PacketFieldType.VARINT);
            public static final PacketInfo Login_Success=new PacketInfo(0x02,"Login_Success",PacketFieldType.UUID,PacketFieldType.STRING);
            public static final PacketInfo Disconnect=new PacketInfo(0x00,"Disconnect(Login)",PacketFieldType.CHAT);
        }
    }
    public static class Status{
        public static class Client extends PacketNameTemplate{
            public static final HashMap<Integer,PacketInfo> Packets=new HashMap<>();
            public static final PacketInfo Response = new PacketInfo(0x00, "Response", PacketFieldType.STRING);
            public static final PacketInfo Pong = new PacketInfo(0x01, "Pong", PacketFieldType.LONG);
        }
    }
    public static class Play{
        public static class Client extends PacketNameTemplate{
            public static final HashMap<Integer,PacketInfo> Packets=new HashMap<>();
            public static final PacketInfo JOIN_GAME=new PacketInfo(0x26,"JOIN_GAME",
                    PacketFieldBuilder.makeBlock().
                            add(PacketFieldType.INT).
                            add(PacketFieldType.BOOLEAN).
                            add(PacketFieldType.UNSIGNEDBYTE).
                            add(PacketFieldType.BYTE).
                            add(PacketFieldType.VARINT,b->{}, Data::setSize).
                            add(PacketFieldType.ARRAY_OF_IDENTIFIER).
                            add(PacketFieldType.NBT).
                            add(PacketFieldType.NBT).
                            add(PacketFieldType.IDENTIFIER).
                            add(PacketFieldType.LONG).
                            add(PacketFieldType.VARINT).
                            add(PacketFieldType.VARINT).
                            add(PacketFieldType.VARINT).
                            add(PacketFieldType.BOOLEAN).
                            add(PacketFieldType.BOOLEAN).
                            add(PacketFieldType.BOOLEAN).
                            add(PacketFieldType.BOOLEAN).build());
            public static final PacketInfo PLUGIN_MESSAGE=new PacketInfo(0x18,"PLUGIN_MESSAGE",PacketFieldBuilder.makeBlock().add(
                    PacketFieldType.IDENTIFIER,a->{},b->{
                        int len=(b.isName_nulled()?b.getThing():b.toString()).length();
                        Data.setSize(Data.packet_max-len-Util.getVarLength(len));
                    }
            ).add(PacketFieldType.ARRAY_OF_BYTE).build());
            public static final PacketInfo SERVER_DIFFICULTY=new PacketInfo(0x0e,"SERVER_DIFFICULTY",PacketFieldType.UNSIGNEDBYTE,PacketFieldType.BOOLEAN);
            public static final PacketInfo PLAYER_ABILITIES=new PacketInfo(0x32,"PLAYER_ABILITIES",PacketFieldType.BYTE,PacketFieldType.FLOAT,PacketFieldType.FLOAT);
            public static final PacketInfo HELD_ITEM_CHANGE=new PacketInfo(0x48,"HELD_ITEM_CHANGE",PacketFieldType.BYTE);
            public static final PacketInfo DECLARE_RECIPES=new PacketInfo(0x66,"DECLARE_RECIPES",PacketFieldBuilder.makeBlock().add(
                    PacketFieldType.VARINT,a->{}, Data::setSize
            ).add(PacketFieldType.ARRAY_OF_RECIPE).build());
            public static final PacketInfo TAGS=new PacketInfo(0x67,"TAGS",PacketFieldBuilder.makeBlock().add(PacketFieldType.VARINT,a->{}, Data::setSize).add(Array.getPacketFieldType(PacketFieldType.TAGS)).build());
            public static final PacketInfo ENTITY_STATUS=new PacketInfo(0x1b,"ENTITY_STATUS",PacketFieldType.INT,PacketFieldType.BYTE);
            public static final PacketInfo DECLARE_COMMANDS=new PacketInfo(0x12,"DECLARE_COMMANDS",PacketFieldBuilder.makeBlock().add(PacketFieldType.VARINT,a->{}, Data::setSize).add(PacketFieldType.ARRAY_OF_NODE).add(PacketFieldType.VARINT).build());
        }
    }
    static{
        registerPacketInfo(Login.Client.Set_Compression,Login.Client.Packets);
        registerPacketInfo(Login.Client.Login_Success, Login.Client.Packets);
        registerPacketInfo(Login.Client.Disconnect,Login.Client.Packets);
        registerPacketInfo(Status.Client.Response,Status.Client.Packets);
        registerPacketInfo(Status.Client.Pong,Status.Client.Packets);
        registerPacketInfo(Play.Client.JOIN_GAME,Play.Client.Packets);
        registerPacketInfo(Play.Client.PLUGIN_MESSAGE,Play.Client.Packets);
        registerPacketInfo(Play.Client.PLAYER_ABILITIES,Play.Client.Packets);
        registerPacketInfo(Play.Client.SERVER_DIFFICULTY,Play.Client.Packets);
        registerPacketInfo(Play.Client.HELD_ITEM_CHANGE,Play.Client.Packets);
        registerPacketInfo(Play.Client.DECLARE_RECIPES,Play.Client.Packets);
        registerPacketInfo(Play.Client.TAGS,Play.Client.Packets);
        registerPacketInfo(Play.Client.ENTITY_STATUS,Play.Client.Packets);
    }
    public static Set<HashMap<Integer,PacketInfo>> getClientPackets(){
        Set<HashMap<Integer,PacketInfo>> result=new HashSet<>();
        result.add(Login.Client.Packets);
        result.add(Status.Client.Packets);
        return result;
    }
    private static PacketInfo registerPacketInfo(@NotNull PacketInfo info,@NotNull HashMap<Integer,PacketInfo> packetMap){
        packetMap.put(info.PacketID,info);
        return info;
    }
    public static HashMap<Integer,PacketInfo> getPacketDatFromPacketStatus(@NotNull PacketState status,@NotNull Sender sender){
        switch (status){
            case Play:
                return (sender==Sender.Client)?Play.Client.Packets:null;
            case Login:
                return (sender==Sender.Client)?Login.Client.Packets:null;
            case Status:
                return (sender==Sender.Client)?Status.Client.Packets:null;
        }
        return null;
    }
    public static List<PacketInfo> getPacketsFromID(int PacketID,@NotNull Sender sender){
        List<PacketInfo> result=new ArrayList<>();
        if(sender==Sender.Client){
            for(HashMap<Integer,PacketInfo> map:getClientPackets()){
                result.add(map.get(PacketID));
            }
        }
        result.removeIf(Objects::isNull);
        return result;
    }
    public static List<PacketInfo> getPacketsFromID(int PacketID,HashMap<Integer,PacketInfo>... data) {
        if(data!=null) {
            List<PacketInfo> result = new ArrayList<>();
            for(HashMap<Integer,PacketInfo> map:data) {
                if(map!=null) {
                    result.add(map.get(PacketID));
                }
            }
            result.removeIf(Objects::isNull);
            return result;
        }
        return null;
    }
    public static PacketInfo getPacketFromID(int PacketID,@NotNull HashMap<Integer,PacketInfo> packetMap){
        return packetMap.get(PacketID);
    }
    public enum Sender{
        Client,Server;
    }
    public enum PacketState{
        Status,Login,Play;
    }
}
abstract class PacketNameTemplate{
}
