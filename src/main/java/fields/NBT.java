package fields;

import fields.nbt.NBTIndex;
import fields.nbt.NBTUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NBT {
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
        StringBuilder builder=new StringBuilder("NBT:"+(name.isBlank()?"None":name)+"{\n");
        if(value==null) {
            for (NBT nbt : data) {
                builder.append("  ").append(nbt.toString(1)).append("\n");
            }
            builder.append("}");
        }else{
            builder.append("  ").append(value).append("\n}");
        }
        return builder.toString();
    }
    public String toString(int num){
        StringBuilder builder=new StringBuilder("NBT:"+(name.isBlank()?"None":name)+"{\n");
        StringBuilder kuhaku=new StringBuilder();
        for(int i=0;i<num;i++){
            kuhaku.append("  ");
        }
        String kuu=kuhaku.toString();
        if(value==null) {
            for (NBT nbt : data) {
                builder.append(kuu).append("  ").append(nbt.toString(num + 1)).append("\n");
            }
            builder.append(kuu).append("}");
        }else{
            builder.append(kuu).append("  ").append(value).append("\n").append(kuu).append("}");
        }
        return builder.toString();
    }
    public static NBT readDataFromDataInputStream(DataInputStream input) throws IOException {
        NBTIndex count = new NBTIndex(-1);
        int data;
        NBT nbt = new NBT("",10);
        while (true) {
            data = input.readByte();
            if (data == 10) {
                NBT nbt1 = new NBT(NBTUtil.getName(input), data);
                putData(nbt, nbt1, count);
                count.setLastIndex(getLen(count.getLength(), nbt, count) - 1);
                count.next(-1);
                System.out.println("start one data");
            } else if (data == 3) {
                putData(nbt, NBTUtil.readInt(input), count);
            } else if (data == 6) {
                putData(nbt, NBTUtil.readDouble(input), count);
            } else if (data == 1) {
                putData(nbt, NBTUtil.readByte(input), count);
            } else if (data == 4) {
                putData(nbt, NBTUtil.readLong(input), count);
            } else if (data == 2) {
                putData(nbt, NBTUtil.readShort(input), count);
            } else if (data == 5) {
                putData(nbt, NBTUtil.readFloat(input), count);
            } else if (data == 9) {
                putData(nbt, NBTUtil.readList(input), count);
            } else if (data == 8) {
                 putData(nbt, NBTUtil.readString(input), count);
            } else if (data == 0) {
                count.back();
                if(count.getLength()<2){
                    return nbt;
                }else{
                    count.setLastIndex(-1);
                }
            } else if (data == 11) {
                putData(nbt, NBTUtil.readIntList(input), count);
            } else if (data == 12) {
                putData(nbt, NBTUtil.readLongList(input), count);
            }else if(data==7){
                putData(nbt,NBTUtil.readByteList(input),count);
            } else {
                System.out.println("What is " + data);
                return nbt;
            }
        }
    }
    private static int getLen(int num, NBT nbt, NBTIndex index){
        for(int i=1;i<num;i++){
            nbt=nbt.data.get(index.getNum(i));
        }
        return nbt.data.size();
    }
    private static void putData(NBT nbt, NBT nbt1, NBTIndex count){
        try {
            for (int i = 1; i <= count.getLength(); i++) {
                int n = count.getNum(i);
                if (n < 0) {
                    break;
                } else {
                    nbt = nbt.data.get(n);
                }
            }
            nbt.data.add(nbt1);
        }catch (Exception e){
            System.out.println(nbt);
            System.out.println(count);
            throw e;
        }
    }
}
