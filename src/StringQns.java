import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class StringQns {
    public static void main(String[] args) {
        Boolean res = isPalindrome("a:vA'");
        System.out.println(res);
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

    @Test
    public void lengthOfLongestSubstringWorks() {
        int res = lengthOfLongestSubstring("hijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789hijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789hijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789hijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789hijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789hijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        assertTrue(res == 55);
    }

    // 3 - Sliding window approach
    public static int lengthOfLongestSubstring(String s) {
        int anchor = 0, max = 0;
        int[] memo = new int[256]; // 1-index
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (memo[chars[i]] > 0 && memo[chars[i]] - 1 >= anchor) {
                anchor = memo[chars[i]];
            }
            memo[chars[i]] = i + 1;
            max = Math.max(i - anchor + 1, max);
        }
        return max;
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
