package Sandbox;

import Sandbox.Node;

import java.util.*;

public class TestStackQueue {
    public static void main(String[] args) {
//        TestStack();
//        TestPQ();
        TestLL();
    }

    // FILO
    public static void TestStack() {
        Stack<Integer> stack = new Stack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.add(4);
        stack.remove(3); // = stack.pop()
        System.out.println(stack.toString());
    }

    //    FIFO - LL implemented w a double linked list - preserves insertion order
//    Poll same as remove -> remove 0'th e
    public static void TestLL() {
        Queue<Integer> queueLL = new LinkedList<Integer>();
        queueLL.add(1);
        queueLL.add(10);
        queueLL.add(2);
//        queueLL.offer(11);
        System.out.println(queueLL.toString());
        while (queueLL.size() > 0) {
            System.out.println(queueLL.remove());
        }
    }

    //    FIFO - Priority Queue DOES NOT preserve insertion order.
//    default PQ is implemented with Min-Heap! Sorting alphabetically or smallest int first
    public static void TestPQ() {
        Queue<Node> queuePQ = new PriorityQueue<>();
        queuePQ.add(new Node((2)));
        queuePQ.add(new Node((3)));
        queuePQ.add(new Node((20)));
        queuePQ.add(new Node((4)));
        queuePQ.add(new Node((10)));
//        insertion order is maintained up to here as children are added L -> R of parent, no child > parent
//        the PQ is traversed by index where parent = n, left child = 2n+1, right child = 2n + 2
//        print queuePQ -> [2, 3, 20, 4, 10]
        queuePQ.add(new Node((1)));
//        here, child < parent -> 1 is shuffled up, swapping with parent (20) then parent (2)
//        print queuePQ -> [1, 3, 2, 4, 10, 20]
//        System.out.println(queuePQ);
//        while(queuePQ.size() > 0){
//        System.out.println(queuePQ.remove());
//        }
        System.out.println(queuePQ);
    }
}
