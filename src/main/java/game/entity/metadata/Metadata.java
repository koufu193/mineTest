package game.entity.metadata;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Metadata {
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
    public static void write(@NotNull EntityMetadata metadata,@NotNull DataOutputStream output) throws IOException{
        metadata.write(output);
        output.writeByte(0xff);
    }
    public static void read(@NotNull EntityMetadata metadata,@NotNull DataInputStream input) throws IOException{
        int index;
        while((index=input.readUnsignedByte())!=0xff){
            metadata.read(index,input);
        }
    }
}
