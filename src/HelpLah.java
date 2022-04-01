import java.util.Arrays;

public class HelpLah {
    public static void main(String[] args) {
        int res = maxScoreSightseeingPair(new int[]{8, 1, 8, 2, 6});
        System.out.println(res);
    }

    // 1014
    public static int maxScoreSightseeingPair(int[] values) {
        int[] res = new int[values.length + 1], cur = new int[values.length + 1];
        for (int i = 0; i < values.length; i++) {
            res[i + 1] = Math.max(res[i], cur[i] + values[i]);
            cur[i + 1] = Math.max(cur[i], values[i]) - 1;
            System.out.println(Arrays.toString(values));
//            System.out.println(Arrays.toString(res));
            System.out.println(Arrays.toString(cur));
            System.out.println();
        }
        return res[values.length];
    }
}
