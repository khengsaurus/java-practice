import java.util.Arrays;
import java.util.Stack;

public class ArrayQns {
    public static void main(String[] args) {
        boolean res = validateStackSequences(new int[]{1, 2, 3, 4, 5}, new int[]{4, 5, 3, 2, 1});
        System.out.println(res);
    }

    // fking brilliant sln which isnt mine
    public static boolean validateStackSequences(int[] pushed, int[] popped) {
        int i = 0, j = 0;
        for (int val : pushed) {
            pushed[i++] = val; // req because i-- later;
            while (j < popped.length && i > 0 && pushed[i - 1] == popped[j]) {
                i--;
                j++;
            }
            System.out.println(i + ", " + j);
            System.out.println(Arrays.toString(pushed));
        }
        return i == 0;
    }
}
