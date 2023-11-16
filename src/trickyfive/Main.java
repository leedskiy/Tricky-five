package trickyfive;
import trickyfive.model.Board;

public class Main {
    public static void main(String[] args) {
        Board b = new Board(12);

        b.placePlayerInCell(2,3); // 1
        b.placePlayerInCell(5,9);
        b.placePlayerInCell(2,4); // 2
        b.placePlayerInCell(5,7);
        b.placePlayerInCell(8,4); //
        b.placePlayerInCell(11,9);
        b.placePlayerInCell(2,10); //
        b.placePlayerInCell(3,11);
        b.placePlayerInCell(4,3); //
        b.placePlayerInCell(4,7);
        b.placePlayerInCell(6,9); //
        b.placePlayerInCell(3,8);
        b.placePlayerInCell(3,2); //
        b.placePlayerInCell(3,10);
        b.placePlayerInCell(4,9); //
        b.placePlayerInCell(4,8);
        b.placePlayerInCell(1,2); //
        b.placePlayerInCell(3,3);
        b.placePlayerInCell(2,5); // 3
        b.placePlayerInCell(4,5);
//        System.out.println(b);
        b.placePlayerInCell(2,6); // 4
        b.placePlayerInCell(10,9);
//        System.out.println(b);
        b.placePlayerInCell(2,7); // 5
        System.out.println(b);
    }
}