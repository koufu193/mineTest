import Packet.*;
import fields.NBT;
import util.Util;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        PacketSender sender=null;
        UUID uuid;
        try{
            sender = new PacketSender("localhost", 25565);
            sender.sendLoginHandShake();
            Util.sendPacket(0x00, Util.getData(b -> Util.writeString("example", StandardCharsets.UTF_8, b)), sender.getOutput());
            System.out.println(PacketType.Login.Client.Set_Compression.PacketID);
            PacketData data = PacketData.fromInputStream(sender.getInput(), PacketType.Sender.Client, sender.state);
            if (data.getPacketID() == PacketType.Login.Client.Set_Compression.PacketID) {
                sender.compressed_chunk_size = (int) data.getData().get(0).value;
                System.out.println(data);
            }
            System.out.println(PacketData.fromInputStream(sender.getInput(), PacketType.Sender.Client, sender.state, sender.compressed_chunk_size));
            sender.state= PacketType.PacketState.Play;
            PacketData d=PacketData.fromInputStream(sender.getInput(), PacketType.Sender.Client, sender.state, sender.compressed_chunk_size);
            try(DataOutputStream output=new DataOutputStream(new FileOutputStream("qwerty.txt"))){
                PacketValue value=d.getData().get(6);
                ((NBT)value.value).write(output);
                System.out.println(value.value);
            }
            Util.sendPacket(0x03, Util.getData(b -> Util.writeString("aaaaa", StandardCharsets.UTF_8, b)), sender.getOutput(), sender.compressed_chunk_size);
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
