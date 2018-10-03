package Main;
import java.util.ArrayDeque;
import java.util.Deque;

public class Board {
    Piece [][] chessPieces;

    /**
    * Board coordination:
    * [r 7,c=0]    [r 7,c=1]   [r 7,c=2]   [r 7,c=3]   [r 7,c 4]   [r 7,c=5]   [r 7,c=6]   [r 7,c=7]
    * [r 6,c=0]
    * [r 5,c=0]
    * [r 4,c=0]
    * [r 3,c=0]
    * [r 2,c=0]
    * [r 1,c=0]    [r 1,c=1]   [r 1,c=2]   [r 1,c=3]   [r 1,c 4]   [r 1,c=5]   [r 1,c=6]   [r 1,c=7]
    * [r 0,c=0]    [r 0,c=1]   [r 0,c=2]   [r 0,c=3]   [r 0,c 4]   [r 0,c=5]   [r 0,c=6]   [r 0,c=7]
    */

    public Board(){
        chessPieces = new Piece[8][8];
        for (int i = 0; i<8; i++){
            for (int j = 0;  j<8; j++) {
                chessPieces[i][j] = null;
            }
        }
    }

    /**
     * This function will initialize the board to the standard beginning model.
     */
    public void startingBoard(){
        for (int row = 6, col = 0; col<8; col++){
            chessPieces[row][col] = new Pawn(-1, new Position(row, col), this);
        }
        for (int row = 1, col = 0; col<8; col++){
            chessPieces[row][col] = new Pawn(1, new Position(row, col), this);
        }
        int [] colorSet = {1,-1};
        for (int color : colorSet){
            int row = (color == 1)? 0 : 7;
            // Rook
            chessPieces[row][0] = new Rook(color, new Position(row, 0), this);
            chessPieces[row][7] = new Rook(color, new Position(row, 7), this);
            // Knight
            chessPieces[row][1] = new Knight(color, new Position(row, 1), this);
            chessPieces[row][6] = new Knight(color, new Position(row, 6), this);
            // Bishop
            chessPieces[row][2] = new Bishop(color, new Position(row, 2), this);
            chessPieces[row][5] = new Bishop(color, new Position(row, 5), this);
            // King
            chessPieces[row][3] = new King(color, new Position(row, 3), this);
            // Queen
            chessPieces[row][4] = new Queen(color, new Position(row, 4), this);
        }
    }

    public void startCUSTOBoard(){
        for (int row = 6, col = 0; col<8; col++){
            chessPieces[row][col] = new Pawn(-1, new Position(row, col), this);
        }
        for (int row = 1, col = 0; col<8; col++){
            chessPieces[row][col] = new Pawn(1, new Position(row, col), this);
        }
        /*
        chessPieces[6][0] = new Falcon(-1, new Position(6, 0), this);
        chessPieces[6][7] = new FlyingBishop(-1, new Position(6, 7), this);

        chessPieces[1][0] = new Falcon(1, new Position(1, 0), this);
        chessPieces[1][7] = new FlyingBishop(1, new Position(1, 7), this);
        */

        int [] colorSet = {1,-1};
        for (int color : colorSet) {
            int row = (color == 1) ? 0 : 7;
            // King
            chessPieces[row][3] = new King(color, new Position(row, 3), this);
            // Queen
            chessPieces[row][4] = new Queen(color, new Position(row, 4), this);
            // Rook
            chessPieces[row][0] = new Rook(color, new Position(row, 0), this);
            chessPieces[row][7] = new Rook(color, new Position(row, 7), this);
            // customized
            chessPieces[row][1] = new Falcon(color, new Position(row, 1), this);
            chessPieces[row][6] = new FlyingBishop(color, new Position(row, 6), this);
            // Bishop
            chessPieces[row][2] = new Bishop(color, new Position(row, 2), this);
            chessPieces[row][5] = new Bishop(color, new Position(row, 5), this);
        }
    }

    /**
     * This function put the customized Piece on give position.
     *  @param  position    target position
     *  @param  piece       customized piece
     */
    public void customizePiece(Position position, Piece piece){
        Piece temp = getPiece(position.getRow(), position.getCol());
        if (temp != null) {
            chessPieces[position.getRow()][position.getCol()] = piece;
        }
    }

    public void putPieceOnBoard(int i, int j, Piece piece) {
        this.chessPieces[i][j] = piece;
    }

    public void removePieceFromBorad(int i, int j){
        this.chessPieces[i][j] = null;
    }

