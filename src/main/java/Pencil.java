import static java.lang.Character.isUpperCase;


public class Pencil {
    private int initialDurability;

    public Pencil(int initialDurability) {
        this.initialDurability = initialDurability;
    }

    public String write(String input, String paper) {
        StringBuilder editingPaper = new StringBuilder(paper);

        for (char letter : input.toCharArray()) {
            boolean dull = initialDurability == 0;

            if (dull) {
                editingPaper.append(' ');
                continue;
            }

            editingPaper.append(letter);

            decrementDurability(letter);
        }

        return editingPaper.toString();
    }

    public void decrementDurability(char letter) {
        int decrementAmount = isUpperCase(letter) ? 2 : 1;

        this.initialDurability -= decrementAmount;
    }
}
