import Sandbox.Node;

import java.util.Iterator;

public class MyIterable implements Iterable {

    public static void main(String[] args) {
        MyIterable list = new MyIterable(new int[]{1, 2, 3, 4});
        System.out.println(list);
    }

    private Node head;
//    private NodeWithTail tail;

    public MyIterable(int val) {
        this.head = new Node(val);
    }

    public MyIterable(int[] vals) {
        if (vals.length > 0) {
            this.head = new Node(vals[0]);
        }
        if (vals.length > 1) {
            Node pointer = this.head;
            for (int curr = 1; curr < vals.length; curr++) {
                pointer.setNext(new Node(vals[curr]));
                pointer = pointer.getNext();
            }
        }
    }

//    public void add(Sandbox.Node node){}

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Node curr = head;
        while (curr != null) {
            str.append(curr.val + ", ");
            curr = curr.next;
        }
        return str.toString();
    }

    @Override
    public Iterator<Node> iterator() {
        return null;
    }
}
