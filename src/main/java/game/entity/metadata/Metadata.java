package game.entity.metadata;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Metadata {
    public static Metadata metadata=null;
    /**
     * 自分自身のデータを変える
     * @param input 読み込み先
     * @param index インデックス
     */
    public abstract void read(int index,@NotNull DataInputStream input) throws IOException;
    /**
     * データを書き込む
     * @param output 書き込み先
     */
    public abstract void write(@NotNull DataOutputStream output) throws IOException;
    protected final void writeIndex(int index,DataOutputStream output) throws IOException{
        output.writeByte(index);
    }
    public static void write(@NotNull Metadata metadata,@NotNull DataOutputStream output) throws IOException{
        metadata.write(output);
        output.writeByte(0xff);
    }
    public static void read(@NotNull Metadata metadata,@NotNull DataInputStream input) throws IOException{
        int index;
        while((index=input.readUnsignedByte())!=0xff){
            input.readByte();
            metadata.read(index,input);
        }
    }
    public static void setMetadata(Metadata metadata) {
        Metadata.metadata = metadata;
    }
}
