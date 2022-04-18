import java.util.*;

public class IntervalQns {
    public static void main(String[] args) {
        int[][] res = merge(new int[][]{{1, 3}, {0, 0}});
        for (int[] i : res) {
            System.out.println(Arrays.toString(i));
        }
    }

    //    56. Merge Intervals
    public static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        LinkedList<int[]> list = new LinkedList<>();
        for (int[] i : intervals) {
            if (list.isEmpty() || list.getLast()[1] < i[0]) {
                list.add(i);
            } else {
                list.getLast()[1] = Math.max(list.getLast()[1], i[1]);
            }
        }
        return list.toArray(new int[list.size()][]);
    }

    /**
     * Think of the problem! The stages, the scenarios, the decision space
     * 57. Insert Interval <- this one has 3
     */
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new LinkedList<>();
        int i = 0;
        // add all the intervals ending before newInterval starts
        while (i < intervals.length && intervals[i][1] < newInterval[0]) result.add(intervals[i++]);

        // merge all overlapping intervals to one considering newInterval, and add to result
        while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        result.add(newInterval);

        // add the rest
        while (i < intervals.length) result.add(intervals[i++]);

        return result.toArray(new int[result.size()][]);
    }

    public static int[][] insert2(int[][] intervals, int[] newInterval) {
        List<int[]> res = new ArrayList<>();

        for (int i = 0; i < intervals.length; i++) {
            int[] interval = intervals[i];
            if (newInterval[1] < interval[0]) {
                res.add(newInterval);
                for (int j = i; j < intervals.length; j++) res.add(intervals[j]);
                return res.toArray(new int[res.size()][]);
            } else if (newInterval[0] > interval[1]) {
                res.add(interval);
            } else {
                newInterval = new int[]{Math.min(interval[0], newInterval[0]), Math.max(interval[1], newInterval[1])};
            }
        }
        res.add(newInterval);
        return res.toArray(new int[res.size()][]);
    }
}
