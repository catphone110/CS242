import Main.*;
import java.lang.Object;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PawnTest{
    @Test
    public void Pawn() {
        Pawn k = new Pawn(1, new Position(2,3), new Board());
        assertEquals(k.getRow(), 2);
        assertEquals(k.getColor(),1);
        assertEquals(k.getCol(), 3);
    }
    @Test
    public void testJudgeMove() {
        Board test = new Board();
        Piece king1 = new King(-1, new Position(2,2), test);
        Piece king2 = new King(-1, new Position(1,1), test);
        Piece king3 = new King(-1, new Position(3,3), test);
        Piece king4 = new King(1, new Position(4,0), test);

        Piece pawn = new Pawn(1, new Position(1,3), test);
        Piece pawn_1 = new Pawn(-1, new Position(5,1), test);
        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);
        test.putPieceOnBoard(king4.getRow(), king4.getCol(), king4);
        test.putPieceOnBoard(pawn.getRow(), pawn.getCol(), pawn);
        test.putPieceOnBoard(pawn_1.getRow(), pawn_1.getCol(), pawn_1);

        test.printBoard();
        // pawn 2,2 = 1
        // pawn 2,3 = 0
        // pawn -3, 0 = -1
        // pawn 1,3 = -2
        // pawn 0,3 = -4
        // pawn 3,3 = -3
        assertEquals(((Pawn) pawn).judgeMove(2,2), 1);
        assertEquals(((Pawn) pawn).judgeMove(2,3), 0);
        assertEquals(((Pawn) pawn).judgeMove(-3,0), -1);
        assertEquals(((Pawn) pawn).judgeMove(1,3), -2);
        assertEquals(((Pawn) pawn).judgeMove(0,3), -4);
        assertEquals(((Pawn) pawn).judgeMove(3,3), -3);

        // pawn is moved 3,3 = -4
        ((Pawn) pawn).changeToMoved();
        assertEquals(((Pawn) pawn).judgeMove(3,3), -4);

        // pawn[-1] 6,1 = -4
        // pawn[-1] 4,0 = 1
        // pawn[-1] 4,1 = 0
        // pawn[-1] 3,1 = 0
        assertEquals(((Pawn) pawn_1).judgeMove(6,1), -4);
        assertEquals(((Pawn) pawn_1).judgeMove(4,0), 1);
        assertEquals(((Pawn) pawn_1).judgeMove(4,1), 0);
        assertEquals(((Pawn) pawn_1).judgeMove(3,1), 0);
    }
}