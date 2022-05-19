package game.chunk;

import org.jetbrains.annotations.NotNull;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PaletteContainer {
    private int bits_per_entry;
    private Palette palette;
    private long[] array;
    public PaletteContainer(int bits_per_entry, @NotNull Palette palette,long[] array){
        this.bits_per_entry=bits_per_entry;
        this.palette=palette;
        this.array=array;

    }

    public long[] getArray() {
        return array;
    }

    public int getBits_per_entry() {
        return bits_per_entry;
    }

    public Palette getPalette() {
        return palette;
    }

    public void setPalette(@NotNull Palette palette) {
        this.palette = palette;
    }

    public void setArray(long[] array) {
        this.array = array;
    }

    public void setBits_per_entry(int bits_per_entry) {
        this.bits_per_entry = bits_per_entry;
    }
    public static void write(@NotNull PaletteContainer container, @NotNull DataOutputStream output) throws IOException {
        output.writeByte(container.getBits_per_entry());
        container.getPalette().write(output);
        Util.writeVarInt(container.getArray().length,output);
        for(long l:container.getArray()){
            output.writeLong(l);
        }
    }
    public static PaletteContainer read(@NotNull DataInputStream input,boolean isBiomes) throws IOException{
        int bits_per_entry=input.readUnsignedByte();
        Palette palette;
        long[] array;
        if(bits_per_entry<=0){
            palette=new SingleValuePalette(0);
            palette.read(input);
        }else if(isBiomes?bits_per_entry<=3:bits_per_entry<=8){
            palette=new IndirectPalette(null);
            palette.read(input);
        }else{
            palette=new DirectPalette();
        }
        if(0<bits_per_entry){
            array=new long[Util.readVarInt(input)];
            for(int i=0;i<array.length;i++){
                array[i]=input.readLong();
            }
        }else{
            array=new long[0];
        }
        return new PaletteContainer(bits_per_entry,palette,array);
    }
}
