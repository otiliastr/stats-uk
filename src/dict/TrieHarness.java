package dict;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class TrieHarness {
    Trie trie;

    public static void main(String[] args) {
        TrieHarness harness = new TrieHarness();
        harness.run();
    }

    public void run() {
        trie = new Trie();
        readAndInsert();
        search();
    }

    public void readAndInsert() {
        BufferedReader buf = null;
        try {
            buf = new BufferedReader(new FileReader(
                        new File("dict.txt")));
        } catch(FileNotFoundException fnfe) {
            System.out.println("Couldn't find the file");
            fnfe.printStackTrace();
        }
        while (true) {
            String word = null;
            try {
                word = buf.readLine();
            } catch (IOException ioe) {
                System.out.println("Something went wrong while reading");
                ioe.printStackTrace();
            }
            if (word == null || word.equals("."))
                break;
            trie.insert(word);
        }
    }

    public void search() {
        String words[] = {"program", "aaron", "abeam", "abegg",
            "asdadssd", "daooo"};
        for (String word : words) {
            if (trie.contains(word))
                System.out.println(word);
        }
    }
}
