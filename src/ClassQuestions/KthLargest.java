package ClassQuestions;

import java.util.PriorityQueue;

// 703
class KthLargest {
    public static void main(String[] args) {
        KthLargest k = new KthLargest(3, new int[]{4, 5, 8, 2});
    }

    final PriorityQueue<Integer> heap;
    final int k;

    public KthLargest(int k, int[] a) {
        this.k = k;
        heap = new PriorityQueue<>(k);
        for (int n : a) add(n);
    }

    public int add(int n) {
        if (heap.size() < k)
            heap.offer(n);
        else if (heap.peek() < n) {
            heap.poll();
            heap.offer(n);
        }
        return heap.peek();
    }
}
