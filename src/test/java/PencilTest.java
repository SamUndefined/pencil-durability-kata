import org.junit.Test;
import static junit.framework.TestCase.assertEquals;


public class PencilTest {
    @Test
    public void pencilShouldWriteHelloWhenPassedHello() {
        Pencil pencil = new Pencil();
        String paper = pencil.write("Hello", "");

        assertEquals("Hello", paper);
    }

    @Test
    public void pencilShouldAppendNewTextToExistingText() {
        Pencil pencil = new Pencil();
        String paper = pencil.write("Hello", "");
        String editedPaper = pencil.write(" World!", paper);

        assertEquals("Hello World!", editedPaper);
    }
}
