import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;


public class PencilTest {
    private Pencil pencil;
    private String blankPaper;

    @Before
    public void setUp() {
        this.pencil = new Pencil(50);
        this.blankPaper = "";
    }

    @Test
    public void pencilShouldWriteHelloWhenPassedHello() {
        String paper = pencil.write("Hello", blankPaper);

        assertEquals("Hello", paper);
    }

    @Test
    public void pencilShouldAppendNewTextToExistingText() {
        String paper = pencil.write("Hello", blankPaper);
        String editedPaper = pencil.write(" World!", paper);

        assertEquals("Hello World!", editedPaper);
    }

    @Test
    public void pencilCanOnlyWriteAsLongAsItIsSharp() {
        Pencil pencil = new Pencil(3);
        String paper = pencil.write("word", blankPaper);

        assertEquals("wor ", paper);
    }
}
