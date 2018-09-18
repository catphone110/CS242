import Main.*;
import java.lang.Object;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * This test prints the given message on console.
 */


public class BishopTest{
    @Test
    public void Bishop() {
        Bishop k = new Bishop(1, new Position(2,3), new Board());
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

        Piece bishop = new Bishop(-1, new Position(1,2), test);
        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);
        test.putPieceOnBoard(bishop.getRow(), bishop.getCol(), bishop);
        test.printBoard();
        // 2,1 == 0
        // 2,3 == 1
        // 4,5 == -3
        assertEquals(((Bishop) bishop).judgeMove(2,1), 0);
        assertEquals(((Bishop) bishop).judgeMove(2,3), 1);
        assertEquals(((Bishop) bishop).judgeMove(-3,-1), -1);
        assertEquals(((Bishop) bishop).judgeMove(1,2), -2);
        assertEquals(((Bishop) bishop).judgeMove(4,5), -3);
        assertEquals(((Bishop) bishop).judgeMove(0,2), -4);


    }

}