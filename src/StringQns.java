import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringQns {
    public static void main(String[] args) {

    }

    //    49. Group Anagrams
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String keyStr = String.valueOf(chars);
            map.computeIfAbsent(keyStr, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }


    //    763. Partition Labels
    public static List<Integer> partitionLabels(String s) {
        int sLen = s.length();
        if (s.charAt(0) == s.charAt(sLen - 1)) return new ArrayList<>(Arrays.asList(sLen));

        int[] last = new int[26];
        for (int i = 0; i < sLen; i++) last[s.charAt(i) - 'a'] = i;

        List<Integer> list = new ArrayList<>();
        int nextReq = last[s.charAt(0) - 'A'];
        int count = 0;
        for (int i = 0; i < sLen; i++) {
            count++;
            nextReq = Math.max(nextReq, last[s.charAt(i) - 'A']);
            if (i == nextReq) {
                list.add(count);
                count = 0;
            }
        }
        if (count > 0) list.add(count);

        return list;
    }


//    public static List<Integer> partitionLabelsBetter(String s) {
//        int sLen = s.length();
//        int[] last = new int[26];
//        for (int i = 0; i < sLen; i++) {
//            last[s.charAt(i) - 'a'] = i;
//        }
//        int left = 0;
//        List<Integer> list = new ArrayList<>();
//
//        while (left < sLen) {
//            int right = last[s.charAt(left) - 'a'];
//            for (int i = left; i < right; i++) {
//                right = Math.max(right, last[s.charAt(i) - 'a']);
//            }
//            list.add(right - left + 1);
//            left = right + 1;
//        }
//        return list;
//    }
//

    //    424 - Longest repeating character replacement
    public static int characterReplacement(String s, int k) {
        if (k >= s.length()) return s.length();
        int l = 0, r = 0, max = 0, maxRepeatedCharsInWindow = 0;
        int[] memo = new int[26];
        while (r < s.length()) {
            int newCharCount = ++memo[s.charAt(r) - 'A'];
            if (newCharCount > maxRepeatedCharsInWindow) maxRepeatedCharsInWindow = newCharCount;
            if (r - l + 1 - maxRepeatedCharsInWindow > k) memo[s.charAt(l++) - 'A']--;
            max = Math.max(max, r - l + 1);
            r++;
        }
        return max;
    }

    //    438. Find All Anagrams in a String TODO: not my solution
    public static List<Integer> findAnagrams(String longer, String shorter) {
        int[] freq = new int[26];
        for (char c : shorter.toCharArray()) freq[c - 'a']++;

        int count = shorter.length(), start = 0, end = 0;
        List<Integer> res = new ArrayList<>();
        while (end < longer.length()) {
            char newChar = longer.charAt(end);
            if (freq[newChar - 'a']-- > 0) count--;
            end++;

            while (count == 0) {
                if (end - start == shorter.length()) res.add(start);
                char c2 = longer.charAt(start);
                freq[c2 - 'a']++;
                if (freq[c2 - 'a'] > 0) count++;
                start++;
            }
        }

        return res;
    }

    //    224. Basic Calculator
    public static int calculate(String s) {
        int len = s.length(), sign = 1, result = 0;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < len; i++) {
            if (Character.isDigit(s.charAt(i))) {
                int sum = s.charAt(i) - '0';
                while (i + 1 < len && Character.isDigit(s.charAt(i + 1))) {
                    sum = sum * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
                result += sum * sign;
            } else if (s.charAt(i) == '+')
                sign = 1;
            else if (s.charAt(i) == '-')
                sign = -1;
            else if (s.charAt(i) == '(') {
                stack.push(result);
                stack.push(sign);
                result = 0;
                sign = 1;
            } else if (s.charAt(i) == ')') {
                result = result * stack.pop() + stack.pop();
            }

        }
        return result;
    }

    /**
     * 394. Decode String
     * Stack
     */
    public static String decodeString(String s) {
        Stack<String> strStack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c != ']') {
                strStack.add(String.valueOf(c));
            } else {
                String subStr = "";
                while (!strStack.peek().equals("[")) subStr = strStack.pop() + subStr;
                strStack.pop(); // clear [

                String numStr = "";
                while (!strStack.isEmpty()
                        && strStack.peek().length() == 1
                        && Character.isDigit(strStack.peek().charAt(0))
                ) {
                    numStr = strStack.pop() + numStr;
                }
                int times = Integer.parseInt(numStr);
                String res = "";
                while (times-- > 0) res += subStr;
                strStack.add(res);
            }
        }
        String res = "";
        while (!strStack.isEmpty()) res = strStack.pop() + res;
        return res;
    }

    //    127. Word Ladder
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        HashSet<String> words = new HashSet<String>();
        for (int i = 0; i < wordList.size(); i++) words.add(wordList.get(i));
        if (!words.contains(endWord)) return 0;

        int depth = 1;
        Queue<String> q = new LinkedList<String>();
        q.add(beginWord);
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                String word = q.remove();
                char[] cw = word.toCharArray();
                for (int i = 0; i < cw.length; i++) {
                    char original = cw[i];
                    for (char j = 'a'; j <= 'z'; j++) {
                        if (cw[i] == j) continue;
                        cw[i] = j;
                        String temp = String.valueOf(cw); // TODO:
                        if (temp.equals(endWord)) return depth + 1;
                        if (words.contains(temp)) {
                            q.offer(temp);
                            words.remove(temp);
                        }
                    }
                    cw[i] = original;
                }
            }
            depth++;
        }
        return 0;
    }

    public static int ladderLengthTLE(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) return 0;

        Map<String, Set<String>> wordsMap = new HashMap<>();
        wordList.add(beginWord);
        for (String word : wordList) {
            wordsMap.computeIfAbsent(word, k -> new HashSet<>());
            for (String nextWord : wordList) {
                if (!word.equals(nextWord) && !wordsMap.get(word).contains(nextWord)) {
                    wordsMap.computeIfAbsent(nextWord, k -> new HashSet<>());
                    if (differsByOneLetter(word, nextWord)) {
                        wordsMap.get(word).add(nextWord);
                        wordsMap.get(nextWord).add(word);
                    }
                }
            }
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(beginWord);
        visited.add(beginWord);

        int changes = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String word = queue.poll();
                if (word.equals(endWord)) return changes;
                for (String nextWord : wordsMap.get(word)) {
                    if (!visited.contains(nextWord)) {
                        visited.add(nextWord);
                        queue.add(nextWord);
                    }
                }
            }
            ++changes;
        }
        return 0;
    }


    public static boolean differsByOneLetter(String a, String b) {
        boolean flag = false;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                if (flag) return false;
                flag = true;
            }
        }
        return flag;
    }

    //    1048. Longest String Chain
    public static int longestStrChain(String[] words) {
        Arrays.sort(words, Comparator.comparingInt(String::length));
        Map<Integer, Set<String>> groupWordsByLen = new HashMap<>();
        Map<String, Integer> wordScores = new HashMap<>();

        for (String word : words) {
            int len = word.length();
            groupWordsByLen.computeIfAbsent(len, k -> new HashSet<>());
            groupWordsByLen.get(len).add(word);
            wordScores.put(word, 1);
        }

        int max = 1, minWordLength = words[0].length();

        for (String word : words) {
            int len = word.length();
            if (len > minWordLength) {
                int wordScore = 1;
                for (String shorterWord : groupWordsByLen.get(len - 1)) {
                    if (canComeNext(shorterWord, word)) {
                        wordScore = Math.max(wordScore, 1 + wordScores.getOrDefault(shorterWord, 1));
                    }
                }
                wordScores.put(word, wordScore);
                max = Math.max(max, wordScore);
            }
        }

        return max;
    }

    public static boolean canComeNext(String a, String b) {
        if (a.length() >= b.length()) return false;
        boolean added = false;
        int i = 0, j = 0;
        while (i < a.length() && j < b.length()) {
            if (a.charAt(i) == b.charAt(j)) {
                i++;
                j++;
            } else {
                if (added) return false;
                added = true;
                j++;
            }
        }
        return true;
    }

    public static int longestStrChainLee(String[] words) {
        Map<String, Integer> dp = new HashMap<>();
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        int res = 0;
        for (String word : words) {
            int best = 0;
            for (int i = 0; i < word.length(); i++) {
                String prev = word.substring(0, i) + word.substring(i + 1);
                best = Math.max(best, dp.getOrDefault(prev, 0) + 1);
            }
            dp.put(word, best);
            res = Math.max(res, best);
        }
        return res;
    }

    //    647. Palindromic Substrings
    public static int countSubstrings(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            count += extendSubstrings(s, i, i);
            count += extendSubstrings(s, i, i + 1);
        }
        return count;
    }

    public static int extendSubstrings(String s, int left, int right) {
        if (left >= 0 && right < s.length()) {
            if (s.charAt(left) == s.charAt(right)) {
                return 1 + extendSubstrings(s, left - 1, right + 1);
            }
        }
        return 0;
    }

    // 76
    public static String minWindow(String s, String t) {
        if (s == null || s.isEmpty() || t == null || t.isEmpty()) return "";
        int tLen = t.length(), sLen = s.length();
        if (tLen > sLen) return "";

        int left = 0, right = 0, found = 0, len = Integer.MAX_VALUE;
        int[] tMap = new int[256], sMap = new int[256], coors = new int[]{-1, -1};
        for (int k = 0; k < t.length(); k++) tMap[t.charAt(k)]++;

        while (right < sLen) {
            if (found < tLen) {
                // if the count of that required char <= required count, found ++
                int c = s.charAt(right++);
                if (tMap[c] > 0) {
                    if (++sMap[c] <= tMap[c]) found++;
                }
            }
            while (found == tLen) {
                if (right - left < len) {
                    len = right - left;
                    coors = new int[]{left, right};
                }
                int c = s.charAt(left);
                if (tMap[c] > 0) {
                    if (--sMap[c] < tMap[c]) found--;
                }
                left++;
            }
        }
        return coors[0] > -1 ? s.substring(coors[0], coors[1]) : "";
    }

    public static boolean hasSufficient(Map<Character, Integer> window, Map<Character, Integer> required) {
        for (char key : required.keySet()) {
            if (window.getOrDefault(key, 0) < required.get(key)) return false;
        }
        return true;
    }


    //
    @Test
    public void lengthOfLongestSubstringWorks() {
        int res = lengthOfLongestSubstring("abba");
        assertEquals(2, res);
    }


    // 3 - Sliding window approach
    public int lengthOfLongestSubstring(String s) {
        int result = 0, left = 0;
        int[] cache = new int[256];
        for (int right = 0; right < s.length(); right++) {
            left = (cache[s.charAt(right)] > 0) ? Math.max(left, cache[s.charAt(right)]) : left;
            cache[s.charAt(right)] = right + 1;
            result = Math.max(result, right - left + 1);
        }
        return result;
    }

    //    242
    public boolean isAnagram(String s, String t) {
        int[] memo = new int[26];
        int count = 0;
        for (char c : s.toCharArray()) {
            memo[c - 'a']++;
            count++;
        }
        for (char c : t.toCharArray()) {
            if (count-- == 0 || memo[c - 'a'] == 0) return false;
            memo[c - 'a']--;
        }
        return count == 0;
    }

    //    5. Longest Palindromic Substring
    public static String longestPalindrome(String s) {
        int maxLen = 0, start = 0, sLen = s.length(), i = 0;
        while (i < sLen) {
            int l = i, r = i, nextI = i + 1;
            while (r + 1 < sLen && s.charAt(r + 1) == s.charAt(i)) nextI = ++r;
            while (l >= 0 && r < sLen) {
                if (s.charAt(r) == s.charAt(l)) {
                    int len = r - l + 1;
                    if (len > maxLen) {
                        start = l;
                        maxLen = len;
                    }
                } else break;
                r++;
                l--;
            }
            i = nextI;
        }
        return s.substring(start, start + maxLen);
    }

    public static String longestCommonSubstring(String s1, String s2) {
        int s1Len = s1.length();
        int s2Len = s2.length();
        int max = 0;
        int subStrEnd = 0;
        int[][] dp = new int[s1Len + 1][s2Len + 1];
        for (int i = 1; i <= s1Len; i++) {
            for (int j = 1; j <= s2Len; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    int newMax = dp[i - 1][j - 1] + 1;
                    if (newMax > max) {
                        max = newMax;
                        subStrEnd = j;
                    }
                    dp[i][j] = newMax;
                }
            }
        }
        return s2.substring(subStrEnd - max, subStrEnd);
    }

    //    1663
    public static String getSmallestString(int n, int k) {
        int Zs = (k - n) / 25;
        int ZsVal = Zs * 26;
        int AsXVal = k - ZsVal;
        int As, XVal = 0;
        if (AsXVal == n - Zs) {
            As = AsXVal;
        } else {
            As = n - Zs - 1;
            XVal = k - ZsVal - As;
        }
        return (As > 0 ? "a".repeat(As) : "") +
                (XVal > 0 ? (char) (XVal + 'a' - 1) : "") +
                (Zs > 0 ? "z".repeat(Zs) : "");
    }

    //    125
    public static boolean isPalindrome(String s) {
        List<Character> arr = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetterOrDigit(s.charAt(i))) {
                arr.add(Character.toLowerCase(s.charAt(i)));
            }
        }
        int len = arr.size();
        for (int i = 0; i < len / 2; i++) {
            if (arr.get(i) != arr.get(len - 1 - i)) return false;
        }
        return true;
    }

    // From Leetcode
    public static int lengthOfLongestSubstring2(String s) {
        int[] count = new int[256]; // memo count
        char[] characters = s.toCharArray();
        int ans = 0;
        for (int l = 0, r = 0; r < s.length(); r++) {
            count[characters[r]]++; // add 1 to the count
            while (count[characters[r]] > 1) { // > 1 instance of char
                count[characters[l]]--; // shd be able to use both l and r here
                l++;
            }
            ans = Math.max(ans, r - l + 1);
        }
        return ans;
    }

    //    20
    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (char c : s.toCharArray()) {
            if (c == '(')
                stack.push(')');
            else if (c == '{')
                stack.push('}');
            else if (c == '[')
                stack.push(']');
            else if (stack.isEmpty() || stack.pop() != c)
                return false;
        }
        return stack.isEmpty();
    }
}
