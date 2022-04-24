package MedianFinder;

import java.util.Collections;
import java.util.PriorityQueue;

class MedianFinder {
    private PriorityQueue<Integer> smallHeap;
    private PriorityQueue<Integer> bigHeap;

    public MedianFinder() {
        smallHeap = new PriorityQueue<>(Collections.reverseOrder());
        bigHeap = new PriorityQueue<>();
    }

    public void addNum(int num) {
        if (bigHeap.size() == smallHeap.size()) {
            bigHeap.add(num);
            smallHeap.add(bigHeap.poll());
        } else {
            smallHeap.add(num);
            bigHeap.add(smallHeap.poll());
        }
    }

    public double findMedian() {
        if ((smallHeap.size() + bigHeap.size()) % 2 == 0) {
            return (double) (smallHeap.peek() + bigHeap.peek()) / 2.0;
        } else {
            return (double) smallHeap.peek();
        }
    }
}