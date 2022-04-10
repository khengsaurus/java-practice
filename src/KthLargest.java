import java.util.PriorityQueue;

// 703
class KthLargest {
    public static void main(String[] args) {
        KthLargest k = new KthLargest(3, new int[]{4, 5, 8, 2});
        System.out.println(k.add(3));
        System.out.println(k.add(5));
        System.out.println(k.add(10));
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

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */