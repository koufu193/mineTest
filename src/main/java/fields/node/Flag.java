package fields.node;

public enum Flag {
    ROOT(0),LITERAL(1),ARGUMENT(2);
    int num;
    Flag(int num){
        this.num=num;
    }
    public int getNum() {
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
