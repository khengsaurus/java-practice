package MedianFinder;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This is so clean omg
 * https://leetcode.com/problems/find-median-from-data-stream/discuss/74062/Short-simple-JavaC%2B%2BPython-O(log-n)-%2B-O(1)
 */
class MedianFinde2 {
    private final Queue<Long> small = new PriorityQueue();
    private final Queue<Long> large = new PriorityQueue();

    public void addNum(int num) {
        large.add((long) num);
        small.add(-large.poll());
        if (large.size() < small.size()) large.add(-small.poll());
    }

    public double findMedian() {
        return large.size() > small.size()
                ? large.peek()
                : (large.peek() - small.peek()) / 2.0;
    }
};