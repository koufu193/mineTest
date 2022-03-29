import Packet.*;
import game.PacketSender;
import game.User;
import info.ClientInfo;
import util.Util;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        PacketSender sender=null;
        User user;
        try{
            sender = new PacketSender("localhost", 25565);
            user=sender.sendLoginPacket("example");
            System.out.println("name:"+user.getName()+",UUID:"+user.getUUID());
            PacketData.fromInputStream(sender.getInput(), PacketType.Sender.Client, sender.state, sender.compressed_chunk_size);//Join Game
            PacketData.fromInputStream(sender.getInput(), PacketType.Sender.Client,sender.state, sender.compressed_chunk_size);
            PacketData.fromInputStream(sender.getInput(), PacketType.Sender.Client,sender.state, sender.compressed_chunk_size);
            System.out.println(PacketData.fromInputStream(sender.getInput(), PacketType.Sender.Client,sender.state, sender.compressed_chunk_size));
            System.out.println(PacketData.fromInputStream(sender.getInput(), PacketType.Sender.Client,sender.state, sender.compressed_chunk_size));
            Util.sendPacket(0x0a,Util.getData(b->{
                Util.writeString("minecraft:brand",StandardCharsets.UTF_8,b);
                Util.writeString(ClientInfo.getClientName(),StandardCharsets.UTF_8,b);
            }),sender.getOutput(),sender.compressed_chunk_size);
            Util.sendPacket(0x05,Util.getData(b->{
                Util.writeString(user.getLocate(),StandardCharsets.UTF_8,b);
                b.writeByte(user.getView_distance());
                Util.writeVarInt(user.getChat_mode(),b);
                b.writeBoolean(user.isChat_color());
                b.writeByte(user.getDisplayed_skin_parts());
                Util.writeVarInt(user.getMain_hand(),b);
                b.writeBoolean(user.isEnable_text_filtering());
                b.writeBoolean(user.isAllow_server_listing());
            }),sender);
            PacketData.fromInputStream(sender, PacketType.Sender.Client);
            PacketData.fromInputStream(sender, PacketType.Sender.Client);//Declare Recipes
            PacketData.fromInputStream(sender, PacketType.Sender.Client);//Tags
            PacketData.fromInputStream(sender, PacketType.Sender.Client);
            PacketData.fromInputStream(sender, PacketType.Sender.Client);//Declare Commands
            Util.sendPacket(0x03, Util.getData(b ->Util.writeString("aaaaa", StandardCharsets.UTF_8, b)), sender.getOutput(), sender.compressed_chunk_size);
            while (true) {
                Thread.sleep(1000);
            }
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        } finally {
            try {
                if (sender != null) {
                    sender.disconnect();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
