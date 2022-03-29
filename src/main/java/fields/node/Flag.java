package fields.node;

public enum Flag {
    ROOT((byte)0),LITERAL((byte)1),ARGUMENT((byte)2);
    private byte num;
    Flag(byte num){
        this.num=num;
    }
    public byte getNum() {
        return num;
    }
    public static Flag getFlag(int value){
        for(Flag flag:Flag.values()){
            if(flag.getNum()==value){
                return flag;
            }
        }
        return null;
    }
}
