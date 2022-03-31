package util;

import java.util.HashMap;

public class Data {
    public static int size=0;
    public static HashMap<String,Integer> map=new HashMap<>();
    public static int packet_max=0;
    public static void setSize(int value){
        size=value;
    }

    public static void setPacket_max(int packet_max) {
        Data.packet_max = packet_max;
    }
}
