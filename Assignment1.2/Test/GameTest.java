import Main.*;
import java.util.Deque;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GameTest{
    @Test
    public void Game() {
        Game game = new Game(true);
        Game game2 = new Game(false);
    }

}