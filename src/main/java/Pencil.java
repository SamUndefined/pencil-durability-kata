import static java.lang.Character.*;


public class Pencil {
    private final int initialDurability;
    private int currentDurability;
    private int currentLength;
    private int currentEraserDurability;
    private Integer lastEraseIndex;

    public Pencil(int initialDurability, int initialLength, int initialEraserDurability) {
        this.initialDurability = initialDurability;
        this.currentDurability = initialDurability;
        this.currentLength = initialLength;
        this.currentEraserDurability = initialEraserDurability;
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
            char writtenChar = isDull() ? ' ' : letter;

            editingPaper.append(writtenChar);
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
        boolean cannotErase = currentEraserDurability < 1 || targetStartIndex == -1;

        if (cannotErase) {
            return paper;
        }

        int targetLength = target.length();
        boolean canEraseFullText = currentEraserDurability >= targetLength;
        int targetEndIndex = targetStartIndex + targetLength;
        int numCharactersErased = canEraseFullText ? targetLength : currentEraserDurability;
        int numCharactersNotErased = targetLength - currentEraserDurability;
        int eraserStartIndex = canEraseFullText ? targetStartIndex : targetStartIndex + numCharactersNotErased;
        String erasedText = " ".repeat(numCharactersErased);

        lastEraseIndex = eraserStartIndex;
        decrementEraserDurability(targetLength);

        return new StringBuilder(paper)
                .replace(eraserStartIndex, targetEndIndex, erasedText)
                .toString();
    }

    public String edit(String requestedEdit, String paper) {
        if (lastEraseIndex == null) {
            return paper;
        }

        int requestedEditLength = requestedEdit.length();
        String startOfPaper = paper.substring(0, lastEraseIndex);
        String partToOverwrite = paper.substring(lastEraseIndex, lastEraseIndex + requestedEditLength);
        String endOfPaper = paper.substring(lastEraseIndex + requestedEditLength);
        String actualEdit = buildActualEdit(requestedEdit, partToOverwrite);
        String writtenEdit = write(actualEdit, "");

        lastEraseIndex = null;

        return startOfPaper + writtenEdit + endOfPaper;
    }

    private void decrementDurability(char letter) {
        if (isWhitespace(letter)) {
            return;
        }

        int decrementAmount = isUpperCase(letter) ? 2 : 1;

        currentDurability -= decrementAmount;
    }

    private void decrementEraserDurability(int amount) {
        currentEraserDurability -= amount;
    }

    private String buildActualEdit(String requestedEdit, String partToOverwrite) {
        StringBuilder actualEdit = new StringBuilder();
        char[] requestedChars = requestedEdit.toCharArray();
        char[] existingChars = partToOverwrite.toCharArray();

        for (int index = 0; index < requestedEdit.length(); index++) {
            char existingChar = existingChars[index];
            char requestedChar = requestedChars[index];
            char actualCharToAdd = isWhitespace(existingChar) ? requestedChar : '@';

            actualEdit.append(actualCharToAdd);
        }

        return actualEdit.toString();
    }
}
