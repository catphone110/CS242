package Main;

public class Position {
    int row; // i
    int col; // j

    public Position(int i, int j){
        this.row = i;
        this.col = j;
    }

    public int changePosition(int i, int j){
        this.row = i;
        this.col = j;
        return 0;
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }
}
