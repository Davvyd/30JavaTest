import com.sun.deploy.util.ArrayUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Admin on 31-Jan-18.
 */
public class SocialNetwork {
    private final String fileName = "data.json";

    private LinkedList<Person> group = new LinkedList<>();

    public static SocialNetwork instance=null;

    private SocialNetwork(){
        JSONParser parser=new JSONParser();
        try {
            JSONArray json = (JSONArray) parser.parse(new FileReader(fileName));
            for(Object o:json){
                //System.out.println(o.toString());
                group.add(Person.jsonToPerson((JSONObject)o));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static SocialNetwork getInstance(){
        if(instance!=null)
            return instance;

        instance = new SocialNetwork();
        return instance;
    }

    public static void print(){
        SocialNetwork.getInstance().group.forEach(System.out::println);
    }

    public Respond personInfo(int id){
        if(group.size()<id || id<0)
            return null;

        Person selected = group.get(id-1);
        int[] friends = selected.getFriends();
        ArrayList<Integer> friendsOfFriends = new ArrayList<>();
        Set<Integer> suggested = new TreeSet<>();

        //iterating friends
        for(int f:friends) {
            Person friend = group.get(f - 1);
            //iter through friends of friend
            //if is he already in list that means we have 2 links to that person so we add him to suggestion list
            for (int ff : friend.getFriends())
                if(friendsOfFriends.contains(ff))

                    suggested.add(ff);
                else
                    friendsOfFriends.add(ff);
        }

        //removing collision
        friendsOfFriends.remove(new Integer(id));
        suggested.remove(new Integer(id));

        for(int fr:friends) {
            friendsOfFriends.remove(new Integer(fr));
            suggested.remove(fr);
        }
        
        //Preparing data
        Collections.sort(friendsOfFriends);
        int[] fof = friendsOfFriends.stream().mapToInt(Integer::intValue).toArray();
        int[] sug = suggested.stream().mapToInt(Integer::intValue).toArray();

        return new Respond(id,friends,fof,sug);
    }
    public class Respond{
        public int person;
        public int[] friends;
        public int[] friendOfFriends;
        public int[] suggested;

        public Respond(int person, int[] friends, int[] friendOfFriends, int[] suggested) {
            this.person = person;
            this.friends = friends;
            this.friendOfFriends = friendOfFriends;
            this.suggested = suggested;
        }

        @Override
        public String toString() {
            return person+") friends "+ Arrays.toString(friends) +" "+" fof "+friendOfFriends+" suggested: "+suggested;
        }
    }

}
