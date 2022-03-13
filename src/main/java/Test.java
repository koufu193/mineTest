import fields.NBT;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        try{
            System.out.println(NBT.readDataFromDataInputStream(new DataInputStream(new FileInputStream("D:/github//cliant/qwerty.txt"))));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
