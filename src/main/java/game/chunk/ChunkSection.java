package game.chunk;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChunkSection {
    private short block_count;
    private PaletteContainer block_states;
    private PaletteContainer biomes;
    public ChunkSection(short block_count, @NotNull PaletteContainer block_states,PaletteContainer biomes){
        this.block_count=block_count;
        this.block_states=block_states;
        this.biomes=biomes;
    }

    public PaletteContainer getBiomes() {
        return biomes;
    }

    public PaletteContainer getBlockStates() {
        return block_states;
    }

    public short getBlockCount() {
        return block_count;
    }

    public void setBlockCount(short block_count) {
        this.block_count = block_count;
    }

    public void setBiomes(@NotNull PaletteContainer biomes) {
        this.biomes = biomes;
    }

    public void setBlockStates(@NotNull PaletteContainer block_states) {
        this.block_states = block_states;
    }
    public static void write(@NotNull ChunkSection section, @NotNull DataOutputStream output) throws IOException{
        output.writeShort(section.getBlockCount());
        PaletteContainer.write(section.getBlockStates(),output);
        PaletteContainer.write(section.getBiomes(),output);
    }
    public static ChunkSection read(@NotNull DataInputStream input) throws IOException{
        return new ChunkSection(input.readShort(),PaletteContainer.read(input,false),PaletteContainer.read(input,true));
    }
}
