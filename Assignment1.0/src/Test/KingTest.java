import Main.*;
import java.lang.Object;
import java.util.Deque;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class KingTest{
    @Test
    public void King() {
        Board test = new Board();
        King k = new King(1, new Position(2,3), test);
        assertEquals(k.getRow(), 2);
        assertEquals(k.getColor(),1);
        assertEquals(k.getCol(), 3);
    }

    @Test
    public void testJudgeMove() {
        Board test = new Board();
        Piece king1 = new King(1, new Position(2,2), test);
        Piece king2 = new King(-1, new Position(1,1), test);

        Piece king3 = new King(-1, new Position(1,2), test);

        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);
        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);

        test.printBoard();
        assertEquals(((King) king3).judgeMove(0,2), 0);
        assertEquals(((King) king3).judgeMove(2,2), 1);
        assertEquals(((King) king3).judgeMove(0,-1), -1);
        assertEquals(((King) king3).judgeMove(1,2), -2);
        assertEquals(((King) king3).judgeMove(1,1), -3);
        assertEquals(((King) king3).judgeMove(1,4), -4);
    }
    @Test
    public void testPotentialMove() {
        Board test = new Board();
        Piece king1 = new King(1, new Position(2,2), test);
        Piece king2 = new King(-1, new Position(1,1), test);

        Piece king3 = new King(-1, new Position(1,2), test);

        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);

        test.printBoard();
        Deque <Position> positionsQue = ((King) king1).potentialMoves();

        //for (Position pos : positionsQue){
        //    System.out.println(pos.getRow()+" "+pos.getCol());
        //}
    }
}