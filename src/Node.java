import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node implements Comparable<Node>, Iterator {
    public int val;
    public int value;
    public Node left;
    public Node right;
    public List<Node> neighbors;

    public Node random;
    public Node next;

    public Node(int value) {
        this.val = value;
        this.value = value;
        this.left = null;
        this.right = null;
        this.next = null;
        this.random = null;
    }

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }

    public Node(int val, Node next, Node random) {
        this.val = val;
        this.next = next;
        this.random = random;
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }

    @Override
    public int compareTo(Node o) {
        if (this.value > o.value) {
            return 1;
        }
        if (this.value < o.value) {
            return -1;
        }
        return 0;
    }

//    @Override
//    public String toString() {
//        StringBuilder str = new StringBuilder();
//        Node head = this;
//        while (head != null) {
//            System.out.println(head.val);
//            str.append(head.val);
//            head = head.next;
//        }
//        return str.toString();
//    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getRandom() {
        return this.random;
    }

    public void setRandom(Node random) {
        this.random = random;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public boolean hasNext() {
        return this.next != null;
    }

    @Override
    public Object next() {
        return null;
    }
}
