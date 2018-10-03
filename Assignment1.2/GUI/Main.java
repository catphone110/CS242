package GUI;



import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



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

        int ret = JOptionPane.showConfirmDialog(
                null,
                "Use Customise Pieces?",
                "",
                JOptionPane.YES_NO_OPTION);
        chessGUI game = new chessGUI(ret==0);

    }
}
