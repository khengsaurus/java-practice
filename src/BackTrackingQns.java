import java.util.*;

public class BackTrackingQns {
    //    47. Permutations II
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        backtrack47(nums, new boolean[nums.length], res, new ArrayDeque<>());
        return res;
    }

    public void backtrack47(int[] nums, boolean[] taken, List<List<Integer>> res, ArrayDeque<Integer> curr) {
        if (curr.size() == nums.length) {
            res.add(new ArrayList<>(curr));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (taken[i]) continue;
            if (i == 0 || nums[i] != nums[i - 1] || !taken[i - 1]) {
                taken[i] = true;
                curr.offerLast(nums[i]);
                backtrack47(nums, taken, res, curr);
                curr.pollLast();
                taken[i] = false;
            }
        }
    }

    //    216. Combination Sum III
    List<List<Integer>> res216;
    ArrayDeque<Integer> curr216;

    public List<List<Integer>> combinationSum3(int k, int n) {
        res216 = new ArrayList<>();
        curr216 = new ArrayDeque<>();
        if (n == 45 && k == 9) res216.add(new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)));
        if (n > 1 && n < 45) backtrack216(k, n, 1);
        return res216;
    }

    public void backtrack216(int k, int n, int start) {
        if (n == 0 && k == 0) res216.add(new ArrayList<>(curr216));
        while (start < 10 && k > 0) {
            if (start > n) break;
            curr216.addLast(start);
            backtrack216(k - 1, n - start, start + 1);
            curr216.removeLast();
            start++;
        }
    }

    //    40. Combination Sum II
    List<List<Integer>> res40;

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        res40 = new ArrayList<>();
        backtrack40(candidates, new ArrayList<>(), 0, target);
        return res40;
    }

    public void backtrack40(int[] nums, List<Integer> curr, int start, int target) {
        if (target == 0) res40.add(new ArrayList<>(curr));
        if (start >= nums.length) return;
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue;
            if (nums[i] <= target) {
                curr.add(nums[i]);
                backtrack40(nums, curr, i + 1, target - nums[i]);
                curr.remove(curr.size() - 1);
            }
        }
    }

    //    131. Palindrome Partitioning
    List<List<String>> res131;
    ArrayList<String> curr131;

    public List<List<String>> partition(String s) {
        res131 = new ArrayList<>();
        curr131 = new ArrayList<>();
        backtrack131(s, 0);
        return res131;
    }

    public void backtrack131(String s, int start) {
        if (curr131.size() > 0 && start >= s.length()) {
            res131.add(new ArrayList<>(curr131));
        }
        for (int i = start; i < s.length(); i++) {
            if (isPalindrome(s, start, i)) {
                curr131.add(s.substring(start, i + 1));
                backtrack131(s, i + 1);
                curr131.remove(curr131.size() - 1);
            }
        }
    }

    public boolean isPalindrome(String str, int l, int r) {
        if (l == r) return true;
        while (l < r) if (str.charAt(l++) != str.charAt(r--)) return false;
        return true;
    }

    //    51. N-Queens
    private boolean[] usedCols;
    private boolean[] pDiag;
    private boolean[] nDiag;
    private List<List<String>> res51;

    public List<List<String>> solveNQueens(int n) {
        usedCols = new boolean[n];
        pDiag = new boolean[n * 2];
        nDiag = new boolean[n * 2];
        res51 = new ArrayList<List<String>>();
        dfs(new ArrayList<String>(), 0, n);
        return res51;
    }

    private void dfs(List<String> list, int r, int n) {
        if (r == n) {
            res51.add(new ArrayList<>(list));
            return;
        }
        for (int c = 0; c < n; c++) {
            if (usedCols[c] || pDiag[r + c] || nDiag[r - c + n]) continue;

            char[] charArray = new char[n];
            Arrays.fill(charArray, '.');
            charArray[c] = 'Q';
            String rowString = new String(charArray);

            list.add(rowString);
            usedCols[c] = true;
            pDiag[r + c] = true;
            nDiag[r - c + n] = true;

            dfs(list, r + 1, n);

            list.remove(list.size() - 1);
            usedCols[c] = false;
            pDiag[r + c] = false;
            nDiag[r - c + n] = false;
        }
    }

    //    22. Generate Parentheses
    public List<String> list22;

    public List<String> generateParenthesis(int n) {
        list22 = new ArrayList<>();
        backtrack22(n, 0, "");
        return list22;

    }

    public void backtrack22(int toOpen, int toClose, String str) {
        if (toOpen == 0 && toClose == 0) list22.add(str);
        if (toOpen > 0) backtrack22(toOpen - 1, toClose + 1, str + "(");
        if (toClose > 0) backtrack22(toOpen, toClose - 1, str + ")");
    }

    //    17. Letter Combinations of a Phone Number

    Map<Character, char[]> phoneChars;
    List<String> combos;

    public List<String> letterCombinations(String digits) {
        phoneChars = new HashMap<>();
        phoneChars.put('2', new char[]{'a', 'b', 'c'});
        phoneChars.put('3', new char[]{'d', 'e', 'f'});
        phoneChars.put('4', new char[]{'g', 'h', 'i'});
        phoneChars.put('5', new char[]{'j', 'k', 'l'});
        phoneChars.put('6', new char[]{'m', 'n', 'o'});
        phoneChars.put('7', new char[]{'p', 'q', 'r', 's'});
        phoneChars.put('8', new char[]{'t', 'u', 'v'});
        phoneChars.put('9', new char[]{'w', 'x', 'y', 'z'});
        combos = new ArrayList<>();
        backtrack17(digits, 0, new StringBuilder());
        return combos;
    }

    private void backtrack17(String digits, int i, StringBuilder sb) {
        if (i == digits.length()) {
            combos.add(sb.toString());
            return;
        }
        for (char c : phoneChars.get(digits.charAt(i))) {
            sb.append(c);
            backtrack17(digits, i + 1, sb);
            sb.delete(i, i + 1);
        }
    }

    //    784. Letter Case Permutation
    public List<String> letterCasePermutation(String s) {
        Backtrack784 ans = new Backtrack784(s);
        return ans.getList();
    }

    class Backtrack784 {
        private List<String> list = new ArrayList<>();

        public Backtrack784(String s) {
            backtrack784(s.toCharArray(), 0);
        }

        public List<String> getList() {
            return this.list;
        }

        void backtrack784(char[] chars, int i) {
            if (i == chars.length) {
                list.add(new String(chars));
                return;
            }
            if (Character.isDigit(chars[i])) {
                backtrack784(chars, i + 1);
            } else {
                chars[i] = Character.toUpperCase(chars[i]);
                backtrack784(chars, i + 1);
                chars[i] = Character.toLowerCase(chars[i]);
                backtrack784(chars, i + 1);
            }
        }
    }

    //    46. Permutations
    public List<List<Integer>> permute(int[] nums) {
        Backtrack46 ans = new Backtrack46(nums);
        return ans.getResult();
    }

    class Backtrack46 {
        private List<List<Integer>> result;

        public Backtrack46(int[] nums) {
            result = new LinkedList<>();
            Deque<Integer> combo = new LinkedList<>();
            boolean[] used = new boolean[nums.length];
            backtrack(nums, combo, used);
        }

        public List<List<Integer>> getResult() {
            return this.result;
        }

        public void backtrack(int[] nums, Deque<Integer> combo, boolean[] used) {
            if (combo.size() == nums.length) {
                result.add(new ArrayList<>(combo));
                return;
            }
            for (int i = 0; i < nums.length; i++) {
                if (used[i]) continue;
                used[i] = true;
                combo.offerLast(nums[i]);
                backtrack(nums, combo, used);
                used[i] = false;
                combo.pollLast();
            }
        }
    }

    /**
     * 77. Combinations
     * Backtracking + Decision tree
     * Base: n, Height of decision tree: k
     * Upperbound: O(n^k)
     */
    public List<List<Integer>> combine(int n, int k) {
        Backtrack77 ans = new Backtrack77(n, k);
        return ans.getResult();
    }

    class Backtrack77 {
        private List<List<Integer>> result;

        public Backtrack77(int n, int k) {
            this.result = new ArrayList<>();
            Deque<Integer> comb = new LinkedList<>();
            for (int i = 1; i <= n; i++) {
                comb.offerLast(i);
                backtrack(i + 1, n, k - 1, comb);
                comb.pollLast();
            }
        }

        public List<List<Integer>> getResult() {
            return this.result;
        }

        private void backtrack(int start, int n, int remaining, Deque<Integer> currComb) {
            if (remaining == 0) {
                result.add(new LinkedList<>(currComb)); // create new obj here, else this will be a reference
                return;
            }
            for (int i = start; i <= n - remaining + 1; i++) { // the decision tree
                currComb.offerLast(i);
                backtrack(i + 1, n, remaining - 1, currComb); // explore all options with i
                currComb.pollLast(); // remove i
            }
        }
    }

    //    78. Subsets
    List<List<Integer>> res78;

    public List<List<Integer>> subsets(int[] nums) {
        res78 = new ArrayList<>();
        backtrack78(nums, new LinkedList<>(), 0);
        return res78;
    }

    private void backtrack78(int[] nums, Deque<Integer> path, int curr) {
        if (curr == nums.length) {
            res78.add(new ArrayList<>(path));
            return;
        }
        backtrack78(nums, path, curr + 1);
        path.offerLast(nums[curr]);
        backtrack78(nums, path, curr + 1);
        path.pollLast();
    }

    //    39. Combination Sum
    public List<List<Integer>> combinationSum(int[] nums, int target) {
        List<List<Integer>> combos = new ArrayList<>();
        backtrack39(nums, target, 0, 0, combos, new LinkedList<>());
        return combos;
    }

    public void backtrack39(int[] nums, int target, int start, int curSum, List<List<Integer>> combos, Deque<Integer> combo) {
        if (curSum == target) combos.add(new ArrayList<>(combo));
        while (start < nums.length) {
            if (nums[start] <= target - curSum) {
                combo.offerLast(nums[start]);
                backtrack39(nums, target, start, curSum + nums[start], combos, combo);
                combo.pollLast();
            }
            start++;
        }
    }
}
