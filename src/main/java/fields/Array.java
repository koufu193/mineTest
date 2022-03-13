package fields;

import Packet.PacketFieldType;
import fields.array.Data;
import util.IOFunction;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Array<T>{
    public List<T> data=new ArrayList<>();
    public final IOFunction<T> func;
    public Array(List<T> data, IOFunction<T> func){
        this.data.addAll(data);
        this.func=func;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    public void write(DataOutputStream output) throws IOException {
        for(T data:this.data){
            func.write(data,output);
        }
    }
    public static <K> Array<K> read(DataInputStream input, IOFunction<K> func,int size) throws IOException {
        List<K> list= new ArrayList<>(size);
        for(int i=0;i<size;i++){
            list.add(func.read(input));
        }
        return new Array<>(list, func);
    }
    public static <K> PacketFieldType<Array<K>> getPacketFieldType(PacketFieldType<K> field){
        if(field==null){
            throw new IllegalArgumentException("fieldがnullです");
        }
        return new PacketFieldType<>("ARRAY_OF_" + field.name, 0, -1, new IOFunction<Array<K>>() {
            @Override
            public void write(Array<K> value, DataOutputStream output) throws IOException {
                value.write(output);
            }

            @Override
            public Array<K> read(DataInputStream input) throws IOException {
                return Array.read(input, field.getIOFunction(), Data.size);
            }
            public Array<K> read(DataInputStream input,int size) throws IOException {
                return Array.read(input, field.getIOFunction(),size);
            }
            @Override
            public int getLength(Array<K> value) {
                return value.data.size();
            }
        });
    }
}
