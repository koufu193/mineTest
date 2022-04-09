package game.entity.metadata;

import fields.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LivingEntityMetadata extends EntityMetadata{
    protected byte handState=0;//index=8
    protected float health=0;//index=9
    protected int potionEffectColor=0;//index=10
    protected boolean isPotionEffectAmbient=false;//index=11
    protected int arrows=0;//index=12
    protected int beeStingers=0;//index=13
    protected Position bedLocation=null;//index14
    @Override
    public void read(int index, @NotNull DataInputStream input) throws IOException {
        switch (index){
            case 8:
                handState=input.readByte();
                break;
            case 9:
                health=input.readFloat();
                break;
            case 10:
                potionEffectColor= Util.readVarInt(input);
                break;
            case 11:
                isPotionEffectAmbient=input.readBoolean();
                break;
            case 12:
                arrows=Util.readVarInt(input);
                break;
            case 13:
                beeStingers=Util.readVarInt(input);
                break;
            case 14:
                bedLocation=(input.readBoolean()?Position.read(input):null);
                break;
            default:
                super.read(index, input);
                break;
        }
    }

    @Override
    public void write(@NotNull DataOutputStream output) throws IOException {
        super.write(output);
        writeIndex(8,output);
        output.writeByte(handState);
        writeIndex(9,output);
        output.writeFloat(health);
        writeIndex(10,output);
        Util.writeVarInt(potionEffectColor,output);
        writeIndex(11,output);
        output.writeBoolean(isPotionEffectAmbient);
        writeIndex(12,output);
        Util.writeVarInt(arrows,output);
        writeIndex(13,output);
        Util.writeVarInt(beeStingers,output);
        writeIndex(14,output);
        output.writeBoolean(bedLocation!=null);
        if(bedLocation!=null){
            Position.write(bedLocation,output);
        }
    }

    public int getBeeStingers() {
        return beeStingers;
    }

    public @Nullable Position getBedLocation() {
        return bedLocation;
    }

    public int getArrows() {
        return arrows;
    }

    public int getPotionEffectColor() {
        return potionEffectColor;
    }

    public float getHealth() {
        return health;
    }

    public boolean isPotionEffectAmbient() {
        return isPotionEffectAmbient;
    }
    public boolean isHandActive(){
        return bitmask(handState,0x01);
    }

    /**
     * @return 0=main hand,1=offhand
     */
    public int getActiveHand(){
        return Util.bitmask(handState,0x02);
    }
    public boolean isRiptideSpinAttack(){
        return bitmask(handState,0x04);
    }

    @Override
    public String toString() {
        return "LivingEntityMetadata{" +
                "handState=" + handState +
                ", health=" + health +
                ", potionEffectColor=" + potionEffectColor +
                ", isPotionEffectAmbient=" + isPotionEffectAmbient +
                ", arrows=" + arrows +
                ", beeStingers=" + beeStingers +
                ", bedLocation=" + bedLocation +
                "} " + super.toString();
    }
}
