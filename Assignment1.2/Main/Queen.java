package Main;

import java.util.ArrayDeque;
import java.util.Deque;

public class Queen extends Piece {

    public Queen(int color, Position position, Board board){
        /* Piece is the super class
         * use the constructor of the super class
         */
        super(color, position, board);
    }

    /**
     * Returns the integer that indicate valid or not
     * The queen moves any number of vacant squares in a horizontal, vertical, or diagonal direction.
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

        if (this.getRow()==i && this.getCol()==j)
            return -2;

        int moveLikeBishop = 0;
        int moveLikeRook = 0;
        if (Math.abs(this.getRow() - i) == Math.abs(this.getCol()- j))
            // moving diagonally
            moveLikeBishop = 1;
        if (this.getRow() ==  i || this.getCol() == j)
            // moving horizontal or vertical
            moveLikeRook = 1;

        if (moveLikeBishop==0 && moveLikeRook ==0)
            return -4;

        // Destination occupied by same color piece
        if (this.getBoard().chessPieces[i][j]!= null) {
            if (this.getBoard().chessPieces[i][j].getColor() == this.getColor()) {
                return -3;
            }
        }

        // loop bishop
        if (moveLikeBishop == 1){
            int step_row = (i - this.getRow()) > 0? 1: -1;
            int step_col = (j - this.getCol()) > 0? 1: -1;
            for (int r = this.getRow()+step_row, c = this.getCol()+step_col; r!=i; r+=step_row, c+=step_col){
                if (this.getBoard().chessPieces[r][c]!=null) {
                    //System.out.println("r,c = "+r+" "+c);
                    return -3;
                }
            }

            if (this.getBoard().chessPieces[i][j]!= null) {
                if (this.getBoard().chessPieces[i][j].getColor() != this.getColor()) {
                    //System.out.println("same color-piece in destination - check--1");
                    return 1;
                }
            }
            return 0;
        }
        else{
            if (this.getRow() != i){
                int step = (i - this.getRow()) > 0 ? 1 : -1;
                for (int r = this.getRow()+step; r!= i; r+=step){
                    if (this.getBoard().chessPieces[r][j]!= null){
                        return -3;
                    }
                }
            }
            else{   //(this.getCol() != j)
                int step = (j - this.getCol()) > 0 ? 1 : -1;
                for (int c = this.getCol()+step; c!= j; c+=step){
                    if (this.getBoard().chessPieces[i][c]!=null) {
                        return -3;
                    }
                }
            }
            // check last piece.
            if (this.getBoard().chessPieces[i][j]!= null) {
                if (this.getBoard().chessPieces[i][j].getColor() != this.getColor()) {
                    return 1;
                    // 1 means: will eat the opponent's piece
                }
            }
            return 0;
        }
    }
    /**
     * Returns all the potential moves
     * @return     Deque<Position> of all vaild positions
     */
    public Deque<Position> potentialMoves(){
        Deque<Position> deque = new ArrayDeque<Position>();
        int row = this.getRow();
        int col = this.getCol();

        // Bishop pattern
        // upper left
        for (int r_step = 1, c_step = -1; row + r_step < 8 && col + c_step > -1; r_step ++, c_step --){
            if (judgeMove(row+r_step, col+c_step)>=0){
                deque.add(new Position(row+r_step, col+c_step));
                if (this.getBoard().chessPieces[row+r_step][col+c_step]!=null) {break;}
            }
        }
        // upper right
        for (int r_step = 1, c_step = 1; row + r_step < 8 && col + c_step < 8; r_step ++, c_step ++){
            if (judgeMove(row+r_step, col+c_step)>=0){
                deque.add(new Position(row+r_step, col+c_step));
                if (this.getBoard().chessPieces[row+r_step][col+c_step]!=null) {break;}
            }
        }
        // lower left
        for (int r_step = -1, c_step = -1; row + r_step > -1 && col + c_step > -1; r_step --, c_step --){
            if (judgeMove(row+r_step, col+c_step)>=0){
                deque.add(new Position(row+r_step, col+c_step));
                if (this.getBoard().chessPieces[row+r_step][col+c_step]!=null) {break;}
            }
        }
        // lower right
        for (int r_step = -1, c_step = 1; row + r_step > -1 && col + c_step < 8; r_step --, c_step ++){
            if (judgeMove(row+r_step, col+c_step)>=0){
                deque.add(new Position(row+r_step, col+c_step));
                if (this.getBoard().chessPieces[row+r_step][col+c_step]!=null) {break;}
            }
        }
        // Rook pattern
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
