package ClassQuestions;

//    208. Implement Trie (Prefix Tree)
class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        int level, index, len = word.length();
        TrieNode pCrawl = root;
        for (level = 0; level < len; level++) {
            index = word.charAt(level) - 'a';
            if (pCrawl.children[index] == null) pCrawl.children[index] = new TrieNode();
            pCrawl = pCrawl.children[index];
        }
        pCrawl.isEndOfWord = true;
    }

    public boolean search(String word) {
        int level, len = word.length(), index;
        TrieNode pCrawl = root;
        for (level = 0; level < len; level++) {
            index = word.charAt(level) - 'a';
            if (pCrawl.children[index] == null) return false;
            pCrawl = pCrawl.children[index];
        }
        return pCrawl != null;
    }

    public boolean startsWith(String prefix) {
        int level, len = prefix.length(), index;
        TrieNode pCrawl = root;
        for (level = 0; level < len; level++) {
            index = prefix.charAt(level) - 'a';
            if (pCrawl.children[index] == null) return false;
            pCrawl = pCrawl.children[index];
        }
        return !pCrawl.isEndOfWord;
    }

    static class TrieNode {
        TrieNode[] children;
        boolean isEndOfWord;

        TrieNode() {
            isEndOfWord = false;
            children = new TrieNode[26];
        }
    }
}