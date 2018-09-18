import Main.*;
import java.lang.Object;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RookTest{
    @Test
    public void Rook() {
        Rook k = new Rook(1, new Position(2,3), new Board());
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

        Piece rook = new Rook(-1, new Position(1,2), test);

        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);
        test.putPieceOnBoard(queen.getRow(), queen.getCol(), queen);
        test.putPieceOnBoard(queen1.getRow(), queen1.getCol(), queen1);
        test.putPieceOnBoard(rook.getRow(), rook.getCol(), rook);
        test.printBoard();


        // 2,2 = 1
        // 1,3 = 0
        // 1,5 = 0
        // -1,0 = -1
        // 1,2 = -2
        // 1,0 = -3
        // 0,0 = -4
        assertEquals(((Rook) rook).judgeMove(2,2), 1);
        assertEquals(((Rook) rook).judgeMove(1,3), 0);
        assertEquals(((Rook) rook).judgeMove(1,5), 0);
        assertEquals(((Rook) rook).judgeMove(-1,0), -1);
        assertEquals(((Rook) rook).judgeMove(1,2), -2);
        assertEquals(((Rook) rook).judgeMove(1,0), -3);
        assertEquals(((Rook) rook).judgeMove(0,0), -4);

    }

}