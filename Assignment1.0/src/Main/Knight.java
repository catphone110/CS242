package Main;

import java.util.ArrayDeque;
import java.util.Deque;

public class Knight extends Piece{
    public Knight(int color, Position position, Board board){
         /* Piece is the super class
         * use the constructor of the super class
         */
        super(color, position, board);
    }

    /**
     * Returns the integer that indicate valid or not
     * A knight moves to the nearest square not on the same rank, file, or diagonal.
     * (This can be thought of as moving two squares horizontally then one square vertically,
     * or moving one square horizontally then two squares vertically—i.e. in an "L" pattern.)
     * The knight is not blocked by other pieces:
     * it jumps to the new location.
     * @param  i    new row
     * @param  j    new col
     * @return      1:  will eat the opponent's piece
     *              0:  is valid
     *              -1: out of bound
     *              -2: did not move
     *              -3: blocked by same color piece
     *              -4: invalid moving range
     */

    public int judgeMove(int i, int j){
        if (i<0 || j<0 ||i>7 ||j>7)
            return -1;

        if (this.getRow() == i && this.getCol()==j)
            return -2;

        if (!((Math.abs(this.getRow()-i) == 1 && Math.abs(this.getCol()-j) ==2)
                || (Math.abs(this.getRow()-i) == 2 && Math.abs(this.getCol()-j) ==1)
            ))
            return -4;

        if (this.getBoard().chessPieces[i][j]==null)
            return 0;

        if (this.getBoard().chessPieces[i][j].getColor() == this.getColor()) {
            return -3;
        }
        else
            return 1;

        // valid and nothing happened
    }

    public Deque<Position> potentialMoves(){
        Deque<Position> deque = new ArrayDeque<Position>();
        int row = this.getRow();
        int col = this.getCol();

        int [] rowSteps = {-2, -1, 1, 2};
        int [] colSteps = {-2, -1, 1, 2};
        for (int r_step : rowSteps){
            for (int c_step : colSteps){
                // knight's possible move
                if (Math.abs(r_step)+Math.abs(c_step)==3){
                    if (judgeMove(row+r_step, col+c_step)>=0){
                        deque.add(new Position(row+r_step, col+c_step));
                    }
                }
            }
        }
        return deque;
    }


}
