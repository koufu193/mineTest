package Packet;

public class PacketValue<T>{
    public final PacketFieldType<T> TYPE;
    public T value;
    public PacketValue(PacketFieldType<T> type,T value){
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
