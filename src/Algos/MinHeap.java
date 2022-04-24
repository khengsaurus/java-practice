package Algos;

import java.util.Arrays;

// Original algo from https://www.geeksforgeeks.org/min-heap-in-java
public class MinHeap {
    private int size;
    private int maxsize;
    private int[] Heap;

    public MinHeap(int maxsize) {
        this.maxsize = maxsize;
        this.size = 0;
        Heap = new int[this.maxsize];
    }

    private int parent(int pos) {
        return (pos - 1) / 2;
    }

    private int leftChild(int pos) {
        return (2 * pos) + 1;
    }

    private int rightChild(int pos) {
        return (2 * pos) + 2;
    }

    private boolean isLeaf(int pos) {
        return pos > (size / 2) && pos <= size;
    }

    // O(1)
    private void swap(int firstPosition, int secondPosition) {
        int tmp = Heap[firstPosition];
        Heap[firstPosition] = Heap[secondPosition];
        Heap[secondPosition] = tmp;
    }

    // O(log N)
    private void heapify(int pos) {
        if (!isLeaf(pos)) {
            if (Heap[pos] > Heap[leftChild(pos)] || Heap[pos] > Heap[rightChild(pos)]) {
                if (Heap[leftChild(pos)] < Heap[rightChild(pos)]) {
                    swap(pos, leftChild(pos));
                    heapify(leftChild(pos));
                } else {
                    swap(pos, rightChild(pos));
                    heapify(rightChild(pos));
                }
            }
        }
    }

    // O(N), not O(N log N) !!
    // -> O(1) for leaf nodes (>= 0.5 of nodes)
    // -> O(log N) for swapping to root
    public void insert(int element) {
        if (size < maxsize) {
            Heap[size] = element;
            int current = size;
            while (Heap[current] < Heap[parent(current)]) {
                swap(current, parent(current));
                current = parent(current);
            }
            size++;
        }
    }

    public int remove() {
        int popped = Heap[0];
        Heap[0] = Heap[size--];
        heapify(0);
        return popped;
    }

    public void printTree() {
        for (int i = 0; i <= size / 2; i++) {
            System.out.println(" PARENT : " + Heap[i]
                    + " LEFT CHILD : " + Heap[leftChild(i)]
                    + " RIGHT CHILD :" + Heap[rightChild(i)]);
        }
    }

    public void print() {
        System.out.println(Arrays.toString(Heap));
    }
}