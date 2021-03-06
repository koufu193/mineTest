package util;

import Packet.PacketFieldType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class GroupData<V>{
    PacketFieldType<V> field;
    Consumer<V> write;
    Consumer<V> read;
    public GroupData(@NotNull PacketFieldType<V> field,@NotNull Consumer<V> write,@NotNull Consumer<V> read){
        this.field=field;
        this.write=write;
        this.read=read;
    }

    public PacketFieldType<V> getField() {
        return field;
    }

    public Consumer<V> getRead() {
        return read;
    }

    public Consumer<V> getWrite() {
        return write;
    }
}
