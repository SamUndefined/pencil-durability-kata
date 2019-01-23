import static java.lang.Character.isUpperCase;
import static java.lang.Character.isWhitespace;


public class Pencil {
    private final int initialDurability;
    private int currentDurability;
    private int currentLength;

    public Pencil(int initialDurability, int initialLength) {
        this.initialDurability = initialDurability;
        this.currentDurability = initialDurability;
        this.currentLength = initialLength;
    }

    public int getCurrentDurability() {
        return currentDurability;
    }

    public int getCurrentLength() {
        return currentLength;
    }

    public boolean isDull() {
        return currentDurability < 1;
    }

    public String write(String input, String paper) {
        StringBuilder editingPaper = new StringBuilder(paper);

        for (char letter : input.toCharArray()) {
            if (isDull()) {
                editingPaper.append(' ');
                continue;
            }

            editingPaper.append(letter);

            decrementDurability(letter);
        }

        return editingPaper.toString();
    }

    public void sharpen() {
        if (currentLength < 1) {
            return;
        }

        currentDurability = initialDurability;
        currentLength -= 1;
    }

    public String erase(String target, String paper) {
        int targetStartIndex = paper.lastIndexOf(target);

        if (targetStartIndex == -1) {
            return paper;
        }

        int targetLength = target.length();
        int targetEndIndex = targetStartIndex + targetLength;
        String emptySpace = " ".repeat(targetLength);
        StringBuilder editablePaper = new StringBuilder(paper);

        return editablePaper
                .replace(targetStartIndex, targetEndIndex, emptySpace)
                .toString();
    }

    private void decrementDurability(char letter) {
        if (isWhitespace(letter)) {
            return;
        }

        int decrementAmount = isUpperCase(letter) ? 2 : 1;

        currentDurability -= decrementAmount;
    }
}
