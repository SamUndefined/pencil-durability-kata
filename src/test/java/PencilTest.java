import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;


public class PencilTest {
    private Pencil pencil;
    private Pencil fourDurabilityPencil;
    private String blankPaper;

    @Before
    public void setUp() {
        this.pencil = new Pencil(50);
        this.fourDurabilityPencil = new Pencil(4);
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
        String paper = fourDurabilityPencil.write("words", blankPaper);

        assertEquals("word ", paper);
    }

    @Test
    public void pencilSharpnessShouldDecreaseMoreForCapitalLetters() {
        String paper = fourDurabilityPencil.write("HALF", blankPaper);

        assertEquals("HA  ", paper);
    }

    @Test
    public void pencilSharpnessShouldNotDecreaseForBlankCharacters() {
        String paper = fourDurabilityPencil.write("  \nFour  ", blankPaper);

        assertEquals("  \nFou   ", paper);
    }
}
