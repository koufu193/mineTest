package fields;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Position {
    protected int x;
    protected int y;
    protected int z;
    public Position(int x,int y,int z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public static Position read(@NotNull DataInputStream input) throws IOException{
        long l=input.readLong();
        return new Position((int) (l>>38),(int)(l&0xFF),(int)((l>>12)&0x3FFFFFF));
    }
    public static void write(@NotNull Position pos,DataOutputStream output) throws IOException{
        output.writeLong(((long) (pos.getX() & 0x3FFFFFF) <<38|((long) (pos.getZ() & 0x3FFFFFF) <<12)|(pos.getY()&0xFFF)));
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getZ() {
        return z;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setZ(int z) {
        this.z = z;
    }
}
