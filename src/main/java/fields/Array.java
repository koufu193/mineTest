package fields;

import Packet.PacketFieldType;
import util.Data;
import org.jetbrains.annotations.NotNull;
import util.IOFunction;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Array<T>{
    public List<T> data=new ArrayList<>();
    public final IOFunction<T> func;
    public Array(@NotNull List<T> data, @NotNull IOFunction<T> func){
        this.data.addAll(data);
        this.func=func;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    public void write(@NotNull DataOutputStream output) throws IOException {
        for(T data:this.data){
            func.write(data,output);
        }
    }
    public static <K> Array<K> read(@NotNull DataInputStream input, @NotNull IOFunction<K> func,int size) throws IOException {
        List<K> list=new ArrayList<>(size);
        for(int i=0;i<size;i++){
            list.add(func.read(input));
        }
        return new Array<>(list, func);
    }
    public static <K> PacketFieldType<Array<K>> getPacketFieldType(@NotNull PacketFieldType<K> field){
        return new PacketFieldType<>("ARRAY_OF_" + field.name, 0, -1, new IOFunction<Array<K>>() {
            @Override
            public void write(@NotNull Array<K> value, @NotNull DataOutputStream output) throws IOException {
                value.write(output);
            }

            @Override
            public Array<K> read(@NotNull DataInputStream input) throws IOException {
                return Array.read(input, field.getIOFunction(), Data.size);
            }
            public Array<K> read(@NotNull DataInputStream input,int size) throws IOException {
                return Array.read(input, field.getIOFunction(),size);
            }
            @Override
            public int getLength(@NotNull Array<K> value) {
                return value.data.size();
            }
        });
    }
}
