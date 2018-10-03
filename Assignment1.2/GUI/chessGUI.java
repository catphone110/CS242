package GUI;
import Main.*;
import java.util.Deque;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class chessGUI extends JFrame {

    private JFrame frame;
    private BoardPanel boardPanel;
    private static int chessSize = 70;
    private Game chessGame;
    private Color darkColor = new Color(255, 242, 230);
    private Color brightColor = new Color(217, 172, 120);
    boolean CUSTOMIZED;
    private static Dimension OUTER_FRAME_DIMENSION = new Dimension(chessSize*8 , chessSize*8 );
    private TilePanel sourceTile;
    private TilePanel destinationTile;
    private Piece selectedMovePiece;

    private Piece LastMovedPiece;
    private Piece LastRemovedPiece;
    private Position LastPosition;


    private String whiteName;
    private String blackName;
    private int whiteScore;
    private int blackScore;
    /**
     * Constructor of a chess board GUI.
     * */
    public chessGUI(boolean CUSTOMIZED){

        this.frame = new JFrame("Chess 1.2");
        this.chessGame = new Game(CUSTOMIZED);
        this.CUSTOMIZED = CUSTOMIZED;

        frame.setSize(OUTER_FRAME_DIMENSION);

        frame.setLayout(new BorderLayout());
        boardPanel = new BoardPanel(chessGame.getBoard());

        // menu bar
        constructGameMenuBar();

        // add tiles
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        whiteScore = 0;
        blackScore = 0;
        whiteName = JOptionPane.showInputDialog("Enter white player's name: ");
        //blackName = JOptionPane.showInputDialog("Enter Black player's name: ");
    }


    private void constructGameMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = createGameJMenu("Game");
        JMenu moveMenu = createActionJMenu("Action");
        menuBar.add(moveMenu);
        menuBar.add(gameMenu);
        /*没有这个set就不会显示create好的JMenuBar*/
        frame.setJMenuBar(menuBar);
    }

    private JMenu createActionJMenu(String name){
        JMenu menu = new JMenu(name);
        JMenuItem button = new JMenuItem("Import File");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Import game");
            }
        });
        menu.add(button);

        button = new JMenuItem("Save");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save game");
            }
        });
        menu.add(button);
        return menu;
    }

    private JMenu createGameJMenu(String name){
        JMenu menu = new JMenu(name);

        JMenuItem newGame, undo, forfeit, exist;
        newGame = new JMenuItem("New Game");
        newGame.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int ret = JOptionPane.showConfirmDialog(
                            null,
                            "Sure want to restart a new game?",
                            "",
                            JOptionPane.YES_NO_OPTION);
                    if (ret==0) {
                        frame.dispose();
                        frame = new JFrame("Chess 1.2");
                        chessGame = new Game(CUSTOMIZED);
                        frame.setSize(OUTER_FRAME_DIMENSION);
                        frame.setLayout(new BorderLayout());
                        boardPanel = new BoardPanel(chessGame.getBoard());
                        constructGameMenuBar();
                        frame.add(boardPanel, BorderLayout.CENTER);
                        frame.setResizable(false);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setVisible(true);

                        whiteScore = 0;
                        blackScore = 0;

                        sourceTile = null;
                        destinationTile = null;
                        selectedMovePiece = null;
                    }
                }
         });
        menu.add(newGame);

        undo = new JMenuItem("Undo Move");
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });
        menu.add(undo);

        forfeit = new JMenuItem("Forfeit");

        forfeit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ret = JOptionPane.showConfirmDialog(
                        null,
                        "Sure want to Forfeit?",
                        "",
                        JOptionPane.YES_NO_OPTION);
                if (ret==0){
                    JOptionPane.showMessageDialog(boardPanel,
                             whiteName +" Score: " + whiteScore +". \n Black Score: "+blackScore+".", "Forfeit",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        menu.add(forfeit);


        exist = new JMenuItem("Exist");
        exist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ret = JOptionPane.showConfirmDialog(
                        null,
                        "Sure want to exist?",
                        "Exist",
                        JOptionPane.YES_NO_OPTION);
                if (ret ==0 ) {
                    System.out.println("GAME END");
                    System.exit(0);
                }
            }
        });
        menu.add(exist);
        return menu;
    }


    private class BoardPanel extends JPanel{
        List<TilePanel> boardTiles;
        Board board;
        BoardPanel(Board gameBoard){
            super(new GridLayout(8,8));
            board = gameBoard;
            boardTiles = new ArrayList<TilePanel>();
            setLocation(0,0);

            for (int i = 0; i<8*8; i++){
                TilePanel tilePanel = new TilePanel(this, new Position(i/8, i%8));

                boardTiles.add(tilePanel);
                this.add(tilePanel);
            }

            setPreferredSize(new Dimension(chessSize*8, chessSize*8));
            validate();
        }
        private void drawBoard(){
            removeAll();
            for (final TilePanel tile : boardTiles) {
                //boolean tile.piecePosition
                tile.drawTile(false);
                add(tile);
            }
            validate();
            repaint();
        }

        private TilePanel getTile(Position position){
            return boardTiles.get(position.getRow()*8+position.getCol());
        }

        private Piece getPieceTileboard(Position position){
            return board.getPiece(position.getRow(), position.getCol());
        }

    }

    private void undo(){
        System.out.println("Undo");
        System.out.println("LastMovedPiece "+LastMovedPiece.getClass()+" "+LastMovedPiece.getRow() +" "+LastMovedPiece.getCol());
        System.out.println("LastRemovedPiece is null"+(LastRemovedPiece==null));
        System.out.println("LastPosition "+LastPosition.getRow() +" "+LastPosition.getCol());

        boardPanel.board.undoMovePiece(LastMovedPiece, LastRemovedPiece, LastPosition);
        //undoMovePiece(Piece piece, Piece oldPiece, Position lastPosition);
        if (LastMovedPiece.getColor() == 1) whiteScore-= 2;
        else blackScore -= 2;

        boardPanel.board.printBoard();
        boardPanel.drawBoard();
        chessGame.flipTurn();

        LastMovedPiece = null;
        LastRemovedPiece = null;
        LastPosition =null;

    }

    private class TilePanel extends JPanel{
        public Position tilePosition;
        public Position piecePosition;
        TilePanel (BoardPanel boardPanel, Position tilePosition){
            super(new GridBagLayout());
            this.tilePosition = tilePosition;
            this.piecePosition = new Position(7-tilePosition.getRow(),tilePosition.getCol());
            setPreferredSize(new Dimension(chessSize, chessSize));
            setBackgroundColor(tilePosition.getRow(), tilePosition.getCol());
            displayChess(convertPosTtoB(tilePosition));

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(SwingUtilities.isLeftMouseButton(e)){
                        // first click
                        if(sourceTile == null){
                            sourceTile = boardPanel.getTile(tilePosition);
                            selectedMovePiece = boardPanel.getPieceTileboard(piecePosition);
                            if (selectedMovePiece == null || selectedMovePiece.getColor()!=chessGame.getTurn()){
                                if(selectedMovePiece != null){
                                    String playerTurn = (chessGame.getTurn()==1)?"WHITE":"BLACK";
                                    System.out.println(playerTurn+" player's turn.");
                                }
                                sourceTile  = null;
                            }

                            else{
                                Deque<Position> positionQueue = boardPanel.board.getPotentialMove(selectedMovePiece);
                                LastPosition = piecePosition;
                                    // TODO PUT ALL VALID MOVES
                            }
                            System.out.println("sourceTile  , selected to be " +tilePosition.getRow()+tilePosition.getCol());
                            if(selectedMovePiece!=null){System.out.println("selected piece is a "+selectedMovePiece.getClass());}
                        }
                        // second click
                        else{
                            destinationTile = boardPanel.getTile(tilePosition);

                            int validMove = boardPanel.board.tryMove(selectedMovePiece, piecePosition);
                            /*
                            * Start moving piece.
                            * change scores.
                            * */
                            if (validMove >= 0){
                                LastRemovedPiece = boardPanel.board.movePiece(selectedMovePiece, piecePosition);
                                if(LastRemovedPiece!=null) System.out.println(LastRemovedPiece.getClass());
                                if(chessGame.getTurn() == 1){
                                    if (validMove ==1) whiteScore +=2;
                                    else whiteScore++;
                                }
                                else {
                                    if (validMove ==1) blackScore +=2;
                                    else blackScore++;
                                }

                                if(LastRemovedPiece instanceof King){
                                    JOptionPane.showMessageDialog(boardPanel,
                                            "Game Over: Player " + ((chessGame.getTurn()==1)?"WHITE":"BLACK")+" WIN. \n "+ whiteName +" Score: " + whiteScore +". \n Black Score: "+blackScore+".", "Game Over",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    boardPanel.setFocusable(false);

                                }
                                LastMovedPiece = selectedMovePiece;
                                //print board
                                boardPanel.board.printBoard();

                                boardPanel.drawBoard();

                                //CHECK MESSAGE
                                if(boardPanel.board.isChecked(1, boardPanel.board.getKingPosition(1))){

                                    chessGame.setWhiteOnCheck();
                                }
                                else{
                                    chessGame.uncheckWhite();
                                }
                                if(boardPanel.board.isChecked(-1, boardPanel.board.getKingPosition(-1))){

                                    chessGame.setBlackOnCheck();
                                }
                                else{
                                    chessGame.uncheckBlack();
                                }
                                /*
                                * Flip sides
                                * */
                                chessGame.flipTurn();
                                String playerTurn = (chessGame.getTurn()==1)?"WHITE":"BLACK";
                                System.out.println(playerTurn+" player's turn.");

                                //TODO CHECKMATE CHECK
                                boolean checkmate = false;
                                Position kingPos = boardPanel.board.getKingPosition(1);
                                if(boardPanel.board.checkMate(boardPanel.board.getPiece(kingPos.getRow(), kingPos.getCol()))){
                                    checkmate = true;
                                    JOptionPane.showMessageDialog(boardPanel,
                                            "White CHECKMATED!  \n "+ whiteName +" Score: " + whiteScore +". \n Black Score: "+blackScore+".", "GAME OVER: Black WIN!",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                                kingPos = boardPanel.board.getKingPosition(-1);
                                if(boardPanel.board.checkMate(boardPanel.board.getPiece(kingPos.getRow(), kingPos.getCol()))){
                                    checkmate = true;
                                    JOptionPane.showMessageDialog(boardPanel,
                                            "Black CHECKMATED!  \n "+ whiteName +" Score: " + whiteScore +". \n Black Score: "+blackScore+".", "GAME OVER: White WIN!",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }

                                //TODO stalemate CHECK
                                Position kingPos_W = boardPanel.board.getKingPosition(1);
                                Position kingPos_B = boardPanel.board.getKingPosition(-1);
                                if((boardPanel.board.staleMate(boardPanel.board.getPiece(kingPos_W.getRow(), kingPos_W.getCol()))
                                ||boardPanel.board.staleMate(boardPanel.board.getPiece(kingPos_B.getRow(), kingPos_B.getCol()))
                                )&&(!checkmate)){
                                    JOptionPane.showMessageDialog(boardPanel,
                                            "STALEMATE \n White Score: " + whiteScore +". \n Black Score: "+blackScore+".", "GAME OVER: TIE!!",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }

                            }

                            System.out.println("vaildMove == " + validMove);
                            //System.out.println("destination tile == "+tilePosition.getRow() + tilePosition.getCol());

                            sourceTile = null;
                            destinationTile = null;
                            selectedMovePiece = null;
                        }
                    }
                }
                @Override
                public void mousePressed(MouseEvent e) {
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                }
                @Override
                public void mouseExited(MouseEvent e) {
                }
            });

        }

        private void drawTile(boolean diaplayVaild){
            setBackgroundColor(tilePosition.getRow(), tilePosition.getCol());
            displayChess(convertPosTtoB(tilePosition));

            if(diaplayVaild) displayValidMove();

            validate();
            repaint();
        }

        private void setBackgroundColor(int row, int col){
            if((row + col)%2 == 0){
                setBackground(brightColor);
            }
            else setBackground(darkColor);
        }

        private Position convertPosTtoB(Position tilePosition) {
            Position boardPosition = new Position(7-tilePosition.getRow(), tilePosition.getCol());
            return boardPosition;
        }

        private void displayChess(Position position){
            this.removeAll();
            Piece piece = chessGame.getBoard().getPiece(position.getRow(), position.getCol());
            if (piece==null)return;
            String className = piece.getClass().getSimpleName().toString();
            StringBuffer imageName = new StringBuffer(className);
            if (piece.getColor() == 1){
                imageName.append("W.png");
            }
            else imageName.append("B.png");
            try{
                final BufferedImage icon = ImageIO.read(new File("src/Image/"+imageName.toString()));
                add(new JLabel(new ImageIcon(icon)));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

        private void displayValidMove(){
            this.removeAll();

            try{
                final BufferedImage icon = ImageIO.read(new File("src/Image/vaildMove.png"));
                add(new JLabel(new ImageIcon(icon)));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }//end TilePanel

}
