package fields;

import fields.nbt.NBTIndex;
import fields.nbt.NBTUtil;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * NBTファイルを操作するクラス
 */
public class NBT implements Cloneable{
    public static final NBT NONE=new NBT("",10){
        @Override
        public void setName(String name) {
        }
        @Override
        public void setData(List<NBT> data) {
        }
        @Override
        public void setList_type(int list_type) {
        }
        @Override
        public void setValue(Object value) {
        }
        @Override
        public NBT add(NBT nbt) {
            return this;
        }
    };
    private String name;
    private List<NBT> data = new ArrayList<>();
    private Object value=null;
    private final int type;
    private int list_type=0;

    /**
     * @param name 名前
     * @param type このNBTのタイプ
     */
    public NBT(String name,int type) {
        this.name = name;
        value = null;
        this.type = type;
    }

    /**
     * リストの中身の型のタイプを設定
     * @param list_type 設定するタイプ
     */
    public void setList_type(int list_type) {
        this.list_type = list_type;
    }

    /**
     * 名前を取得する
     * @return 名前
     */
    public String getName() {
        return name;
    }

    /**
     * タイプを取得
     * @return タイプ
     */
    public int getType() {
        return type;
    }

    /**
     * 中のリストを設定
     * @param data 設定するリスト
     */
    public void setData(List<NBT> data) {
        this.data = data;
    }

    /**
     * リストを取得
     * @return リスト
     */
    public List<NBT> getData() {
        return data;
    }

    /**
     * リストの中身の型のタイプを取得
     * @return リストの中身の型のタイプ
     */
    public int getList_type() {
        return list_type;
    }

    /**
     * 名前を設定する
     * @param name 設定する名前
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param name 名前
     * @param value 値
     * @param type このNBTのタイプ
     */
    public NBT(String name, Object value,int type) {
        this.name = name;
        this.value = value;
        this.type=type;
    }

    /**
     * @param name 名前
     * @param type このNBTのタイプ(TAG_List)
     * @param list_type このNBTのリストのタイプ
     */
    public NBT(String name,int type,int list_type) {
        this.name = name;
        this.type=type;
        this.list_type=list_type;
    }

    /**
     * valueを取得する
     * @return このNBTの値
     */
    public Object getValue(){
        return value;
    }

    /**
     * valueを設定する
     * @param value 設定する値
     * @exception IllegalStateException リストの中身が1以上の時
     */
    public void setValue(Object value){
        if(0<data.size()){
            throw new IllegalStateException("リストが既に設定されています");
        }
        this.value=value;
    }

    /**
     * 指定したパスのNBTを取得する
     * パスの形式は"test.example"のように
     * 「.」で分ける
     * @param path 取得したいNBTのパス
     * @return 結果
     */
    public @NotNull List<NBT> getNBT(String path){
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

    /**
     * データにNBTを追加する
     * @param nbt 増やすNBT
     * @return 自分自身
     */
    public NBT add(NBT nbt){
        this.data.add(nbt);
        return this;
    }

    /**
     * 指定した場所にのNBTを書き込む
     * @param output 書き込み先
     * @throws IOException
     */
    public void write(@NotNull DataOutputStream output) throws IOException{
        write(output,true,true);
    }

    /**
     * 指定した場所に指定した状態でこのNBTを書き込む
     * @param output 書き込み先
     * @param needTellType 自分のタイプを書き込むか
     * @param needTellName 自分の名前を書き込むか
     * @throws IOException
     */
    void write(@NotNull DataOutputStream output,boolean needTellType,boolean needTellName) throws IOException{
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

    private String toString(int num) {
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

    /**
     * 非圧縮のデータからNBTを読み込む
     * @param input 読み込み先
     * @return 結果
     * @throws IOException
     */
    public static NBT readDataFromDataInputStream(DataInputStream input) throws IOException{
        return readDataFromDataInputStream(input,new NBT("",10),new NBTIndex(-1),1);
    }

    /**
     * 指定した状態から非圧縮のデータをNBTにする
     * @param input 読み込み先
     * @param compound_nbt 元のデータ
     * @param index 今のインデックス
     * @param finish_len 今の長さ
     * @return 結果
     * @throws IOException
     */
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
