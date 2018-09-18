package Main;

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
                System.out.println("same color-piece in destination - check--1");
                return -3;
            }
        }

        // loop bishop
        if (moveLikeBishop == 1){
            int step_row = (i - this.getRow()) > 0? 1: -1;
            int step_col = (j - this.getCol()) > 0? 1: -1;
            for (int r = this.getRow()+step_row, c = this.getCol()+step_col; r!=i; r+=step_row, c+=step_col){
                if (this.getBoard().chessPieces[r][c]!=null) {
                    System.out.println("r,c = "+r+" "+c);
                    return -3;
                }
            }

            if (this.getBoard().chessPieces[i][j]!= null) {
                if (this.getBoard().chessPieces[i][j].getColor() != this.getColor()) {
                    System.out.println("same color-piece in destination - check--1");
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
                        System.out.println("hereh----2");
                        return -3;
                    }
                }
            }
            else{   //(this.getCol() != j)
                int step = (j - this.getCol()) > 0 ? 1 : -1;
                for (int c = this.getCol()+step; c!= j; c+=step){
                    if (this.getBoard().chessPieces[i][c]!=null) {

                        System.out.println("hereh----3");
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
}
