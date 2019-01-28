import static java.lang.Character.*;


public class Pencil {
    private final int INITIAL_SHARPNESS;

    private int currentSharpness;
    private int currentLength;
    private int currentEraserDurability;
    private Integer lastEraseIndex;

    public Pencil(int initialSharpness, int initialLength, int initialEraserDurability) {
        this.INITIAL_SHARPNESS = initialSharpness;
        this.currentSharpness = initialSharpness;
        this.currentLength = initialLength;
        this.currentEraserDurability = initialEraserDurability;
    }

    public int getCurrentSharpness() {
        return currentSharpness;
    }

    public int getCurrentLength() {
        return currentLength;
    }

    public boolean isDull() {
        return currentSharpness < 1;
    }

    public String write(String input, String paper) {
        StringBuilder editingPaper = new StringBuilder(paper);

        for (char letter : input.toCharArray()) {
            char writtenChar = isDull() ? ' ' : letter;

            editingPaper.append(writtenChar);
            decrementSharpness(letter);
        }

        return editingPaper.toString();
    }

    public void sharpen() {
        if (currentLength < 1) {
            return;
        }

        resetSharpness();
        decrementLength();
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

        decrementEraserDurability(targetLength);
        updateLastEraseIndex(eraserStartIndex);

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

        updateLastEraseIndex(null);

        return startOfPaper + writtenEdit + endOfPaper;
    }

    private void decrementSharpness(char letter) {
        if (isWhitespace(letter)) {
            return;
        }

        int decrementAmount = isUpperCase(letter) ? 2 : 1;

        currentSharpness -= decrementAmount;
    }

    private void resetSharpness() {
        currentSharpness = INITIAL_SHARPNESS;
    }

    private void decrementLength() {
        currentLength -= 1;
    }

    private void decrementEraserDurability(int amount) {
        currentEraserDurability -= amount;
    }

    private void updateLastEraseIndex(Integer index) {
        lastEraseIndex = index;
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
