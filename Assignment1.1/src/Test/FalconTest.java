import Main.*;
import java.lang.Object;
import java.util.Deque;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * This test prints the given message on console.
 */


public class FalconTest{
    @Test
    public void Bishop() {
        Falcon k = new Falcon(1, new Position(2,3), new Board());
        assertEquals(k.getRow(), 2);
        assertEquals(k.getColor(),1);
        assertEquals(k.getCol(), 3);
    }
    @Test
    public void testJudgeMove() {
        Board test = new Board();
        test.printBoard();
        Piece king1 = new King(1, new Position(2,2), test);
        Piece king2 = new King(-1, new Position(1,1), test);
        Piece king3 = new King(1, new Position(2,3), test);

        Piece f = new Falcon(-1, new Position(1,2), test);
        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);
        test.putPieceOnBoard(f.getRow(), f.getCol(), f);
        test.printBoard();

        assertEquals(((Falcon) f).judgeMove(0,1), 0);
        assertEquals(((Falcon) f).judgeMove(2,2), -4);
        assertEquals(((Falcon) f).judgeMove(-3,-1), -1);
        assertEquals(((Falcon) f).judgeMove(1,2), -2);
        assertEquals(((Falcon) f).judgeMove(4,5), -3);
        assertEquals(((Falcon) f).judgeMove(0,2), 0);

    }
    @Test
    public void testPotentialMove() {
        Board test = new Board();
        Piece king1 = new King(1, new Position(2,2), test);
        Piece king2 = new King(-1, new Position(1,1), test);
        Piece king3 = new King(1, new Position(2,3), test);

        Piece f = new Falcon(-1, new Position(1,2), test);
        Piece f1 = new Falcon(-1, new Position(3,4), test);
        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);
        test.putPieceOnBoard(f.getRow(), f.getCol(), f);
        test.putPieceOnBoard(f1.getRow(), f1.getCol(), f1);
        test.printBoard();

        Deque<Position> positionsQue = ((Falcon) f).potentialMoves();
        Deque<Position> positionsQue1 = ((Falcon) f1).potentialMoves();

        for (Position pos : positionsQue){
            System.out.println(pos.getRow()+" "+pos.getCol());
        }
        System.out.println(" next ");
        for (Position pos : positionsQue1){
            System.out.println(pos.getRow()+" "+pos.getCol());
        }
    }

}