package Main;

public class Main {

    public static void main(String[] args) {

        /**
         * Returns the integer that indicate valid or not
         * King can move exactly one square horizontally, vertically, or diagonally.
         * At most once in every game, each king is allowed to make a special move, known as castling.
         * @param  i    new row
         * @param  j    new col
         * @return      1:  will eat the opponent's piece
         *              0:  is valid
         *              -1: out of bound
         *              -2: did not move
         *              -3: blocked by same/other color piece
         *              -4: invalid moving range
         */

        /*
        // test Root-1
        System.out.println("Hello World!");
        Board test = new Board();
        test.printBoard();
        Piece king1 = new King(1, new Position(2,2), test);
        Piece king2 = new King(1, new Position(1,1), test);
        Piece ROOK3 = new Rook(-1, new Position(1,2), test);
        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(ROOK3.getRow(), ROOK3.getCol(), ROOK3);
        test.printBoard();
        Object r = ROOK3;
        if (r instanceof Rook){
            System.out.println("r instanceof Rook");
            //r = (Rook) r;
            //System.out.println(((King) k).getBoard()==null);
            //.printBoard();
            int result = ((Rook) r).judgeMove(3,2);
            System.out.println("result"+result);
        }

        // test Bishop
        Board test = new Board();
        test.printBoard();
        Piece king1 = new King(1, new Position(2,2), test);
        Piece king2 = new King(-1, new Position(1,1), test);
        Piece king3 = new King(1, new Position(2,3), test);

        Piece bishop = new Bishop(-1, new Position(1,2), test);
        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);
        test.putPieceOnBoard(bishop.getRow(), bishop.getCol(), bishop);
        test.printBoard();
        Object r = bishop;
        if (r instanceof Bishop){
            System.out.println("r instanceof Bishop");
            // 2,1 == 0
            // 2,3 == 1
            // 4,5 == -3
            int result = ((Bishop) r).judgeMove(4,5);
            System.out.println("result"+result);
        }


        // test Queen
        Board test = new Board();
        test.printBoard();
        Piece king1 = new King(1, new Position(2,2), test);
        Piece king2 = new King(-1, new Position(1,1), test);
        Piece king3 = new King(1, new Position(2,3), test);

        Piece queen = new Queen(-1, new Position(3,2), test);
        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);
        test.putPieceOnBoard(queen.getRow(), queen.getCol(), queen);
        test.printBoard();
        Object r = queen;
        if (r instanceof Queen){
            System.out.println("r instanceof Queen");
            // 4,3 == 0
            // 2,2 == 1
            // 1,4 == -3
            // 3,2 == -2

            // 1,4 == -3
            // 5,2 == 0
            // 5,4 == 0
            // 1,0 == 0

            // 0,2 == -3
            // 2,3 == 1

        }
        Piece king4 = new King(1, new Position(4,1), test);
        test.putPieceOnBoard(king4.getRow(), king4.getCol(), king4);
        test.printBoard();
        // 4，1 == 1
        // 5, 0 == -3
        // 5, 5 == -4
        int result = ((Queen) r).judgeMove(5,5);
        System.out.println("result"+result);

        //test Knight
        Board test = new Board();
        test.printBoard();
        Piece king1 = new King(1, new Position(2,2), test);
        Piece king2 = new King(-1, new Position(1,1), test);
        Piece king3 = new King(1, new Position(2,3), test);
        Piece queen = new Queen(-1, new Position(3,3), test);
        Piece queen1 = new Queen(1, new Position(3,1), test);

        Piece knight = new Knight(-1, new Position(1,2), test);
        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);
        test.putPieceOnBoard(queen.getRow(), queen.getCol(), queen);
        test.putPieceOnBoard(queen1.getRow(), queen1.getCol(), queen1);
        test.putPieceOnBoard(knight.getRow(), knight.getCol(), knight);
        test.printBoard();
        Object r = knight;
        if (r instanceof Knight){
            System.out.println("r instanceof Knight");
            // 2,0 == 0
            // 3,1 == 1
            // 3,3 == -3
            // 2,3 == -4
            int result = ((Knight) r).judgeMove(3,1);
            System.out.println("result"+result);
        }

        //test Pawn
        Board test = new Board();
        Piece king1 = new King(-1, new Position(2,2), test);
        Piece king2 = new King(-1, new Position(1,1), test);
        Piece king3 = new King(-1, new Position(3,3), test);
        Piece king4 = new King(1, new Position(4,0), test);

        Piece pawn = new Pawn(1, new Position(1,3), test);
        Piece pawn_1 = new Pawn(-1, new Position(5,1), test);
        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);
        test.putPieceOnBoard(king4.getRow(), king4.getCol(), king4);
        test.putPieceOnBoard(pawn.getRow(), pawn.getCol(), pawn);
        test.putPieceOnBoard(pawn_1.getRow(), pawn_1.getCol(), pawn_1);

        test.printBoard();
        // pawn 2,2 = 1
        // pawn 2,3 = 0
        // pawn -3, 0 = -1
        // pawn 1,3 = -2
        // pawn 0,3 = -4
        // pawn 3,3 = -3


        //(Pawn) pawn).isFirstMove = 0;

        // pawn[-1] 6,1 = -4
        // pawn[-1] 4,0 = 1
        // pawn[-1] 4,1 = 0
        // pawn[-1] 3,1 = 0
        int result = ((Pawn) pawn_1).judgeMove(3,1);

        System.out.println("result"+result);

  Board test = new Board();
        Piece king1 = new King(1, new Position(2,2), test);
        Piece king2 = new King(-1, new Position(1,1), test);
        Piece king3 = new King(1, new Position(2,3), test);
        Piece queen = new Queen(-1, new Position(3,3), test);
        Piece queen1 = new Queen(1, new Position(3,1), test);

        Piece rook = new Rook(-1, new Position(1,2), test);

        test.putPieceOnBoard(king1.getRow(), king1.getCol(), king1);
        test.putPieceOnBoard(king2.getRow(), king2.getCol(), king2);
        test.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);
        test.putPieceOnBoard(queen.getRow(), queen.getCol(), queen);
        test.putPieceOnBoard(queen1.getRow(), queen1.getCol(), queen1);
        test.putPieceOnBoard(rook.getRow(), rook.getCol(), rook);
        test.printBoard();

        // 2,2 = 1
        // 1,3 = 0
        // 1,5 = 0
        // -1,0 = -1
        // 1,2 = -2
        // 1,0 = -3
        // 0,0 = -4
        int result = ((Rook) rook).judgeMove(1,3);

        System.out.println("result"+result);
 */

        /* test startingBoard()
        Board board = new Board();
        board.startingBoard();
        board.printBoard();
        */

        //test pawnThread();
        /*
        Board board = new Board();
        Piece kingwhite = new King(1, new Position(2,2), board);
        Piece kingblack = new King(-1, new Position(5,2), board);
        Piece king3 = new King(1, new Position(3,4), board);
        Piece p1 = new Pawn(1, new Position(4,1), board);
        Piece p2 = new Pawn(-1, new Position(4,3), board);
        Piece p3 = new Pawn(1, new Position(3,1), board);
        Piece p4 = new Pawn(-1, new Position(1,1), board);

        board.putPieceOnBoard(kingwhite.getRow(),kingwhite.getCol(),kingwhite);
        board.putPieceOnBoard(kingblack.getRow(),kingblack.getCol(),kingblack);
        board.putPieceOnBoard(king3.getRow(), king3.getCol(), king3);
        board.putPieceOnBoard(p1.getRow(), p1.getCol(), p1);
        board.putPieceOnBoard(p2.getRow(), p2.getCol(), p2);
        board.putPieceOnBoard(p3.getRow(), p3.getCol(), p3);
        board.putPieceOnBoard(p4.getRow(), p4.getCol(), p4);

        board.printBoard();
        // true
        System.out.println(board.pawnThread(-1*kingblack.getColor(),kingblack.getRow(),kingblack.getCol()));
        // false
        System.out.println(board.pawnThread(-1*kingwhite.getColor(),kingwhite.getRow(),kingwhite.getCol()));
        // true
        System.out.println(board.pawnThread(-1*king3.getColor(),king3.getRow(),king3.getCol()));
        */

        //test kingThread();
        /*
        Board board = new Board();
        Piece king1 = new King(1, new Position(2,2), board);
        Piece king2 = new King(-1, new Position(5,2), board);

        Piece p1 = new King(1, new Position(3,2), board);
        Piece p2 = new King(-1, new Position(5,1), board);
        Piece p3 = new King(1, new Position(4,1), board);
        Piece p4 = new King(1, new Position(4,3), board);


        board.putPieceOnBoard(king1.getRow(),king1.getCol(),king1);
        board.putPieceOnBoard(king2.getRow(),king2.getCol(),king2);
        board.putPieceOnBoard(p1.getRow(), p1.getCol(), p1);
        board.putPieceOnBoard(p2.getRow(), p2.getCol(), p2);
        board.putPieceOnBoard(p3.getRow(), p3.getCol(), p3);
        board.putPieceOnBoard(p4.getRow(), p4.getCol(), p4);
        board.printBoard();
        //false
        System.out.println(board.kingThread(-1*king1.getColor(),king1.getRow(),king1.getCol()));
        //true
        System.out.println(board.kingThread(-1*king2.getColor(),king2.getRow(),king2.getCol()));
        //true
        System.out.println(board.kingThread(-1*p4.getColor(),p4.getRow(),p4.getCol()));
        //false
        System.out.println(board.kingThread(-1*p1.getColor(),p1.getRow(),p1.getCol()));
        */


        //test knightThread();
        /*
        Board board = new Board();
        Piece k1 = new King(1, new Position(2,2), board);
        Piece k2 = new King(-1, new Position(5,2), board);
        Piece k3 = new King(-1, new Position(6,4), board);

        Piece p1 = new Knight(-1, new Position(0,3), board);
        Piece p2 = new Knight(-1, new Position(5,1), board);
        Piece p3 = new Knight(1, new Position(4,1), board);
        Piece p4 = new Knight(1, new Position(4,4), board);


        board.putPieceOnBoard(k1.getRow(),k1.getCol(),k1);
        board.putPieceOnBoard(k2.getRow(),k2.getCol(),k2);
        board.putPieceOnBoard(k3.getRow(),k3.getCol(),k3);
        board.putPieceOnBoard(p1.getRow(), p1.getCol(), p1);
        board.putPieceOnBoard(p2.getRow(), p2.getCol(), p2);
        board.putPieceOnBoard(p3.getRow(), p3.getCol(), p3);
        board.putPieceOnBoard(p4.getRow(), p4.getCol(), p4);
        board.printBoard();
        //true
        System.out.println(board.knightThread(-1*k1.getColor(),k1.getRow(),k1.getCol()));
        //true
        System.out.println(board.knightThread(-1*k2.getColor(),k2.getRow(),k2.getCol()));
        //false
        System.out.println(board.knightThread(-1*k3.getColor(),k3.getRow(),k3.getCol()));
        */

        //test bishopQueenThread();
/*
        Board board = new Board();
        //board.bishopQueenThread(1, 3,4);

        Piece k1 = new King(1, new Position(2,2), board);
        Piece k2 = new King(-1, new Position(5,2), board);
        Piece k3 = new King(1, new Position(6,4), board);

        Piece p1 = new Bishop(-1, new Position(0,3), board);
        Piece p2 = new Bishop(-1, new Position(5,1), board);
        Piece p3 = new Bishop(1, new Position(3,0), board);
        Piece p4 = new Bishop(1, new Position(4,4), board);
        Piece p5 = new Bishop(-1, new Position(5,5), board);


        board.putPieceOnBoard(k1.getRow(),k1.getCol(),k1);
        board.putPieceOnBoard(k2.getRow(),k2.getCol(),k2);
        board.putPieceOnBoard(k3.getRow(),k3.getCol(),k3);
        board.putPieceOnBoard(p1.getRow(), p1.getCol(), p1);
        board.putPieceOnBoard(p2.getRow(), p2.getCol(), p2);
        board.putPieceOnBoard(p3.getRow(), p3.getCol(), p3);
        board.putPieceOnBoard(p4.getRow(), p4.getCol(), p4);
        board.putPieceOnBoard(p5.getRow(), p5.getCol(), p5);
        board.printBoard();
        //false
        System.out.println(board.bishopQueenThread(-1*k1.getColor(),k1.getRow(),k1.getCol()));
        //true
        System.out.println(board.bishopQueenThread(-1*k2.getColor(),k2.getRow(),k2.getCol()));
        //true
        System.out.println(board.bishopQueenThread(-1*k3.getColor(),k3.getRow(),k3.getCol()));
        */

        //test rookQueenThread

        /*
        Piece k1 = new King(1, new Position(2,2), board);
        Piece k2 = new King(1, new Position(5,2), board);
        Piece k3 = new King(-1, new Position(6,4), board);

        Piece p1 = new Rook(-1, new Position(0,3), board);
        Piece p2 = new Rook(-1, new Position(5,1), board);
        Piece p3 = new Rook(1, new Position(3,0), board);
        Piece p4 = new Rook(1, new Position(4,4), board);
        Piece p5 = new Rook(-1, new Position(5,5), board);


        board.putPieceOnBoard(k1.getRow(),k1.getCol(),k1);
        board.putPieceOnBoard(k2.getRow(),k2.getCol(),k2);
        board.putPieceOnBoard(k3.getRow(),k3.getCol(),k3);
        board.putPieceOnBoard(p1.getRow(), p1.getCol(), p1);
        board.putPieceOnBoard(p2.getRow(), p2.getCol(), p2);
        board.putPieceOnBoard(p3.getRow(), p3.getCol(), p3);
        board.putPieceOnBoard(p4.getRow(), p4.getCol(), p4);
        board.putPieceOnBoard(p5.getRow(), p5.getCol(), p5);
        board.printBoard();
        //false
        System.out.println(board.rookQueenThread(-1*k1.getColor(),k1.getRow(),k1.getCol()));
        //true
        System.out.println(board.rookQueenThread(-1*k2.getColor(),k2.getRow(),k2.getCol()));
        //true
        System.out.println(board.rookQueenThread(-1*k3.getColor(),k3.getRow(),k3.getCol()));
        */


    }
}
