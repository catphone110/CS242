package GUI;
import Main.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import javax.imageio.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class chessGUI extends JFrame {

    private final JFrame frame;
    private final BoardPanel boardPanel;
    private static int chessSize = 70;
    private Game chessGame;
    private Color darkColor = new Color(255, 242, 230);
    private Color brightColor = new Color(217, 172, 120);

    /**
     * Constructor of a chess board GUI.
     * */
    public chessGUI(){
        this.frame = new JFrame("Chess 1.1");
        //this.boardPanel = new BoardPanel();
        this.chessGame = new Game();

        frame.setSize(new Dimension(chessSize*8, chessSize*8));

        frame.setLayout(new BorderLayout());
        boardPanel = new BoardPanel();

        // menu bar
        JMenu gameMenu = new JMenu("Game");
        JMenu moveMenu = new JMenu("Action");
        JMenuBar chessMenuBar = new JMenuBar();
        addMenuRestart(gameMenu);
        addMenuExist(gameMenu);
        addMenuUndoMove(moveMenu);
        chessMenuBar.add(gameMenu);
        chessMenuBar.add(moveMenu);
        frame.setJMenuBar(chessMenuBar);

        // add tiles
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void addMenuRestart(JMenu chessMenu){
        final JMenuItem button = new JMenuItem("New Game");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Restart a new game");
            }
        });
        chessMenu.add(button);
    }

    private void addMenuUndoMove(JMenu chessMenu){
        final JMenuItem button = new JMenuItem("Undo Move");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Undo");
            }
        });
        chessMenu.add(button);
    }

    private void addMenuExist(JMenu chessMenu){
        final JMenuItem button = new JMenuItem("Exist");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("GAME END");
                System.exit(0);
            }
        });
        chessMenu.add(button);
    }

    private class BoardPanel extends JPanel{
        List<TilePanel> boardTiles;
        BoardPanel(){
            super(new GridLayout(8,8));

            boardTiles = new ArrayList<TilePanel>();
            setLocation(0,0);
            //setBackground(new Color(255,255,255));
            for (int i = 0; i<8*8; i++){
                TilePanel tilePanel = new TilePanel(this, new Position(i/8, i%8));
                boardTiles.add(tilePanel);
                this.add(tilePanel);
            }

            setPreferredSize(new Dimension(chessSize*8, chessSize*8));
            validate();
        }
    }

    private class TilePanel extends JPanel{
        public Position position;
        TilePanel (BoardPanel boardPanel, Position position){
            super(new GridBagLayout());
            this.position = position;
            setPreferredSize(new Dimension(chessSize, chessSize));
            setBackgroundColor(position.getRow(), position.getCol());
            displayChess(convertPosTtoB(position));
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
            Piece piece = chessGame.getBoard().getPiece(position.getRow(), position.getCol());
            if (piece==null)return;
            this.removeAll();
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
    }//end TilePanel

}
