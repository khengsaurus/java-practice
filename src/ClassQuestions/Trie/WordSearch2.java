package ClassQuestions.Trie;

import java.util.ArrayList;
import java.util.List;

//    212. Word Search II
public class WordSearch2 {
    static class WSTrieNode {
        WSTrieNode[] next = new WSTrieNode[26];
        String word;
    }

    public WSTrieNode buildTrie(String[] words) {
        WSTrieNode root = new WSTrieNode();
        for (String w : words) {
            WSTrieNode curr = root;
            for (char c : w.toCharArray()) {
                int i = c - 'a';
                if (curr.next[i] == null) curr.next[i] = new WSTrieNode();
                curr = curr.next[i];
            }
            curr.word = w;
        }
        return root;
    }

    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        WSTrieNode trieRoot = buildTrie(words);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs212(board, i, j, trieRoot, res);
            }
        }
        return res;
    }

    public void dfs212(char[][] board, int row, int col, WSTrieNode root, List<String> res) {
        char c = board[row][col];
        if (c == '#' || root.next[c - 'a'] == null) return;
        root = root.next[c - 'a'];
        if (root.word != null) { // found one
            res.add(root.word);
            root.word = null; // de-duplicate
        }

        board[row][col] = '#'; // backtrack
        if (row > 0) dfs212(board, row - 1, col, root, res);
        if (col > 0) dfs212(board, row, col - 1, root, res);
        if (row < board.length - 1) dfs212(board, row + 1, col, root, res);
        if (col < board[0].length - 1) dfs212(board, row, col + 1, root, res);
        board[row][col] = c;
    }
}