    public Position getKingPosition(int color){
        for (int row = 0; row<8; row++){
            for (int col = 0; col<8; col++){
                if (chessPieces[row][col] instanceof King && chessPieces[row][col].getColor() == color){
                    return new Position(row, col);
                }
            }
        }
        return null;
    }

    public int tryMove(Piece p, Position position){
        int row = position.getRow(), col = position.getCol();
        //Piece p = getPiece(row, col);
        if (p == null){
            return -1;
        }
        else if (p instanceof King){
            return ((King) p).judgeMove(row, col);
        }
        else if (p instanceof Knight){
            return ((Knight) p).judgeMove(row, col);
        }
        else if (p instanceof Bishop){
            return ((Bishop) p).judgeMove(row, col);
        }
        else if (p instanceof FlyingBishop){
            return ((FlyingBishop) p).judgeMove(row, col);
        }
        else if (p instanceof Pawn){
            return ((Pawn) p).judgeMove(row, col);
        }
        else if (p instanceof Falcon){
            return ((Falcon) p).judgeMove(row, col);
        }
        else if (p instanceof Queen){
            return ((Queen) p).judgeMove(row, col);
        }
        else { //(p instanceof Rook)
            return ((Rook) p).judgeMove(row, col);
        }
    }

    public  Deque<Position> getPotentialMove(Piece p){
        if (p == null){
            return null;
        }
        else if (p instanceof King){
            return ((King) p).potentialMoves();
        }
        else if (p instanceof Knight){
            return ((Knight) p).potentialMoves();
        }
        else if (p instanceof Bishop){
            return ((Bishop) p).potentialMoves();
        }
        else if (p instanceof Pawn){
            return ((Pawn) p).potentialMoves();
        }
        else if (p instanceof FlyingBishop){
            return ((FlyingBishop) p).potentialMoves();
        }
        else if (p instanceof Falcon){
            return ((Falcon) p).potentialMoves();
        }
        else if (p instanceof Queen){
            return ((Queen) p).potentialMoves();
        }
        else { //(p instanceof Rook)
            return ((Rook) p).potentialMoves();
        }
    }

    /**
     * This function will move the give piece to a new location (must be check valid before call this function)
     * This function should be called in Game().
     * This function will take care of piece location update.
     * @param  piece      last moved piece
     * @param  position   destination location
     * @return      the old piece which is being replaced by the new piece
     *              no prev piece, return null.
     */
    public Piece movePiece(Piece piece, Position position){
        Piece old_piece = chessPieces[position.row][position.col];
        if (piece instanceof Pawn) ((Pawn) piece).incrementMove();
        chessPieces[position.row][position.col] = piece;
        chessPieces[piece.getRow()][piece.getCol()] = null;
        piece.setPosition(position);
        return old_piece;
    }

    /**
     * This function will undo the last move
     * last move should be recorded in Game.java
     * This function should be called in checkmate condition.
     * This function will take care of piece location update.
     * @param  piece  last moved piece
     * @param  oldPiece  old piece is the return object of movePiece()
     *                   which is the chessPiece[current piece's current position]'s old piece
     *                   either be null or a Piece object
     * @param  lastPosition  current's previous position
     *                       in Game() only lastposition
     *                                      and last_move piece should be record.
     * @return      null for null piece: indicated last move is used and no undomove can be done
     */
    public Piece undoMovePiece(Piece piece, Piece oldPiece, Position lastPosition){
        if (piece instanceof Pawn) ((Pawn) piece).decrementMove();
        this.chessPieces[piece.getRow()][piece.getCol()] = oldPiece;
        this.chessPieces[lastPosition.row][lastPosition.col] = piece;
        piece.setPosition(lastPosition);
        return null;
    }

