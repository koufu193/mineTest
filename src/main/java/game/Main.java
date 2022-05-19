package game;

import Packet.*;
import fields.Array;
import fields.Identifier;
import game.PacketSender;
import game.User;
import game.World;
import game.entity.Player;
import game.entity.metadata.PlayerMetadata;
import info.ClientInfo;
import util.Util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Main {
    static PacketSender sender=null;
    static User user;
    public static void main(String[] args) {
        try{
            /*try(PacketSender s=new PacketSender("localhost",25565)){
                s.sendHandshake(1);
                s.state= PacketType.PacketState.Status;
                Util.sendPacket(0x00,new byte[0],s);
                System.out.println(PacketData.fromInputStream(s, PacketType.Sender.Client));
                Util.sendPacket(0x01, ByteBuffer.allocate(8).putLong(System.currentTimeMillis()).array(),s);
                System.out.println(PacketData.fromInputStream(s, PacketType.Sender.Client));
            }catch (IOException e) {
                e.printStackTrace();
                System.out.println("closed");
            }Status*/
            sender = new PacketSender("localhost", 25565);
            user=sender.sendLoginPacket("example");
            System.out.println("name:"+user.getName()+",UUID:"+user.getUUID());
            PacketData d=PacketData.fromInputStream(sender.getInput(), PacketType.Sender.Client, sender.state, sender.compressed_chunk_size);//Join Game
            for(Identifier identifier:((Array<Identifier>)d.getData().get(5).value).data){
                sender.getServer().getWorlds().add(new World(identifier));
            }
            user.setWorld(sender.getServer().getWorld((Identifier)d.getData().get(8).value));
            user.getWorld().getEntities().add(new Player(user.getUUID(),(Integer)d.getData().get(0).value,new PlayerMetadata()));
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
            PacketData.fromInputStream(sender, PacketType.Sender.Client);//Unlock Recipes
            System.out.println(PacketData.fromInputStream(sender, PacketType.Sender.Client));
            System.out.println(PacketData.fromInputStream(sender, PacketType.Sender.Client));
            System.out.println(PacketData.fromInputStream(sender, PacketType.Sender.Client));
            System.out.println(PacketData.fromInputStream(sender, PacketType.Sender.Client));
            System.out.println(PacketData.fromInputStream(sender, PacketType.Sender.Client));
            System.out.println(PacketData.fromInputStream(sender, PacketType.Sender.Client));
            System.out.println(PacketData.fromInputStream(sender, PacketType.Sender.Client));
            System.out.println(PacketData.fromInputStream(sender, PacketType.Sender.Client));
            System.out.println(PacketData.fromInputStream(sender, PacketType.Sender.Client));

            Util.sendPacket(0x03, Util.getData(b ->Util.writeString("aaaaa", StandardCharsets.UTF_8, b)), sender.getOutput(), sender.compressed_chunk_size);
            while (true) {
                Thread.sleep(1000);
            }
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        } finally {
            try {
                if (sender != null) {
                    sender.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public static World getWorld(){
        return user.getWorld();
    }
}
