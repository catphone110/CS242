package Main;

import java.util.ArrayDeque;
import java.util.Deque;

public class Rook extends Piece{

    public Rook(int color, Position position, Board board){
        /* Piece is the super class
         * use the constructor of the super class
         */
        super(color, position, board);
    }

    /**
     * Returns the integer that indicate valid or not
     * Rook moves any number of vacant squares in a horizontal or vertical direction.
     * It also is moved when castling.
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

        if (this.getRow()!=i && this.getCol()!=j)
            return -4;

        if (this.getBoard().chessPieces[i][j]!= null) {
            if (this.getBoard().chessPieces[i][j].getColor() == this.getColor()) {
                return -3;
            }
        }

        if (this.getRow() != i){
            int step = (i - this.getRow()) > 0 ? 1 : -1;
            for (int r = this.getRow()+step; r!= i; r+=step){
                if (this.getBoard().chessPieces[r][j]!= null){
                    return -3;
                }
            }
            // last one unchecked
        }

        if(this.getCol() != j){
            int step = (j - this.getCol()) > 0 ? 1 : -1;
            for (int c = this.getCol()+step; c!= j; c+=step){
                if (this.getBoard().chessPieces[i][c]!=null) {
                    return -3;
                }
            }
            // last one unchecked
        }
        //
        if (this.getBoard().chessPieces[i][j]!= null) {
            if (this.getBoard().chessPieces[i][j].getColor() != this.getColor()) {
                return 1;
                // 1 means: will eat the opponent's piece
            }
        }
        return 0;
    }

    /**
     * Returns all the potential moves
     * @return     Deque<Position> of all vaild positions
     */
    public Deque<Position> potentialMoves(){
        Deque<Position> deque = new ArrayDeque<Position>();
        int row = this.getRow();
        int col = this.getCol();

        // upper
        for (int r_step = 1 ; row + r_step < 8 ; r_step ++) {
            if (judgeMove(row+r_step, col)>=0){
                deque.add(new Position(row+r_step, col));
                if (this.getBoard().chessPieces[row+r_step][col]!=null) {break;}
            }
        }
        // lower
        for (int r_step = -1 ; row + r_step >-1; r_step --) {
            if (judgeMove(row+r_step, col)>=0){
                deque.add(new Position(row+r_step, col));
                if (this.getBoard().chessPieces[row+r_step][col]!=null) {break;}
            }
        }
        // left
        for (int c_step = -1; col + c_step >= 0; c_step --) {
            if (judgeMove(row, col+c_step)>=0){
                deque.add(new Position(row, col+c_step));
                if (this.getBoard().chessPieces[row][col+c_step]!=null) {break;}
            }
        }
        // right
        for (int c_step = 1; col + c_step <= 7 ; c_step ++) {
            if (judgeMove(row, col+c_step)>=0){
                deque.add(new Position(row, col+c_step));
                if (this.getBoard().chessPieces[row][col+c_step]!=null) {break;}
            }
        }
        return deque;
    }

}
