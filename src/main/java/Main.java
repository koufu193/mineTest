import Packet.*;
import fields.NBT;
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
            System.out.println(PacketData.fromInputStream(sender.getInput(), PacketType.Sender.Client,sender.state, sender.compressed_chunk_size));
            PacketData.fromInputStream(sender.getInput(), PacketType.Sender.Client,sender.state, sender.compressed_chunk_size);
            Util.sendPacket(0x0a,Util.getData(b->{
                Util.writeString("minecraft:brand",StandardCharsets.UTF_8,b);
                Util.writeString(ClientInfo.getClientName(),StandardCharsets.UTF_8,b);
            }),sender.getOutput(),sender.compressed_chunk_size);
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