    /**
     * This function will check the given king's player is in stalemate condition
     * @param  king the king piece
     * @return      true/false
     */
    public boolean staleMate(Piece king) {
        // Default condition: king is on check
        int color = king.getColor();
        int row = king.getRow();
        int col = king.getCol();
        // loop through the board to check all-same-color piece's all possible movement's
        // will any of the movement not put king on check.
        for (int r = 0; r<8; r++){
            for(int c = 0; c<8; c++){
                Piece piece = getPiece(r,c);
                // skip if empty
                if (piece == null){ continue; }
                // skip those same color pieces
                if (piece.getColor() != color){ continue; }

                Deque <Position> possibleMove = new ArrayDeque<Position>();

                /** if this piece is instanceof King
                 *  this piece's isChecked() parameter must be new king position!
                 *  otherwise the return result is false
                 */
                if(piece instanceof King){
                    possibleMove = ((King) piece).potentialMoves();
                    for (Position position : possibleMove){
                        Position oldPosition = new Position(r, c);
                        Piece removedPiece = movePiece(piece, position);
                        boolean afterMoveStillChecked = this.isChecked(king.getColor(), position);
                        undoMovePiece(piece, removedPiece, oldPosition);
                        if (!afterMoveStillChecked) return false;
                    }
                    continue;
                }
                else if (piece instanceof Bishop){
                    possibleMove = ((Bishop) piece).potentialMoves();
                }
                else if(piece instanceof Knight){
                    possibleMove = ((Knight) piece).potentialMoves();
                }
                else if(piece instanceof Pawn){
                    possibleMove = ((Pawn) piece).potentialMoves();
                }
                else if(piece instanceof Queen){
                    possibleMove = ((Queen) piece).potentialMoves();
                }
                else if(piece instanceof Rook){
                    possibleMove = ((Rook) piece).potentialMoves();
                }

                // loop through all the potential moves
                for (Position position : possibleMove){
                    Position oldPosition = new Position(r, c);
                    Piece removedPiece = movePiece(piece, position);
                    boolean afterMoveStillChecked = this.isChecked(king.getColor(), new Position(row, col));
                    undoMovePiece(piece, removedPiece, oldPosition);
                    if (!afterMoveStillChecked) return false;
                }
            }
        }
        return true;
    }

    /**
     * This function will check if one king is in check-mate condition (if yes end game)
     * precondition of check-mate : king must be checked!
     * @param  king the king piece
     * @return      true/false
     */
    public boolean checkMate(Piece king){
        if (!isChecked(king.getColor(), new Position(king.getRow(), king.getCol()))) return false;
        return staleMate(king);
    }

    public Piece getPiece(int row, int col){
        if (row<0 || row>7 || col<0 ||col>7){
            return null;
        }
        return chessPieces[row][col];
    }

    /**
     * This function will return weather this king is being checked
     * This function should be called when the other player moved one piece.
     * @param  position  king's position
     * @param  position  king's color
     * @return      true:  is checked
     *              false:  a free king
     */
    public boolean isChecked(int color, Position position){
        int row = position.row;
        int col = position.col;
        int opponentColor = -1*color;
        return (pawnThread(opponentColor, row, col) ||
                kingThread(opponentColor, row, col) ||
                knightThread(opponentColor, row, col) ||
                bishopQueenThread(opponentColor, row, col) ||
                rookQueenThread(opponentColor, row, col)
                );
    }

    /**
     * Helper function of isChecked();
     * This function will return is pawn in the color found near this position that can thread the king.
     * @param  opponentColor    king's opposite color
     * @param  row      king's row
     * @param  col      king's col
     * @return      true:  is checked
     *              false:  a free king
     */
    public boolean pawnThread(int opponentColor, int row, int col){
        Piece pawn1 = getPiece(row-opponentColor, col +1);
        Piece pawn2 = getPiece(row-opponentColor, col -1);
        boolean check = false;
        if (pawn1 != null){
            if (pawn1 instanceof Pawn && pawn1.getColor() == opponentColor) check = true;
        }
        if (pawn2 != null){
            if (pawn2 instanceof Pawn && pawn2.getColor() == opponentColor) check = true;
        }
        return check;
    }

