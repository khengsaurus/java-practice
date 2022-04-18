import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MatrixQns {
    public static void main(String[] args) {
//        int[][] r = new int[][]{{1, 2, 3, 4, 5}, {2, 2, 3, 4, 5}, {3, 2, 3, 4, 5}, {4, 2, 3, 4, 5}, {5, 2, 3, 4, 5}};
//        List<List<Integer>> arr = shiftGrid(r, 5);
//        for (List<Integer> a : arr) System.out.println(a);
    }

    public static int[] shiftLeft(int[] arr, int k) {
        int len = arr.length;
        k = k % len;
        int[] r = new int[len];
        for (int i = 0; i < len; i++) {
            int j = i + k >= len
                    ? (i + k) % len
                    : i + k;
            r[i] = arr[j];
        }
        return r;
    }

    public static int[] shiftRight(int[] arr, int k) {
        int len = arr.length;
        k = k % len;
        int[] r = new int[len];
        for (int i = 0; i < len; i++) {
            int j = i - k < 0
                    ? len - (k - i)
                    : i - k;
            r[i] = arr[j];
        }
        return r;
    }

    public static List<List<Integer>> shiftGrid(int[][] grid, int k) {
        LinkedList<List<Integer>> r = new LinkedList<>();
        int rows = grid.length, cols = grid[0].length, elements = rows * cols, start = elements - k % elements;
        for (int i = start; i < elements + start; ++i) {
            int j = i % elements, row = j / cols, col = j % cols;
            if ((i - start) % cols == 0) r.add(new ArrayList<>());
            r.peekLast().add(grid[row][col]);
        }
        return r;
    }
}
