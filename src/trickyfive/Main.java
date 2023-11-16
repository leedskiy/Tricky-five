package trickyfive;
import trickyfive.model.Board;

public class Main {
    public static void main(String[] args) {
        Board b = new Board(6);

        b.placePlayerInCell(0,0); //
        b.placePlayerInCell(0,2);
        b.placePlayerInCell(0,3); //
        b.placePlayerInCell(2,1);
        b.placePlayerInCell(2,2); //
        b.placePlayerInCell(5,5);
        b.placePlayerInCell(3,2); //
        b.placePlayerInCell(4,2);
        b.placePlayerInCell(1,5); //

        b.placePlayerInCell(4,4);
        b.placePlayerInCell(5,0); //
        b.placePlayerInCell(1,4);
        b.placePlayerInCell(5,1); //
        b.placePlayerInCell(4,0);
        b.placePlayerInCell(5,2); //
        b.placePlayerInCell(3,1); //
        b.placePlayerInCell(5,3); //
        b.placePlayerInCell(1,1); //
        b.placePlayerInCell(5,4); //
        System.out.println(b);
    }
}