import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {

    @Test
    @Timeout(value = 22)
    @Disabled
    public void mainTest() {
        String[] args = {};
        try {
            Main.main(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
