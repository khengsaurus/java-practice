import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Bit {
    public static void main(String[] args) {
        int i = 8;
        System.out.println(-8 >>> 2);
    }

    //    29. Divide Two Integers
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
        boolean negative = dividend < 0 ^ divisor < 0;

        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        int quotient = 0, subQuot = 0;

        while (dividend - divisor >= 0) {
            for (subQuot = 0; dividend - (divisor << subQuot << 1) >= 0; subQuot++) ;
            quotient += 1 << subQuot;
            dividend -= divisor << subQuot;
        }
        return negative ? -quotient : quotient;
    }

    //    318. Maximum Product of Word Lengths
    public static int maxProduct(String[] words) {
        int n = words.length;
        int[] bitRep = new int[n];

        for (int i = 0; i < n; i++) {
            for (char c : words[i].toCharArray()) {
                bitRep[i] |= (1 << (c - 'a'));
            }
        }

        int max = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (i != j && (bitRep[i] & bitRep[j]) == 0)
                    max = Math.max(max, words[i].length() * words[j].length());

        return max;
    }

    //    67. Add Binary
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1, carry = 0;
        while (i >= 0 || j >= 0) {
            int sum = carry;
            if (i >= 0) sum += a.charAt(i--) - '0';
            if (j >= 0) sum += b.charAt(j--) - '0';
            sb.insert(0, sum % 2);
            carry = sum / 2;
        }
        if (carry != 0) sb.insert(0, carry);
        return sb.toString();
    }
}
