package util;

import Packet.PacketFieldType;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.function.BooleanSupplier;

public class Optional<T> extends PacketFieldType<T>{
    private BooleanSupplier write;
    private BooleanSupplier read;
    public Optional(@NotNull PacketFieldType<T> type,BooleanSupplier read,BooleanSupplier write) {
        super(type.name,type.min,type.max,type.getIOFunction());
        this.write=write;
        this.read=read;
    }
    public BooleanSupplier getRead() {
        return read;
    }
    public BooleanSupplier getWrite() {
        return write;
    }
    public void setRead(BooleanSupplier read) {
        this.read = read;
    }
    public void setWrite(BooleanSupplier write) {
        this.write = write;
    }

    @Override
    public void write(@NotNull T value, @NotNull DataOutputStream output) throws IOException {
        if(write.getAsBoolean()) {
            super.write(value, output);
        }
    }

    @Override
    public T read(@NotNull DataInputStream input) throws IOException {
        if(read.getAsBoolean()) {
            return super.read(input);
        }
        return null;
    }
}
