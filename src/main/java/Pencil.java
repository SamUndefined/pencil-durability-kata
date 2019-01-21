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

            this.initialDurability -= 1;
        }

        return editingPaper.toString();
    }
}
