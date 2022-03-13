import Packet.PacketData;
import Packet.PacketType;
import util.Util;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PacketSender {
    private DataInputStream input;
    private DataOutputStream output;
    private final Socket socket;
    private String host;
    private int port;
    public int compressed_chunk_size=-10;
    public PacketType.PacketState state= PacketType.PacketState.Login;
    public PacketSender(String host,int port) throws IOException {
        this.host=host;
        this.port=port;
        socket=new Socket(host,port);
        input=new DataInputStream(socket.getInputStream());
        output=new DataOutputStream(socket.getOutputStream());
    }
    public void disconnect() throws IOException {
        checkSocket();
        socket.close();
    }
    public void sendLoginHandShake() throws IOException{
        checkSocket();
        Util.sendPacket(0x00,Util.getData(b->{
            Util.writeVarInt(757,b);
            Util.writeString(host, StandardCharsets.UTF_8,b);
            b.writeShort(port);
            Util.writeVarInt(2,b);
        }),output);
        state= PacketType.PacketState.Login;
    }
    public void sendPacket(PacketData packet) throws IOException{
        checkSocket();
        if(compressed_chunk_size<0) {
            Util.sendPacket(packet.getPacketID(), packet.getData(), output);
        }else{
            Util.sendPacket(packet.getPacketID(), packet.getData(), output,compressed_chunk_size);
        }
    }
    public DataInputStream getInput() {
        return input;
    }

    private void checkSocket(){
        if(socket.isClosed()){
            new IllegalStateException("Socketがすでにcloseされています");
        }
    }

    public DataOutputStream getOutput() {
        return output;
    }
}