    /**
     * Helper function of isChecked();
     * This function will return will be threaten by the other king.
     * @param  opponentColor    king's opposite color
     * @param  row      king's row
     * @param  col      king's col
     * @return      true:  is checked
     *              false:  a free king
     */
    public boolean kingThread(int opponentColor, int row, int col){
        for (int r_step = -1; r_step < 2; r_step ++){
            for (int c_step = -1; c_step < 2; c_step ++){
                if (r_step == 0 && c_step == 0) {continue;}
                Piece king = getPiece(row+r_step, col+c_step);
                if (king!=null){
                    if ((king instanceof King || king instanceof Queen) && king.getColor() == opponentColor){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Helper function of isChecked();
     * This function will return will be threaten by the other knight.
     * @param  opponentColor    king's opposite color
     * @param  row      king's row
     * @param  col      king's col
     * @return      true:  is checked
     *              false:  a free king
     */
    public boolean knightThread(int opponentColor, int row, int col){
        int [] rowSteps = {-2, -1, 1, 2};
        int [] colSteps = {-2, -1, 1, 2};
        for (int r_step : rowSteps){
            for (int c_step : colSteps){
                // knight's possible move
                if (Math.abs(r_step)+Math.abs(c_step)==3){
                    Piece knight = getPiece(row +r_step, col+c_step);
                    if (knight!=null){
                        if (knight instanceof Knight  && knight.getColor() == opponentColor){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Helper function of isChecked();
     * This function will return will be threaten by the other Bishop or Queen(bishop's part).
     * @param  opponentColor    king's opposite color
     * @param  row      king's row
     * @param  col      king's col
     * @return      true:  is checked
     *              false:  a free king
     */
    public boolean bishopQueenThread(int opponentColor, int row, int col){
        // upper left
        for (int r_step = 1, c_step = -1; row + r_step < 8 && col + c_step > -1; r_step ++, c_step --){
            Piece piece = getPiece(row + r_step , col + c_step);
            if (piece!= null){
                if ((piece instanceof Bishop || piece instanceof Queen) && piece.getColor() == opponentColor){
                    return true;
                }
                // break for if bing blocked way by any other piece.
                break;
            }
        }
        // upper right
        for (int r_step = 1, c_step = 1; row + r_step < 8 && col + c_step < 8; r_step ++, c_step ++){
            Piece piece = getPiece(row + r_step , col + c_step);
            if (piece!= null){
                if ((piece instanceof Bishop || piece instanceof Queen) && piece.getColor() == opponentColor){
                    return true;
                }
                break;
            }
        }
        // lower left
        for (int r_step = -1, c_step = -1; row + r_step > -1 && col + c_step > -1; r_step --, c_step --){
            Piece piece = getPiece(row + r_step , col + c_step);
            if (piece!= null){
                if ((piece instanceof Bishop || piece instanceof Queen) && piece.getColor() == opponentColor){
                    return true;
                }
                break;
            }
        }
        // lower right
        for (int r_step = -1, c_step = 1; row + r_step > -1 && col + c_step < 8; r_step --, c_step ++){
            Piece piece = getPiece(row + r_step , col + c_step);
            if (piece!= null){
                if ((piece instanceof Bishop || piece instanceof Queen) && piece.getColor() == opponentColor){
                    return true;
                }
                break;
            }
        }
        return false;
    }

    /**
     * Helper function of isChecked();
     * This function will return will be threaten by the other Rook or Queen(bishop's part).
     * @param  opponentColor    king's opposite color
     * @param  row              king's row
     * @param  col              king's col
     * @return      true:  is checked
     *              false:  a free king
     */
    public boolean rookQueenThread(int opponentColor, int row, int col){
        // upper
        for (int r_step = 1 ; row + r_step < 8 ; r_step ++){
            Piece piece = getPiece(row + r_step , col);
            if (piece!= null){
                if ((piece instanceof Rook || piece instanceof Queen) && piece.getColor() == opponentColor){
                    return true;
                }
                // break for if bing blocked way by any other piece.
                break;
            }
        }
        // lower
        for (int r_step = -1 ; row + r_step >-1; r_step --){
            Piece piece = getPiece(row + r_step , col);
            if (piece!= null){
                if ((piece instanceof Rook || piece instanceof Queen) && piece.getColor() == opponentColor){
                    return true;
                }
                break;
            }
        }
        // left
        for (int c_step = -1; col + c_step >= 0; c_step --){
            Piece piece = getPiece(row,col + c_step);
            if (piece!= null){
                if ((piece instanceof Rook || piece instanceof Queen) && piece.getColor() == opponentColor){
                    return true;
                }
                break;
            }
        }
        // right
        for (int c_step = 1; col + c_step <= 7 ; c_step ++){
            Piece piece = getPiece(row, col + c_step);
            if (piece!= null){
                if ((piece instanceof Rook || piece instanceof Queen) && piece.getColor() == opponentColor){
                    return true;
                }
                break;
            }
        }
        return false;

    }

    /**
     * Print the current game board
     * Helpful for me to debug.
     */
    public void printBoard(){
        System.out.println("======================================================================");
        for (int i = 7; i>-1; i--){
            for(int j = 0; j<8; j++){
                if (chessPieces[i][j]!=null){
                    String name = chessPieces[i][j].getClass().getName()+chessPieces[i][j].getColor();
                    name = name.substring(5);
                    if (name.length() > 12)
                        System.out.print("F_Bishop"+chessPieces[i][j].getColor()+"\t");
                    else if (name.length()<8)
                        System.out.print(name+"\t\t");
                    else System.out.print(name+"\t");
                }
                else
                    System.out.print("["+i+"  "+j+"]\t\t");
            }
            System.out.println();
        }
        System.out.println("======================================================================");
    }
}