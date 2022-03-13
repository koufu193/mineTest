package Packet;

public class PacketInfo {
    public final int PacketID;
    public final PacketFieldType[] types;
    public final String name;
    public PacketInfo(int PacketID,String name,PacketFieldType... types){
        this.PacketID=PacketID;
        this.types=types;
        this.name=name;
    }
}
