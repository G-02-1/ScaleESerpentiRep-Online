import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<Integer, String> hm = new HashMap<>();
        hm.put(1, "aaaah");
        hm.put(2, "yeaaaah");

        ArrayList<Integer> al = new ArrayList<>(hm.keySet());
        System.out.println(al.getLast());
    }
}