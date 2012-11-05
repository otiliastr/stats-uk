package dict;

public class Trie {

    private Node root;

    public Trie() {
        root = new Node();
    }

    private class Node {
        Node[] child;
        boolean isWord;

        Node() {
            child = new Node[27];
            for (int i = 0; i < 27; ++i)
                child[i] = null;
            isWord = false;
        }
    }

    public void insert(String word) {
        insert(word, root);
    }

    public void insert(String word, Node node) {
        // we've reached the end of the word
        if (word.equals("")) {
            node.isWord = true;
            return;
        }
        // Try to insert at node corresponding to first character
        char first = word.charAt(0);
        int position;
        if (first != ' ')
            position = first - 'a'; // 0-25 for alphabet characters
        else position = 26;         // 26 for space
        if (node.child[position] == null) 
            node.child[position] = new Node();
        // remove first character and continue
        insert(word.substring(1), node.child[position]);
    }

    public boolean contains(String word) {
        return contains(word, root);
    }

    public boolean contains(String word, Node node) {
        // if we've reached the end of the word and the node
        // contains a word, return true
        if (word.equals("") && node.isWord)
            return true;
        char first = word.charAt(0);
        int position = first - 'a';
        if (node.child[position] == null)
            return false;
        // recurse without the first character on the tree corresponding
        // to the first character of the string
        return contains(word.substring(1), node.child[position]);
    }
}
