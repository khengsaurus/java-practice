import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringQns {
    public static void main(String[] args) {
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

    //    424 - Longest repeating character replacement TODO:
    public static int characterReplacement(String s, int k) {
        int[] count = new int[26];
        int len = s.length(), left = 0, maxCountRepeatedChar = 0;
        for (int right = 0; right < len; right++) {
            maxCountRepeatedChar = Math.max(maxCountRepeatedChar, ++count[s.charAt(right) - 'A']);
            int replaces = right - left + 1 - maxCountRepeatedChar;
            if (replaces > k) {
                count[s.charAt(left++) - 'A']--;
            }
        }
        return Math.min(len, maxCountRepeatedChar + k);
    }

    //    5
    public static String longestPalindrome(String s) {
        int maxLen = 0;
        int start = 0;
        int sLen = s.length();
        int i = 0;
        while (i < sLen) {
            int l = i;
            int r = i;
            int nextI = i + 1;
            // push right pointer if characters are repeating
            while (r + 1 < sLen && s.charAt(r + 1) == s.charAt(i)) {
                r++;
                // push index if ^
                nextI = r;
            }
            ;
            while (l >= 0 && r < sLen) {
                if (s.charAt(r) == s.charAt(l)) {
                    int len = r - l + 1;
                    if (len > maxLen) {
                        start = l;
                        maxLen = len;
                    }
                } else {
                    break;
                }
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
