package fields;

import fields.nbt.NBTIndex;
import fields.nbt.NBTUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NBT implements Cloneable{
    public String name;
    public List<NBT> data = new ArrayList<>();
    Object value;
    public final int type;
    public int list_type=0;

    public NBT(String name,int type) {
        this.name = name;
        value=null;
        this.type=type;
    }
    public NBT(String name, Object value,int type) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
    public NBT(String name,int type,int list_type) {
        this.name = name;
        this.type=type;
        this.list_type=list_type;
    }
    public Object getValue(){
        return value;
    }
    public void setValue(Object value){
        if(0<data.size()){
            throw new IllegalStateException("dataが既に設定されています");
        }
        this.value=value;
    }
    public List<NBT> getNBT(String path){
        List<NBT> list=new ArrayList<>(data);
        List<NBT> result=new ArrayList<>();
        String[] paths=path.split("\\.");
        for(int i=0;i<paths.length;i++) {
            result.clear();
            int finalI = i;
            list.removeIf(b->!b.name.equals(paths[finalI]));
            if(list.size()<=0){
                return result;
            }
            result.addAll(list);
            list.clear();
            result.forEach(b->list.addAll(b.data));
        }
        return result;
    }
    public NBT add(NBT nbt){
        this.data.add(nbt);
        return this;
    }
    public void write(DataOutputStream output) throws IOException{
        write(output,true,true);
    }
    public void write(DataOutputStream output,boolean needTellType,boolean needTellName) throws IOException{
        if(needTellType) {
            output.writeByte(type);
        }
        if(needTellName) {
            output.writeShort(name.length());
            for (byte b : name.getBytes()) {
                output.write(b);
            }
        }
        if(type==12||type==11||type==7||type==9){
            if(type==9){
                output.writeByte(list_type);
            }
            output.writeInt(data.size());
            for(NBT nbt:data){
                nbt.write(output,false,false);
            }
        }else{
            switch (type) {
                case 10:
                    for (NBT nbt : data) {
                        nbt.write(output, true, true);
                    }
                    output.writeByte(0);
                    break;
                case 8:
                    output.writeShort(value.toString().length());
                    for(byte b:value.toString().getBytes()){
                        output.writeByte(b);
                    }
                    break;
                case 1:
                    if(value instanceof Byte) {
                        output.writeByte((Byte) value);
                    }else{
                        throw new IllegalArgumentException("valueがnullかvalueをByteにキャストできません");
                    }
                    break;
                case 2:
                    if(value instanceof Short) {
                        output.writeShort((Short) value);
                    }else{
                        throw new IllegalArgumentException("valueがnullかvalueをShortにキャストできません");
                    }
                    break;
                case 3:
                    if(value instanceof Integer) {
                        output.writeInt((Integer) value);
                    }else{
                        throw new IllegalArgumentException("valueがnullかvalueをIntegerにキャストできません");
                    }
                    break;
                case 4:
                    if(value instanceof Long){
                        output.writeLong((Long)value);
                    }else{
                        throw new IllegalArgumentException("valueがnullかvalueをLongにキャストできません");
                    }
                    break;
                case 5:
                    if(value instanceof Float){
                        output.writeFloat((Float)value);
                    }else{
                        throw new IllegalArgumentException("valueがnullかvalueをFloatにキャストできません");
                    }
                    break;
                case 6:
                    if(value instanceof Double){
                        output.writeDouble((Double) value);
                    }else{
                        throw new IllegalArgumentException("valueがnullかvalueをDoubleにキャストできません");
                    }
                    break;
                default:
                    return;
            }
        }
    }
    @Override
    public String toString() {
        if (value != null) {
            return (name.isBlank() ? "None" : name) + ":" + value + "\n";
        }
        StringBuilder builder = new StringBuilder((name.isBlank() ? "None" : name) + "{\n");
        for (NBT nbt : data) {
            builder.append(nbt.toString(1)).append("\n");
        }
        builder.append("}");
        return builder.toString();
    }
    public String toString(int num) {
        StringBuilder kuhaku = new StringBuilder();
        for (int i = 0; i < num; i++) {
            kuhaku.append(" ");
        }

        String kuu = kuhaku.toString();
        if (value != null) {
            return kuu+name+":"+value;
        }
        StringBuilder builder = new StringBuilder(kuu+"NBT:" + (name.isBlank() ? "None" : name)+" "+type + "{\n");
        for (NBT nbt : data) {
            builder.append(kuu).append(" ").append(nbt.toString(num + 1)).append("\n");
        }
        builder.append(kuu).append(kuu).append("}");
        return builder.toString();
    }
    public static NBT readDataFromDataInputStream(DataInputStream input) throws IOException{
        return readDataFromDataInputStream(input,new NBT("",10),new NBTIndex(-1),1);
    }
    public static NBT readDataFromDataInputStream(DataInputStream input,NBT compound_nbt,NBTIndex index,int finish_len) throws IOException {
        int data;
        int now_len = 1;
        while (true) {
            data = input.readByte();
            if (data == 10) {
                NBT nbt1 = new NBT(NBTUtil.getName(input), data);
                putData(compound_nbt, nbt1, index);
                index.setLastIndex(getLen(index.getLength(), compound_nbt, index) - 1);
                index.next(-1);
                now_len++;
            } else if (data == 3) {
                putData(compound_nbt, NBTUtil.readInt(input), index);
            } else if (data == 6) {
                putData(compound_nbt, NBTUtil.readDouble(input), index);
            } else if (data == 1) {
                putData(compound_nbt, NBTUtil.readByte(input), index);
            } else if (data == 4) {
                putData(compound_nbt, NBTUtil.readLong(input), index);
            } else if (data == 2) {
                putData(compound_nbt, NBTUtil.readShort(input), index);
            } else if (data == 5) {
                putData(compound_nbt, NBTUtil.readFloat(input), index);
            } else if (data == 9) {
                putData(compound_nbt, NBTUtil.readList(input, null), index);
            } else if (data == 8) {
                putData(compound_nbt, NBTUtil.readString(input), index);
            } else if (data == 0) {
                index.back();
                now_len--;
                if (now_len <= finish_len) {
                    return compound_nbt;
                } else {
                    index.setLastIndex(-1);
                }
            } else if (data == 11) {
                putData(compound_nbt, NBTUtil.readIntList(input), index);
            } else if (data == 12) {
                putData(compound_nbt, NBTUtil.readLongList(input), index);
            } else if (data == 7) {
                putData(compound_nbt, NBTUtil.readByteList(input), index);
            } else {
                System.out.println("What is " + data);
                return compound_nbt;
            }
        }
    }
    private static int getLen(int num, NBT nbt, NBTIndex index){
        for(int i=1;i<num;i++){
            nbt=nbt.data.get(index.getNum(i));
        }
        return nbt.data.size();
    }
    private static void putData(NBT nbt, NBT nbt1, NBTIndex count) {
        for (int i = 1; i <= count.getLength(); i++) {
            int n = count.getNum(i);
            if (n < 0) {
                break;
            } else {
                nbt = nbt.data.get(n);
            }
        }
        nbt.data.add(nbt1);
    }

    @Override
    public NBT clone() {
        NBT result=new NBT(name,type,list_type);
        result.value=this.value;
        List<NBT> list=new ArrayList<>();
        for(NBT nbt:data){
            list.add(nbt.clone());
        }
        result.data=list;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o instanceof NBT nbt) {
            boolean is=true;
            if(data==null||nbt.data==null){
                if(data!=nbt.data){
                    is=false;
                }
            }else {
                if (data.size() == nbt.data.size()) {
                    for (int i = 0; i < data.size(); i++) {
                        if (!data.get(i).equals(nbt.data.get(i))) {
                            is = false;
                            break;
                        }
                    }
                } else {
                    is = false;
                }
            }
            return type == nbt.type && (type!=9||list_type == nbt.list_type) && Objects.equals(name, nbt.name) && is && Objects.equals(value, nbt.value);
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, data, value, type, list_type);
    }
}
