package util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GroupDataFunction<V> implements IOFunction<V> {
    GroupData<V> data;
    public GroupDataFunction(GroupData<V> data){
        this.data=data;
    }
    @Override
    public void write(V value, DataOutputStream output) throws IOException {
        data.getWrite().accept(value);
        data.getField().write(value,output);
    }

    @Override
    public V read(DataInputStream input) throws IOException {
        V result=data.getField().read(input);
        data.read.accept(result);
        return result;
    }

    @Override
    public int getLength(V value) {
        return data.getField().getLength(value);
    }
}
