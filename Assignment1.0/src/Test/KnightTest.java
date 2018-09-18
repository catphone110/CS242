import Main.*;
import java.lang.Object;
import java.util.Deque;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class KnightTest{
    @Test
    public void Knight() {
        Knight k = new Knight(1, new Position(2,3), new Board());
        assertEquals(k.getRow(), 2);
        assertEquals(k.getColor(),1);
        assertEquals(k.getCol(), 3);
    }
    @Test
    public void testJudgeMove() {
        Board test = new Board();
        Piece king1 = new King(1, new Position(2,2), test);
        Piece king2 = new King(-1, new Position(1,1), test);
        Piece king3 = new King(1, new Position(2,3), test);
        Piece queen = new Queen(-1, new Position(3,3), test);
        Piece queen1 = new Queen(1, new Position(3,1), test);

        Piece knight = new Knight(-1, new Position(1,2), test);

        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);
        test.putPieceOnBoard(queen.getRow(), queen.getCol(), queen);
        test.putPieceOnBoard(queen1.getRow(), queen1.getCol(), queen1);
        test.putPieceOnBoard(knight.getRow(), knight.getCol(), knight);
        test.printBoard();

        // 2,0 == 0
        // 3,1 == 1
        // 3,3 == -3
        // 2,3 == -4
        assertEquals(((Knight) knight).judgeMove(2,0), 0);
        assertEquals(((Knight) knight).judgeMove(3,1), 1);
        assertEquals(((Knight) knight).judgeMove(-3,-1), -1);
        assertEquals(((Knight) knight).judgeMove(1,2), -2);
        assertEquals(((Knight) knight).judgeMove(3,3), -3);
        assertEquals(((Knight) knight).judgeMove(2,3), -4);

    }
    @Test
    public void testPotentialMove() {
        Board test = new Board();
        Piece king1 = new King(1, new Position(2,2), test);
        Piece king2 = new King(-1, new Position(1,2), test);

        Piece knight = new Knight(-1, new Position(3,1), test);

        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(knight.getRow(), knight.getCol(), knight);

        test.printBoard();
        Deque<Position> positionsQue = ((Knight) knight).potentialMoves();

        //for (Position pos : positionsQue){
        //    System.out.println(pos.getRow()+" "+pos.getCol());
        //}
    }

}