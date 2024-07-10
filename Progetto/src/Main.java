import Board.Grid.GridBoard.Board;
import SupportingObjects.Cards.Card;
import SupportingObjects.Position;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        /*
        HashMap<Integer, String> hm = new HashMap<>();
        hm.put(1, "aaaah");
        hm.put(2, "yeaaaah");

        ArrayList<Integer> al = new ArrayList<>(hm.keySet());
        System.out.println(al.getLast());
        */

//        LinkedList<Collection> n = new LinkedList<>();
//        n.add(new LinkedList<>());
//        n.add(new ArrayList<>());
//        System.out.println(n.getFirst() instanceof LinkedList<?>);
//        System.out.println(n.getLast() instanceof LinkedList<?>);
//        System.out.println(n.getLast() instanceof ArrayList<?>);
//        System.out.println(n.getLast().getClass());

        //_______________________________________________SORT
//        ArrayList<Position> pos = new ArrayList<>();
//        pos.add(new Position(1,0));
//        pos.add(new Position(3,0));
//        pos.add(new Position(2,1));
//        pos.add(new Position(0,0));
//        pos.add(new Position(2,2));
//        pos.add(new Position(2,0));
//        pos.add(new Position(1,1));
//
//        System.out.println("PRE SORT");
//        for(Position p : pos) {
//            System.out.println(p.toString()+"\n");
//        }
//
//        System.out.println("\n\n\n\n\nPOST SORT");
//        Collections.sort(pos);
//
//        for(Position p : pos) {
//            System.out.println(p.toString()+"\n");
//        }
        //_______________________________________________


//        Object o = "Ciaooooo";
//        if(o instanceof Integer) {
//            System.out.println("INTEGER");
//        }
//        if(o instanceof String) {
//            System.out.println("STRING");
//        }

//        enum Type {
//            BENCH, INN, DICES, SPRING, PARKINGTERM;
//        }
//
//        System.out.println(Type.BENCH.toString());

//        int a = (int) Math.sqrt(48);
//        System.out.println(a);
//        TimeUnit.SECONDS.sleep(5);
//        System.out.println(a);

        Card card1 = new Card("BENCH");
        Card card2 = card1.clone();

        System.out.println("card 1: " + card1 + ",  card 2: " + card2);
    }
}