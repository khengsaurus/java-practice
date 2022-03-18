import java.util.*;

public class StringQns {
    public static void main(String[] args) {
        Boolean res = isPalindrome("a:vA'");
        System.out.println(res);
    }

    public static boolean isPalindrome(String s) {
        List<Character> arr = new ArrayList<>();
        for(int i = 0; i< s.length(); i++){
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

    public static int lengthOfLongestSubstringBetter(String s) {
        // not my solution
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

    public static int lengthOfLongestSubstring(String s) {
        int len = s.length();
        if (s.length() <= 1) return len;
        Map<Character, Integer> map = new HashMap<>();
        int anchor = 0;
        int globalMax = 0;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            int prev = map.getOrDefault(c, -1);
            if (prev < anchor) {
                globalMax = Math.max(globalMax, i - anchor + 1);
            } else {
                anchor = prev + 1;
            }
            map.put(c, i);
        }
        return globalMax;
    }

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
