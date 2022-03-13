package fields.nbt;

public class NBTIndex {
    public int num;
    public NBTIndex index=null;
    public NBTIndex(int num,NBTIndex index){
        this.num=num;
        this.index=index;
    }
    public NBTIndex(int num){
        this.num=num;
    }
    public boolean back(){
        return hasNext();
    }
    public NBTIndex next(int num){
        if(index==null){
            return index=new NBTIndex(num);
        }
        return index.next(num);
    }
    private boolean hasNext(){
        if(index==null){
            return false;
        }
        if(!index.hasNext()){
            index=null;
        }
        return true;
    }

    @Override
    public String toString() {
        return "NBTIndex{" +
                "num=" + num +
                ", index=" + index +
                '}';
    }

    public int getLength(){
        if(index==null){
            return 1;
        }
        return index.getLength()+1;
    }
    public void setLastIndex(int num){
        if(index==null){
            this.num=num;
        }else{
            index.setLastIndex(num);
        }
    }
    public int getLastIndex(){
        return index==null?this.num:index.getLastIndex();
    }
    public int getNum(int num){
        if(1<num&&index==null){
            throw new IllegalArgumentException("numが2以上だけどindexはnullです");
        }
        return num<=1?this.num:index.getNum(num-1);
    }
}
