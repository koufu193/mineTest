package Packet;

import org.jetbrains.annotations.NotNull;

public class PacketValue<T>{
    public final PacketFieldType<T> TYPE;
    public T value;
    public PacketValue(@NotNull PacketFieldType<T> type,@NotNull T value){
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
