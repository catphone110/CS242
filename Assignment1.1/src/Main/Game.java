package Main;
import javafx.geometry.Pos;
import javax.crypto.spec.PSource;

public class Game {
    Board board;
    Player pWhite;
    Player pBlack;

    public Game(){
        board = new Board();
        board.startingBoard();
        board.printBoard();
        pWhite = new Player(1,new Position(0,3));
        pBlack = new Player(-1, new Position(7,3));
    }

    public Board getBoard(){
        return this.board;
    }
    /**
     * move if instance of King, must update player's kingPosition;
     * move if instance of Pawn, must update Pawn's isFirstMove;
    */
    // public void movePiece(Player player, Position pos1, Position pos2){ }

}// end game class


class Player{
    int color;
    //int gameStatus;
    Position kingPosition;

    public Player(int color, Position kingPosition){
        this.color = color;
        kingPosition = kingPosition;
        //this.gameStatus = gameStatus;
    }

    /*
    public boolean ownPiece(Piece piece){
        return this.color == piece.getColor();
    }
    */

}