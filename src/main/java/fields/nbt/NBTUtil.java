package fields.nbt;

import fields.NBT;
import sun.misc.Unsafe;

import javax.print.attribute.standard.JobName;
import javax.script.ScriptEngine;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class NBTUtil {
    public static NBT readLong(DataInputStream input) throws IOException{
        return new NBT(getName(input),input.readLong(),4);
    }
    public static NBT readByte(DataInputStream input) throws IOException{
        return new NBT(getName(input),(Object)input.readByte(),1);
    }
    public static NBT readInt(DataInputStream input) throws IOException{
        return new NBT(getName(input),(Object)input.readInt(),3);
    }
    public static NBT readDouble(DataInputStream input) throws IOException{
        return new NBT(getName(input),input.readDouble(),6);
    }
    public static NBT readString(DataInputStream input) throws IOException{
        return new NBT(getName(input),getName(input),8);
    }
    public static NBT readShort(DataInputStream input) throws IOException{
        return new NBT(getName(input),(Object)input.readShort(),2);
    }
    public static NBT readFloat(DataInputStream input) throws IOException{
        return new NBT(getName(input),input.readFloat(),5);
    }
    private static NBT readCompound(DataInputStream input,int len) throws IOException{
        NBT nbt=new NBT("",10);
        return NBT.readDataFromDataInputStream(input,nbt,new NBTIndex(-1),len);
    }
    public static NBT readList(DataInputStream input,String name) throws IOException{
        NBT list=new NBT(name==null?getName(input):name,9);
        int TypeID=input.readByte();
        list.list_type=TypeID;
        int size=input.readInt();
        if(TypeID==0&&0<size){
            throw new RuntimeException("TypeIDは0なのにsizeは1以上です");
        }
        if(0<size) {
            for (int i = 0; i < size; i++) {
                NBT nbt=(TypeID!=10?readNBT(TypeID, input,false):readCompound(input,0));
                if(nbt==null){
                    System.out.println("nbt is null");
                }else {
                    nbt.name+=" "+i;
                    list.data.add(nbt);
                }
            }
        }else{
            System.out.println("size is "+size+" "+list.name);
        }
        return list;
    }
    public static NBT readIntList(DataInputStream input) throws IOException{
        NBT list=new NBT(getName(input),11);
        int size=input.readInt();
        if(0<=size){
            for (int i = 0; i < size; i++) {
                list.data.add(new NBT("",(Object)input.readInt(),3));
            }
        }else{
            System.out.println("size is "+size+" "+list.name);
        }
        return list;
    }
    public static NBT readByteList(DataInputStream input) throws IOException{
        NBT list=new NBT(getName(input),7);
        int size=input.readInt();
        if(0<=size){
            for (int i = 0; i < size; i++) {
                list.data.add(new NBT("",(Object) input.readByte(),1));
            }
        }else{
            System.out.println("size is "+size+" "+list.name);
        }
        return list;
    }
    public static NBT readLongList(DataInputStream input) throws IOException{
        NBT list=new NBT(getName(input),12);
        int size=input.readInt();
        if(0<=size){
            for (int i = 0; i < size; i++) {
                list.data.add(new NBT("",input.readLong(),4));
            }
        }else{
            System.out.println("size is "+size+" "+list.name);
        }
        return list;
    }
    public static NBT readNBT(int TypeID,DataInputStream input,boolean needName) throws IOException{
        String name="";
        if(TypeID<0){
            TypeID=input.readByte();
            System.out.println("read "+TypeID);
        }
        if(TypeID==0) {
            return null;
        }
        if(needName){
            name=getName(input);
        }
        if(TypeID==8){
            return new NBT(name,getName(input),TypeID);
        }else if(TypeID==1){
            return new NBT(name,(Object)input.readByte(),TypeID);
        }else if(TypeID==2){
            return new NBT(name,(Object)input.readShort(),TypeID);
        }else if(TypeID==3){
            return new NBT(name,(Object)input.readInt(),TypeID);
        }else if(TypeID==4){
            return new NBT(name,input.readLong(),TypeID);
        }else if(TypeID==5){
            return new NBT(name,input.readFloat(),TypeID);
        }else if(TypeID==6) {
            return new NBT(name, input.readDouble(),TypeID);
        }else if(TypeID==11) {
            NBT list = new NBT(name,TypeID);
            int size = input.readInt();
            if (0 <= size) {
                for (int i = 0; i < size; i++) {
                    list.data.add(new NBT(name,(Object)input.readInt(),3));
                }
            } else {
                System.out.println("size is " + size + " " + list.name);
            }
            return list;
        }else if(TypeID==12) {
            NBT list = new NBT("",TypeID);
            int size = input.readInt();
            if (0 <= size) {
                for (int i = 0; i < size; i++) {
                    list.data.add(new NBT(name, input.readLong(),4));
                }
            } else {
                System.out.println("size is " + size + " " + list.name);
            }
            return list;
        }else if(TypeID==7) {
            NBT list = new NBT(name, TypeID);
            int size = input.readInt();
            if (0 <= size) {
                for (int i = 0; i < size; i++) {
                    list.data.add(new NBT(name, (Object) input.readByte(), 1));
                }
            } else {
                System.out.println("size is " + size + " " + list.name);
            }
            return list;
        }else if(TypeID==10) {
            return readCompound(input,1);
        }else if(TypeID==9){
            return readList(input,name);
        }else{
            System.out.println("What is "+TypeID);
            return null;
        }
    }
    public static String getName(DataInputStream input) throws IOException {
        int s = input.readShort();
        return new String(input.readNBytes(s));
    }
}
