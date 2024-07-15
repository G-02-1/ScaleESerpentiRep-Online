import Board.Grid.GridBoard.Board;
import Board.Grid.GridCells.Cell;
import Board.Grid.GridCells.StandardCell;
import SupportingObjects.Cards.Card;
import SupportingObjects.Position;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
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

//        String a = "PICKACARD - Carta - DICES";
//        String[] parts = a.split("-");
//        System.out.println(parts[0]);
//        System.out.println(parts[1].trim());
//        System.out.println(parts[2].trim());

//        ArrayList<Integer> i = new ArrayList<>();
//        for(int n = 0; n < 11; n++) {
//            i.add(n);
//        }
//        i.add(999);
//        System.out.println((int) i.stream().max(Integer::compareTo).orElseThrow());
//        //System.out.println(i.indexOf());

//        class prova implements Serializable {
//            int n;
//            String msg;
//
//            public prova(int n, String msg) {
//                this.n = n;
//                this.msg = msg;
//            }
//
//            public int getN() {
//                return n;
//            }
//
//            public String getMsg() {
//                return msg;
//            }
//        }
//
//        prova a = new prova(1, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy_HH-mm");
//        String formattedDateTime = currentDateTime.format(formatter);
//
//        // Stampa la stringa risultante
//        System.out.println(formattedDateTime);
//
//        // Specifica la cartella di destinazione (ad esempio, "/percorso/alla/cartella/")
//        String folderPath = "C:\\LaddersAndSnakeFolder\\saves\\" + formattedDateTime + ".dat";
//
//
//        // Specifica il percorso completo del file (incluso il nome "prova1.dat")
//        String percorsoFile = "C:\\LaddersAndSnakeFolder\\saves\\" + formattedDateTime + ".dat";
//
//
//
//        try {
//            // Crea un flusso di output per il file
//            FileOutputStream fos = new FileOutputStream(percorsoFile);
//
//            // Crea un flusso di output di oggetti per serializzare l'oggetto
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//
//            oos.writeObject(a); // Scrive l'oggetto su un file
//            fos.close();
//
//            System.out.println("Oggetto salvato correttamente in: " + percorsoFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            FileInputStream door = new FileInputStream(percorsoFile);
//            ObjectInputStream reader = new ObjectInputStream(door);
//            prova x = (prova) reader.readObject();
//            System.out.println(x.getN());
//            System.out.println(x.getMsg());
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }


        Board board = new Board(6, 6, true, true);
        board.putBoardComponent();
        System.out.println(board);
        for(Cell c : board.getAllCells()) {
            if(c instanceof StandardCell) {
                System.out.println(c.getNumber() + ": " + ((StandardCell) c).containsBoardComponentActive() + ", " + ((StandardCell) c).containsBoardComponentPassive());
            }
        }
    }
}