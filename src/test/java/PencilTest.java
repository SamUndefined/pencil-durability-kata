import org.junit.Test;
import static junit.framework.TestCase.assertEquals;


public class PencilTest {
    @Test
    public void pencilShouldWriteHelloWhenPassedHello() {
        Pencil pencil = new Pencil();
        String paper = pencil.write("Hello");

        assertEquals("Hello", paper);
    }
}
