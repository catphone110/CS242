package Main;

public class Piece {
    /**
     * Class piece
     * color: -1 = black. 1 = white
     */

    private int color;
    private Position position;
    private Board board;

    public Piece(int color, Position position, Board board){
        this.color = color;
        this.position = position;
        this.board = board;
    }

    public Board getBoard(){
        return this.board;
    }

    public int getRow(){
        return this.position.row;
    }

    public int getCol(){
        return this.position.col;
    }

    public int getColor(){
        return this.color;
    }

    public void setPosition(Position position){
        this.position = position;
    }


}
