package game.entity.metadata;

import fields.NBT;
import org.jetbrains.annotations.NotNull;
import util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerMetadata extends LivingEntityMetadata{
    protected float additionalHearts=0;//index=15
    protected int score=0;//index=16
    protected SkinParts skinParts=new SkinParts((byte)0);//index=17
    protected byte mainHand=1;//index=18
    protected NBT LeftShoulderEntityData=NBT.NONE;//index=19
    protected NBT RightShoulderEntityData=NBT.NONE;//index=20

    public class SkinParts{
        protected byte value;
        public SkinParts(byte value){
            this.value=value;
        }

        public byte getValue() {
            return value;
        }

        public void setValue(byte value) {
            this.value = value;
        }
        public boolean isCapeEnable(){
            return bitmask(value,0x01);
        }
        public boolean isJacketEnable(){
            return bitmask(value,0x02);
        }
        public boolean isLeftSleeveEnable(){
            return bitmask(value,0x04);
        }
        public boolean isRightSleeveEnable(){
            return bitmask(value,0x08);
        }
        public boolean isLeftPantsLegEnable(){
            return bitmask(value,0x10);
        }
        public boolean isRightPantsLegEnable(){
            return bitmask(value,0x20);
        }
        public boolean isHatEnable(){
            return bitmask(value,0x40);
        }

        @Override
        public String toString() {
            return "SkinParts{" +
                    "value=" + value +
                    '}';
        }
    }

    @Override
    public void read(int index, @NotNull DataInputStream input) throws IOException {
        switch (index){
            case 15:
                additionalHearts=input.readFloat();
                break;
            case 16:
                score= Util.readVarInt(input);
                break;
            case 17:
                skinParts=new SkinParts(input.readByte());
                break;
            case 18:
                mainHand=input.readByte();
                break;
            case 19:
                LeftShoulderEntityData=NBT.readDataFromDataInputStream(input);
                break;
            case 20:
                RightShoulderEntityData=NBT.readDataFromDataInputStream(input);
                break;
            default:
                super.read(index,input);
                break;
        }
    }

    @Override
    public void write(@NotNull DataOutputStream output) throws IOException {
        super.write(output);
        writeIndex(15,output);
        output.writeFloat(additionalHearts);
        writeIndex(16,output);
        Util.writeVarInt(score,output);
        writeIndex(17,output);
        output.writeByte(skinParts.getValue());
        writeIndex(18,output);
        output.writeByte(mainHand);
        writeIndex(19,output);
        LeftShoulderEntityData.write(output);
        writeIndex(20,output);
        RightShoulderEntityData.write(output);
    }

    public byte getMainHand() {
        return mainHand;
    }

    public @NotNull NBT getRightShoulderEntityData() {
        return RightShoulderEntityData;
    }

    public @NotNull NBT getLeftShoulderEntityData() {
        return LeftShoulderEntityData;
    }

    public int getScore() {
        return score;
    }

    public float getAdditionalHearts() {
        return additionalHearts;
    }

    public @NotNull SkinParts getSkinParts() {
        return skinParts;
    }

    @Override
    public String toString() {
        return "PlayerMetadata{" +
                "additionalHearts=" + additionalHearts +
                ", score=" + score +
                ", skinParts=" + skinParts +
                ", mainHand=" + mainHand +
                ", LeftShoulderEntityData=" + LeftShoulderEntityData +
                ", RightShoulderEntityData=" + RightShoulderEntityData +
                "} " + super.toString();
    }
}
