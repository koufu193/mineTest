package game.entity.metadata;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.IOException;

public class LivingEntityMetadata extends EntityMetadata{
    @Override
    public void read(int index, @NotNull DataInputStream input) throws IOException {
        super.read(index, input);
        // TODO: 2022/04/08 index書き込み 
    }
}
