package Packet;

import org.jetbrains.annotations.NotNull;
import util.GroupData;
import util.GroupDataFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PacketFieldBuilder {
    List<GroupData<?>> list = new ArrayList<>();

    public static PacketFieldBuilder makeBlock() {
        return new PacketFieldBuilder();
    }

    public <V> PacketFieldBuilder add(@NotNull PacketFieldType<V> field,@NotNull Consumer<V> write,@NotNull Consumer<V> read) {
        list.add(new GroupData<>(field, write, read));
        return this;
    }

    public <V> PacketFieldBuilder add(@NotNull GroupData<V> groupData) {
        list.add(groupData);
        return this;
    }

    public int size() {
        return list.size();
    }

    public PacketFieldType<?>[] build() {
        return list.stream().map(b -> new PacketFieldType(b.getField().name, b.getField().min, b.getField().max, new GroupDataFunction(b)
        )).toArray(PacketFieldType[]::new);
    }
    public <V> PacketFieldBuilder add(@NotNull PacketFieldType<V> field){
        return add(field,b->{},b->{});
    }
}
