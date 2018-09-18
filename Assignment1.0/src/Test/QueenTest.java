import Main.*;
import java.lang.Object;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class QueenTest{
    @Test
    public void Queen() {
        Queen k = new Queen(1, new Position(2,3), new Board());
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

        Piece queen = new Queen(-1, new Position(3,2), test);
        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);
        test.putPieceOnBoard(queen.getRow(), queen.getCol(), queen);
        test.printBoard();

        // 4,3 == 0
        // 2,2 == 1
        // 1,4 == -3
        // 3,2 == -2
        // 5,1 == -4
        assertEquals(((Queen) queen).judgeMove(4,3), 0);
        assertEquals(((Queen) queen).judgeMove(2,2), 1);
        assertEquals(((Queen) queen).judgeMove(0,-1), -1);
        assertEquals(((Queen) queen).judgeMove(3,2), -2);
        assertEquals(((Queen) queen).judgeMove(1,4), -3);
        assertEquals(((Queen) queen).judgeMove(5,1), -4);

        // 1,4 == -3
        // 5,2 == 0
        // 5,4 == 0
        // 1,0 == 0
        // 0,2 == -3
        // 2,3 == 1
        assertEquals(((Queen) queen).judgeMove(1,4), -3);
        assertEquals(((Queen) queen).judgeMove(5,2), 0);
        assertEquals(((Queen) queen).judgeMove(5,4), 0);
        assertEquals(((Queen) queen).judgeMove(1,0), 0);
        assertEquals(((Queen) queen).judgeMove(0,2), -3);
        assertEquals(((Queen) queen).judgeMove(2,3), 1);

        Piece king4 = new King(1, new Position(4,1), test);
        test.putPieceOnBoard(king4.getRow(), king4.getCol(), king4);
        test.printBoard();
        // 4，1 == 1
        // 5, 0 == -3
        // 5, 5 == -4
        assertEquals(((Queen) queen).judgeMove(4,1), 1);
        assertEquals(((Queen) queen).judgeMove(5,0), -3);
        assertEquals(((Queen) queen).judgeMove(5,5), -4);
    }

}