import Main.*;
import java.lang.Object;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PositionTest{

    @Test
    public void testChangePosition() {
        Position k = new Position(2,3);
        k.changePosition(2,4);
    }
}