import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;


public class PencilTest {
    private Pencil pencil;
    private Pencil fourDurabilityPencil;
    private String blankPaper;

    @Before
    public void setUp() {
        this.pencil = new Pencil(50, 50, 50);
        this.fourDurabilityPencil = new Pencil(4, 50, 50);
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

    @Test
    public void pencilShouldRegainDurabilityWhenSharpened() {
        fourDurabilityPencil.write("Dull", blankPaper);

        assertTrue(fourDurabilityPencil.isDull());

        fourDurabilityPencil.sharpen();

        assertEquals(4, fourDurabilityPencil.getCurrentDurability());
    }

    @Test
    public void pencilLosesLengthAsItIsSharpened() {
        pencil.sharpen();

        assertEquals(49, pencil.getCurrentLength());
    }

    @Test
    public void pencilCannotBeSharpenedOnceItIsOutOfLength() {
        Pencil pencil = new Pencil(1, 0, 50);
        pencil.write("blah", blankPaper);
        pencil.sharpen();

        assertTrue(pencil.isDull());
    }

    @Test
    public void pencilErasesTheTextItIsGivenToErase() {
        String paper = "Word are not.";
        String editedPaper = pencil.erase("Word", paper);

        assertEquals("     are not.", editedPaper);
    }

    @Test
    public void pencilErasesTheLastOccurrenceOfTheTextItIsGivenToErase() {
        String paper = "Hear ye, hear ye.";
        String editedPaper = pencil.erase("ye", paper);

        assertEquals("Hear ye, hear   .", editedPaper);
    }

    @Test
    public void eraserWillStopErasingOnceDegraded() {
        Pencil pencil = new Pencil(50, 50, 4);
        String paper = "Some things to erase..";
        String editedPaper = pencil.erase("erase", paper);

        assertEquals("Some things to e    ..", editedPaper);
    }

    @Test
    public void pencilShouldBeAbleToEditErasedText() {
        String paper = "An apple a day keeps the doctor away";
        String editedPaper = pencil.erase("apple", paper);
        String finishedPaper = pencil.edit("onion", editedPaper);

        assertEquals("An onion a day keeps the doctor away", finishedPaper);
    }

    @Test
    public void addingTooManyCharactersDuringAnEditCausesCollisionWithExistingNonWhiteSpaceCharacters() {
        String paper = "An apple a day keeps the doctor away";
        String editedPaper = pencil.erase("apple", paper);
        String finishedPaper = pencil.edit("artichoke", editedPaper);

        assertEquals("An artich@k@ay keeps the doctor away", finishedPaper);
    }
}
