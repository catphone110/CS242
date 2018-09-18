import Main.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    public void testStartingBoard() {
    }
    @Test
    public void testPutPieceOnBoard() {
    }

    @Test
    public void testUndoMovePiece() {
        Board board  = new Board();
        Piece p1 = new Pawn(1, new Position(3,4), board);
        Piece p2 = new Rook(1, new Position(2,2), board);
        board.putPieceOnBoard(p1.getRow(), p1.getCol(), p1);
        board.putPieceOnBoard(p2.getRow(), p2.getCol(), p2);
        board.printBoard();
        Position lastPosition = new Position(p1.getRow(), p1.getCol());
        Piece oldPiece = board.movePiece(p1, new Position(p2.getRow(),p2.getCol()));
        board.printBoard();
        board.undoMovePiece(p1,oldPiece, lastPosition);
        board.printBoard();
        assertEquals(p2.getRow(), 2);
        assertEquals(p2.getCol(), 2);
        assertEquals(p1.getRow(), 3);
        assertEquals(p1.getCol(), 4);
    }
    @Test
    public void testMovePiece() {
        Board board  = new Board();
        Piece p1 = new Pawn(1, new Position(3,4), board);
        Piece p2 = new Rook(1, new Position(2,2), board);
        board.putPieceOnBoard(p1.getRow(), p1.getCol(), p1);
        board.putPieceOnBoard(p2.getRow(), p2.getCol(), p2);
        board.printBoard();
        Position lastPosition = new Position(p1.getRow(), p1.getCol());
        Piece old_piece = board.movePiece(p1, new Position(p2.getRow(),p2.getCol()));
        board.printBoard();
        assertEquals(old_piece, p2);
        assertNull(board.getPiece(lastPosition.getRow(), lastPosition.getCol()));
        //check moved piece location update
        assertEquals(board.getPiece(p1.getRow(), p1.getCol()), p1);

    }
    @Test

    public void testRemovePieceFromBorad() {
        Board board = new Board();
        Piece p1 = new Rook(-1, new Position(0,3), board);
        board.putPieceOnBoard(p1.getRow(), p1.getCol(), p1);
        assertNotNull(board.getPiece(p1.getRow(), p1.getCol()));
        board.removePieceFromBorad(p1.getRow(),p1.getCol());
        assertNull(board.getPiece(p1.getRow(), p1.getCol()));
    }
    @Test
    public void testGetPiece() {
        Board board = new Board();
        Piece p1 = new Rook(-1, new Position(0,3), board);
        board.putPieceOnBoard(p1.getRow(), p1.getCol(), p1);
        assertEquals(board.getPiece(p1.getRow(),p1.getCol()), p1);
    }
    @Test
    public void testIsChecked() {
        Board board = new Board();
        Piece p1 = new Rook(-1, new Position(0,3), board);
        Piece p2 = new Pawn(-1, new Position(5,1), board);
        Piece p3 = new Queen(1, new Position(2,0), board);
        Piece p4 = new Bishop(-1, new Position(4,4), board);
        Piece p5 = new Rook(-1, new Position(5,5), board);

        Piece k1 = new King(1, new Position(2,2), board);
        Piece k2 = new King(1, new Position(5,2), board);
        Piece k3 = new King(-1, new Position(6,4), board);
        Piece k4 = new King(1, new Position(2,4), board);

        board.putPieceOnBoard(k1.getRow(),k1.getCol(),k1);
        board.putPieceOnBoard(k2.getRow(),k2.getCol(),k2);
        board.putPieceOnBoard(k3.getRow(),k3.getCol(),k3);
        board.putPieceOnBoard(k4.getRow(),k4.getCol(),k4);
        board.putPieceOnBoard(p1.getRow(), p1.getCol(), p1);
        board.putPieceOnBoard(p2.getRow(), p2.getCol(), p2);
        board.putPieceOnBoard(p3.getRow(), p3.getCol(), p3);
        board.putPieceOnBoard(p4.getRow(), p4.getCol(), p4);
        board.putPieceOnBoard(p5.getRow(), p5.getCol(), p5);
        board.printBoard();
        boolean check_k1 = board.isChecked(k1.getColor(),new Position(k1.getRow(),k1.getCol()));
        boolean check_k2 = board.isChecked(k2.getColor(),new Position(k2.getRow(),k2.getCol()));
        boolean check_k3 = board.isChecked(k3.getColor(),new Position(k3.getRow(),k3.getCol()));
        boolean check_k4 = board.isChecked(k4.getColor(),new Position(k4.getRow(),k4.getCol()));
        assertTrue(check_k1);
        assertTrue(check_k2);
        assertTrue(check_k3);
        assertFalse(check_k4);
    }
    @Test
    public void testPawnThread() {
        Board board = new Board();
        Piece kingwhite = new King(1, new Position(2,2), board);
        Piece kingblack = new King(-1, new Position(5,2), board);
        Piece king3 = new King(1, new Position(3,4), board);
        Piece p1 = new Pawn(1, new Position(4,1), board);
        Piece p2 = new Pawn(-1, new Position(4,3), board);
        Piece p3 = new Pawn(1, new Position(3,1), board);
        Piece p4 = new Pawn(-1, new Position(1,1), board);

        board.putPieceOnBoard(kingwhite.getRow(),kingwhite.getCol(),kingwhite);
        board.putPieceOnBoard(kingblack.getRow(),kingblack.getCol(),kingblack);
        board.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);
        board.putPieceOnBoard(p1.getRow(), p1.getCol(), p1);
        board.putPieceOnBoard(p2.getRow(), p2.getCol(), p2);
        board.putPieceOnBoard(p3.getRow(), p3.getCol(), p3);
        board.putPieceOnBoard(p4.getRow(), p4.getCol(), p4);

        board.printBoard();
        // true
        assertTrue(board.pawnThread(-1*kingblack.getColor(),kingblack.getRow(),kingblack.getCol()));
        // false
        assertFalse(board.pawnThread(-1*kingwhite.getColor(),kingwhite.getRow(),kingwhite.getCol()));
        // true
        assertTrue(board.pawnThread(-1*king3.getColor(),king3.getRow(),king3.getCol()));

    }
    @Test
    public void testKingThread() {
        Board board = new Board();
        Piece king1 = new King(1, new Position(2,2), board);
        Piece king2 = new King(-1, new Position(5,2), board);

        Piece p1 = new King(1, new Position(3,2), board);
        Piece p2 = new King(-1, new Position(5,1), board);
        Piece p3 = new King(1, new Position(4,1), board);
        Piece p4 = new King(1, new Position(4,3), board);


        board.putPieceOnBoard(king1.getRow(),king1.getCol(),king1);
        board.putPieceOnBoard(king2.getRow(),king2.getCol(),king2);
        board.putPieceOnBoard(p1.getRow(), p1.getCol(), p1);
        board.putPieceOnBoard(p2.getRow(), p2.getCol(), p2);
        board.putPieceOnBoard(p3.getRow(), p3.getCol(), p3);
        board.putPieceOnBoard(p4.getRow(), p4.getCol(), p4);
        board.printBoard();
        //false
        assertFalse(board.kingThread(-1*king1.getColor(),king1.getRow(),king1.getCol()));
        //true
        assertTrue(board.kingThread(-1*king2.getColor(),king2.getRow(),king2.getCol()));
        //true
        assertTrue(board.kingThread(-1*p4.getColor(),p4.getRow(),p4.getCol()));
        //false
        assertFalse(board.kingThread(-1*p1.getColor(),p1.getRow(),p1.getCol()));

    }
    @Test
    public void testKnightThread() {
        Board board = new Board();
        Piece k1 = new King(1, new Position(2,2), board);
        Piece k2 = new King(-1, new Position(5,2), board);
        Piece k3 = new King(-1, new Position(6,4), board);

        Piece p1 = new Knight(-1, new Position(0,3), board);
        Piece p2 = new Knight(-1, new Position(5,1), board);
        Piece p3 = new Knight(1, new Position(4,1), board);
        Piece p4 = new Knight(1, new Position(4,4), board);

        board.putPieceOnBoard(k1.getRow(),k1.getCol(),k1);
        board.putPieceOnBoard(k2.getRow(),k2.getCol(),k2);
        board.putPieceOnBoard(k3.getRow(),k3.getCol(),k3);
        board.putPieceOnBoard(p1.getRow(), p1.getCol(), p1);
        board.putPieceOnBoard(p2.getRow(), p2.getCol(), p2);
        board.putPieceOnBoard(p3.getRow(), p3.getCol(), p3);
        board.putPieceOnBoard(p4.getRow(), p4.getCol(), p4);
        board.printBoard();
        //true
        assertTrue(board.knightThread(-1*k1.getColor(),k1.getRow(),k1.getCol()));
        //true
        assertTrue(board.knightThread(-1*k2.getColor(),k2.getRow(),k2.getCol()));
        //false
        assertFalse(board.knightThread(-1*k3.getColor(),k3.getRow(),k3.getCol()));
    }
    @Test
    public void testBishopQueenThread() {
        Board board = new Board();
        //board.bishopQueenThread(1, 3,4);

        Piece k1 = new King(1, new Position(2,2), board);
        Piece k2 = new King(-1, new Position(5,2), board);
        Piece k3 = new King(1, new Position(6,4), board);

        Piece p1 = new Bishop(-1, new Position(0,3), board);
        Piece p2 = new Bishop(-1, new Position(5,1), board);
        Piece p3 = new Bishop(1, new Position(3,0), board);
        Piece p4 = new Bishop(1, new Position(4,4), board);
        Piece p5 = new Bishop(-1, new Position(5,5), board);

        board.putPieceOnBoard(k1.getRow(),k1.getCol(),k1);
        board.putPieceOnBoard(k2.getRow(),k2.getCol(),k2);
        board.putPieceOnBoard(k3.getRow(),k3.getCol(),k3);
        board.putPieceOnBoard(p1.getRow(), p1.getCol(), p1);
        board.putPieceOnBoard(p2.getRow(), p2.getCol(), p2);
        board.putPieceOnBoard(p3.getRow(), p3.getCol(), p3);
        board.putPieceOnBoard(p4.getRow(), p4.getCol(), p4);
        board.putPieceOnBoard(p5.getRow(), p5.getCol(), p5);
        board.printBoard();
        //false
        assertFalse(board.bishopQueenThread(-1*k1.getColor(),k1.getRow(),k1.getCol()));
        //true
        assertTrue(board.bishopQueenThread(-1*k2.getColor(),k2.getRow(),k2.getCol()));
        //true
        assertTrue(board.bishopQueenThread(-1*k3.getColor(),k3.getRow(),k3.getCol()));
    }
    @Test
    public void testRookQueenThread() {
        Board board = new Board();
        Piece k1 = new King(1, new Position(2,2), board);
        Piece k2 = new King(1, new Position(5,2), board);
        Piece k3 = new King(-1, new Position(6,4), board);

        Piece p1 = new Rook(-1, new Position(0,3), board);
        Piece p2 = new Rook(-1, new Position(5,1), board);
        Piece p3 = new Rook(1, new Position(3,0), board);
        Piece p4 = new Rook(1, new Position(4,4), board);
        Piece p5 = new Rook(-1, new Position(5,5), board);

        board.putPieceOnBoard(k1.getRow(),k1.getCol(),k1);
        board.putPieceOnBoard(k2.getRow(),k2.getCol(),k2);
        board.putPieceOnBoard(k3.getRow(),k3.getCol(),k3);
        board.putPieceOnBoard(p1.getRow(), p1.getCol(), p1);
        board.putPieceOnBoard(p2.getRow(), p2.getCol(), p2);
        board.putPieceOnBoard(p3.getRow(), p3.getCol(), p3);
        board.putPieceOnBoard(p4.getRow(), p4.getCol(), p4);
        board.putPieceOnBoard(p5.getRow(), p5.getCol(), p5);
        board.printBoard();

        //false
        assertFalse(board.rookQueenThread(-1*k1.getColor(),k1.getRow(),k1.getCol()));
        //true
        assertTrue(board.rookQueenThread(-1*k2.getColor(),k2.getRow(),k2.getCol()));
        //true
        assertTrue(board.rookQueenThread(-1*k3.getColor(),k3.getRow(),k3.getCol()));
    }
    @Test
    public void testPrintBoard() {
    }
}