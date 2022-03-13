package Packet;

public class PacketValue {
    public final PacketFieldType TYPE;
    public Object value;
    public PacketValue(PacketFieldType type,Object value){
        if(type==null){
            throw new IllegalArgumentException("typeがnullです");
        }
        this.TYPE=type;
        this.value=value;
    }

    @Override
    public String toString() {
        return "PacketValue{" +
                "TYPE=" + TYPE +
                ", value=" + value +
                '}';
    }
}
