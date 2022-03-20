import Packet.PacketData;
import Packet.PacketType;
import game.User;
import util.Util;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class PacketSender {
    private DataInputStream input;
    private DataOutputStream output;
    private final Socket socket;
    private String host;
    private int port;
    public int compressed_chunk_size=-10;
    Logger logger;
    public PacketType.PacketState state= PacketType.PacketState.Login;
    public PacketSender(String host,int port) throws IOException {
        this.host=host;
        this.port=port;
        socket=new Socket(host,port);
        input=new DataInputStream(socket.getInputStream());
        output=new DataOutputStream(socket.getOutputStream());
        logger=Logger.getLogger(getClass().getName());
    }
    public void disconnect() throws IOException {
        checkSocket();
        socket.close();
    }
    public User sendLoginPacket(String player_name) throws IOException{
        sendLoginHandshake();
        Util.sendPacket(0x00,Util.getData(b->Util.writeString(player_name,StandardCharsets.UTF_8,b)),output);
        PacketData data=PacketData.fromInputStream(input, PacketType.Sender.Client,state,compressed_chunk_size);
        while(data.getPacketID()!=PacketType.Login.Client.Login_Success.PacketID){
            switch (data.getPacketID()){
                case 0x03:
                    compressed_chunk_size=(Integer)data.getData().get(0).value;
                    break;
            }
            data=PacketData.fromInputStream(input, PacketType.Sender.Client,state,compressed_chunk_size);
        }
        state=PacketType.PacketState.Play;
        return new User((UUID)data.getData().get(0).value,data.data.get(1).value.toString());
    }
    public void sendHandshake(int next_state) throws IOException{
        checkSocket();
        Util.sendPacket(0x00,Util.getData(b->{
            Util.writeVarInt(757,b);
            Util.writeString(host, StandardCharsets.UTF_8,b);
            b.writeShort(port);
            Util.writeVarInt(next_state,b);
        }),output);;
    }
    public void sendLoginHandshake() throws IOException{
        sendHandshake(2);
        state=PacketType.PacketState.Login;
        logger.info("sent LoginHandshake");
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
            throw new IllegalStateException("Socketがすでにcloseされています");
        }
    }

    public DataOutputStream getOutput() {
        return output;
    }
}
