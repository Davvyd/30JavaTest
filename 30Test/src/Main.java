import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Admin on 31-Jan-18.
 */
public class Main {
    public static void main(String[] args){
        SocialNetwork.print();
        for(int i=1;i<22;i++)
           System.out.println( SocialNetwork.getInstance().personInfo(i));

    }
}
