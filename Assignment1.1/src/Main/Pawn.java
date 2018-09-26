package Main;

import java.util.ArrayDeque;
import java.util.Deque;

public class Pawn extends Piece{
    // !!!!! ATTENTION !!!!!
    // !!!!! must remember to set isFirstMove = 0 !!! at any other move!! of PAWN
    // !!!!!
    int isFirstMove;

    public Pawn( int color, Position position, Board board){
        /* Piece is the super class
         * use the constructor of the super class
         * super() constructor: In the given program,
         * when an object to My_Calculation class is created,
         * a copy of the contents of the superclass is made within it.
         * That is why, using the object of the subclass you can access the members of a superclass.
         *  more: https://www.tutorialspoint.com/java/java_inheritance.htm
         */
        super(color, position, board);
        isFirstMove = 1;
    }

    public void changeToMoved(){
        this.isFirstMove = 0;
    }

    /**
     * Returns the integer that indicate valid or not
     * (1)  A pawn moves straight forward one square, if that square is vacant.
     *      If it has not yet moved,
     *      a pawn also has the option of moving two squares straight forward,
     *      provided both squares are vacant. Pawns cannot move backwards.
     * (2)  Pawns are the only pieces that capture differently from how they move.
     *      A pawn can capture an enemy piece on either of the two squares
     *      diagonally in front of the pawn (but cannot move to those squares if they are vacant).
     *
     * @param  i    new row
     * @param  j    new col
     * @return      1:  will eat the opponent's piece
     *              0:  is valid
     *              -1: out of bound
     *              -2: did not move
     *              -3: blocked by same/other color piece
     *              -4: invalid moving range
     */
    public int judgeMove(int i, int j){
        if (i<0 || j<0 ||i>7 ||j>7)
            return -1;

        if (this.getRow() == i && this.getCol()== j)
            return -2;

        // check move up/down 1 or two steps
        if (this.getCol() == j && Math.abs(this.getRow()-i)<=2){
            // black color = -1 move row down
            // white color = 1 move row up
            if (i - this.getRow() == this.getColor()){
                if (this.getBoard().chessPieces[i][j] == null){
                    return 0;
                }
                return -3;
            }
            if (this.isFirstMove == 1 && i - this.getRow() == this.getColor() * 2){
                if (this.getBoard().chessPieces[i][j] != null || this.getBoard().chessPieces[i+this.getColor()][j] != null){
                    return -3;
                }
                return 0;
            }
        }
        // check eat opponent's piece
        else if (Math.abs(this.getCol()-j) == 1 && (i - this.getRow() == this.getColor())){
            if (this.getBoard().chessPieces[i][j]!=null && this.getBoard().chessPieces[i][j].getColor()!=this.getColor()){
                return 1;
            }
        }
        return -4;
    }
    /**
     * Returns all the potential moves
     * @return     Deque<Position> of all vaild positions
     */
    public Deque<Position> potentialMoves(){
        Deque<Position> deque = new ArrayDeque<Position>();
        int row = this.getRow();
        int col = this.getCol();
        if (judgeMove(row + this.getColor(), col)>=0){
            deque.add(new Position(row + this.getColor(), col));
        }
        if (isFirstMove == 1 && judgeMove(row + this.getColor(), col) == 0){
            if (judgeMove(row + this.getColor()*2, col)>=0){
                deque.add(new Position(row + this.getColor()*2, col));
            }
        }
        return deque;
    }
}
