package Main;
import javafx.geometry.Pos;
import javax.crypto.spec.PSource;

public class Game {
    Board board;
    boolean whiteChecked;
    boolean blackChecked;
    Player pWhite;
    Player pBlack;
    int TURN;

    public Game(boolean CUSTOMIZED){
        board = new Board();
        whiteChecked = false;
        blackChecked = false;
        TURN = 1;
        if(CUSTOMIZED){
            board.startCUSTOBoard();
        }
        else{
            board.startingBoard();
        }

        board.printBoard();
        pWhite = new Player(1,new Position(0,3));
        pBlack = new Player(-1, new Position(7,3));
    }
    public void setWhiteOnCheck(){
        whiteChecked = true;
    }
    public void setBlackOnCheck(){
        blackChecked = true;
    }
    public void uncheckWhite(){
        whiteChecked = false;
    }
    public void uncheckBlack(){
        blackChecked = false;
    }
    public void flipTurn(){
        this.TURN = (-1)*TURN;
    }
    public int getTurn(){
        return this.TURN;
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

    }

    /*
    public boolean ownPiece(Piece piece){
        return this.color == piece.getColor();
    }
    */

}