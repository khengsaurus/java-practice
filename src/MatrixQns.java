import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MatrixQns {
    public static void main(String[] args) {
        rotate(new int[][]{{10, 20, 30, 40}, {50, 60, 70, 80}, {90, 10, 11, 12}, {13, 14, 15, 16}});
    }

    //    48. Rotate Image - take my knees pls
    public static void rotate(int[][] matrix) {
        int n = matrix.length;
        // l is the side length of the matrix we are processing
        for (int len = n; len > 1; len -= 2) {
            // left is the start index of the matrix we are processing
            int left = (n - len) / 2;
            // right is the end index of the matrix we are processing
            int right = left + len - 1;
            for (int i = 0; i < len - 1; i++) {
                // move number on one side to the other side clockwise
                int temp = matrix[right - i][left];
                matrix[right - i][left] = matrix[right][right - i];
                matrix[right][right - i] = matrix[left + i][right];
                matrix[left + i][right] = matrix[left][left + i];
                matrix[left][left + i] = temp;
            }
        }
        return;
    }

    //    59. Spiral Matrix II
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        int curr = 1, top = 0, bottom = n - 1, left = 0, right = n - 1;
        while (curr <= n * n) {
            // right
            for (int i = left; i <= right; i++) res[top][i] = curr++;
            top++;
            // down
            for (int i = top; i <= bottom; i++) res[i][right] = curr++;
            right--;
            // left
            for (int i = right; i >= left; i--) res[bottom][i] = curr++;
            bottom--;
            // up
            for (int i = bottom; i >= top; i--) res[i][left] = curr++;
            left++;
        }
        return res;
    }

    //    54. Spiral Matrix
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<Integer>();
        if (matrix.length == 0) return res;

        int rowBegin = 0, rowEnd = matrix.length - 1, colBegin = 0, colEnd = matrix[0].length - 1;
        while (rowBegin <= rowEnd && colBegin <= colEnd) {
            // Right
            for (int j = colBegin; j <= colEnd; j++) res.add(matrix[rowBegin][j]);
            rowBegin++;
            // Down
            for (int j = rowBegin; j <= rowEnd; j++) res.add(matrix[j][colEnd]);
            colEnd--;

            // Left
            if (rowBegin <= rowEnd) {
                for (int j = colEnd; j >= colBegin; j--) res.add(matrix[rowEnd][j]);
            }
            rowEnd--;

            // Up
            if (colBegin <= colEnd) {
                for (int j = rowEnd; j >= rowBegin; j--) res.add(matrix[j][colBegin]);
            }
            colBegin++;
        }

        return res;
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
