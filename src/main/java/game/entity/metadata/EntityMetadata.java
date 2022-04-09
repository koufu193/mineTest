package game.entity.metadata;

import fields.Chat;
import fields.Pose;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EntityMetadata extends Metadata {

    public static int ON_FIRE=0x01;
    public static int CROUCHING=0x02;
    public static int SPRINTING=0x08;
    public static int SWIMMING=0x10;
    public static int INVISIBLE=0x20;
    public static int GLOWING_EFFECT=0x40;
    public static int FLYING_WITH_AN_ELYTRA=0x80;

    protected byte status=0;//index=0
    protected int air_ticks=300;//index=1
    protected Chat custom_name=null;//index=2
    protected boolean isCustom_name_visible=false;//index=3
    protected boolean isSilent=false;//index=4
    protected boolean hasNo_gravity=false;//index=5
    protected int pose=Pose.STANDING;//index=6
    protected int frozen_ticks=0;//index=7
    @Override
    public void read(int index,@NotNull DataInputStream input) throws IOException{
        switch (index){
            case 0:
                status=input.readByte();
                break;
            case 1:
                air_ticks= Util.readVarInt(input);
                break;
            case 2:
                custom_name=(input.readBoolean()?Chat.read(input):null);
                break;
            case 3:
                isCustom_name_visible=input.readBoolean();
                break;
            case 4:
                isSilent=input.readBoolean();
                break;
            case 5:
                hasNo_gravity=input.readBoolean();
                break;
            case 6:
                pose=Util.readVarInt(input);
                break;
            case 7:
                frozen_ticks=Util.readVarInt(input);
                break;
        }
    }
    public boolean isOnFire(){
        return bitmask(status,EntityMetadata.ON_FIRE);
    }
    public boolean isCrouching(){
        return bitmask(status,EntityMetadata.CROUCHING);
    }
    public boolean isSprinting(){
        return bitmask(status,EntityMetadata.SPRINTING);
    }
    public boolean isSwimming(){
        return bitmask(status,EntityMetadata.SWIMMING);
    }
    public boolean isInvisible(){
        return bitmask(status,EntityMetadata.INVISIBLE);
    }
    public boolean hasGlowingEffect(){
        return bitmask(status,EntityMetadata.GLOWING_EFFECT);
    }
    public boolean isFlyingWithAnElytra(){
        return bitmask(status,EntityMetadata.FLYING_WITH_AN_ELYTRA);
    }
    public boolean isSilent() {
        return isSilent;
    }
    public boolean isCustomNameVisible() {
        return isCustom_name_visible;
    }
    public boolean hasNoGravity() {
        return hasNo_gravity;
    }
    public @Nullable Chat getCustomName() {
        return custom_name;
    }
    public int getAirTicks() {
        return air_ticks;
    }
    public int getFrozenTicks() {
        return frozen_ticks;
    }
    public int getPose() {
        return pose;
    }
    protected final boolean bitmask(int value, int bitmask){
        return (value&bitmask)==bitmask;
    }
    @Override
    public void write(@NotNull DataOutputStream output) throws IOException{
        writeIndex(0,output);
        output.writeByte(status);
        writeIndex(1,output);
        Util.writeVarInt(getAirTicks(),output);
        writeIndex(2,output);
        output.writeBoolean(getCustomName()!=null);
        if(getCustomName()!=null){
            Util.writeString(getCustomName().toString(), StandardCharsets.UTF_8,output);
        }
        writeIndex(3,output);
        output.writeBoolean(isCustomNameVisible());
        writeIndex(4,output);
        output.writeBoolean(isSilent());
        writeIndex(5,output);
        output.writeBoolean(hasNoGravity());
        writeIndex(6,output);
        Util.writeVarInt(getPose(),output);
        writeIndex(7,output);
        Util.writeVarInt(getFrozenTicks(),output);
    }

    @Override
    public String toString() {
        return "EntityMetadata{" +
                "status=" + status +
                ", air_ticks=" + air_ticks +
                ", custom_name=" + custom_name +
                ", isCustom_name_visible=" + isCustom_name_visible +
                ", isSilent=" + isSilent +
                ", hasNo_gravity=" + hasNo_gravity +
                ", pose=" + pose +
                ", frozen_ticks=" + frozen_ticks +
                '}';
    }
}
