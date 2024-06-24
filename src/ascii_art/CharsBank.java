package ascii_art;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CharsBank {
    private List<Character> charList = new ArrayList<>();

    public CharsBank() {
        initCharList();
    }

    private void initCharList() {
        // Add characters '0' to '9' to the list
        for (char ch = '0'; ch <= '9'; ch++) {
            this.charList.add(ch);
        }
    }

    public void printCharList() {
        // Sort the list in natural (ASCII) order
        Collections.sort(this.charList);

        // Build the output string with a space between each character
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.charList.size(); i++) {
            result.append(this.charList.get(i));
            if (i < this.charList.size() - 1) {
                result.append(' ');
            }
        }

        // Print the resulting string
        System.out.println(result.toString());
    }

    public void add(Character c) {
        if (!this.charList.contains(c)) {
            this.charList.add(c);
        }
    }

    public void remove(Character c) {
        this.charList.remove(c);
    }

    public int getSize() {
        return this.charList.size();
    }
}
