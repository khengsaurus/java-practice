import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class LinkedListQns {
    public static void main(String[] args) {
//        ListNode list1 = newLinkedList(new int[]{1, 2, 3, 4, 5}, -1);
//        ListNode list2 = newLinkedList(new int[]{3, 4, 11}, -1);
//        ListNode list3 = newLinkedList(new int[]{1, 2}, -1);
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
//        ListNode l3 = new ListNode(3);
//        ListNode l4 = new ListNode(4);
//        ListNode l5 = new ListNode(5);
//        ListNode l6 = new ListNode(6);
        l1.next = l2;
//        l2.next = l3;
//        l3.next = l4;
//        l4.next = l5;
//        l5.next = l6;
        l1 = removeNthFromEnd2(l1, 2);
        System.out.println(l1);
    }

    //    19 - with hashmap
    public ListNode removeNthFromEnd(ListNode head, int n) {
        HashMap<Integer, ListNode> map = new HashMap<>();
        int count = 0;
        while (head != null) {
            count++;
            map.put(count, head);
            head = head.next;
        }
        if (n == count) {
            // return 2nd element as head. use .next as 2 may be null
            return map.get(1).next;
        }
        int prev = count - n;
        if (n == 1) {
            map.get(prev).next = null;
        } else {
            map.get(prev).next = map.get(prev + 2);
        }
        return map.get(1);
    }

    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode pre = new ListNode(-1, head);
        ListNode first = pre, second = pre;
        while (n-- > 0) second = second.next;
        while (second.next != null) { //
            second = second.next;
            first = first.next;
        }
        first.next = first.next.next;
        return pre.next;
    }

    //    876
    public static ListNode middleNode(ListNode head) {
        if (head.next == null) return head;
        ListNode slow = head, fast = head.next;
        while (fast != null) {
            slow = slow.next;
            fast = fast.next == null ? fast.next : fast.next.next;
        }
        return slow;
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (ListNode head : lists) {
            while (head != null) {
                // use a PQ here because it prioritises min val
                queue.add(head.val);
                head = head.next;
            }
        }
        ListNode head = null;
        ListNode pointer = null;
        while (!queue.isEmpty()) {
            ListNode newNode = new ListNode(queue.remove());
            if (head == null) {
                head = newNode;
                pointer = head;
            } else {
                pointer.next = newNode;
                pointer = pointer.next;
            }
        }
        return head;
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null && list2 == null) {
            return null;
        }
        ListNode head = null;
        ListNode temp = null;
        if (list1 == null || (list2 != null && list2.val < list1.val)) {
            temp = list2.next;
            head = list2;
            list2 = temp;
        } else {
            temp = list1.next;
            head = list1;
            list1 = temp;
        }
        ListNode current = head;
        while (list1 != null || list2 != null) {
            if (list1 == null || (list2 != null && list2.val < list1.val)) {
                temp = list2.next;
                current.next = list2;
                current = current.next;
                list2 = temp;
            } else {
                temp = list1.next;
                current.next = list1;
                current = current.next;
                list1 = temp;
            }
        }
        return head;
    }

    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode tortoise = head;
        ListNode hare = head.next;
        // will exit if no loop
        while (hare != null && tortoise != null) {
            if (hare == tortoise) {
                return true;
            }
            if (hare.next.next == null) return false;
            hare = hare.next.next;
            tortoise = tortoise.next;
        }
        return false;
    }

    public static ListNode reverseList(ListNode head) {
        ListNode curr = head;
        ListNode prev = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public static ListNode newLinkedList(int[] vals, int cycle) {
        ListNode head = new ListNode(vals[0]);
        ListNode pointer = head;
        for (int i = 1; i < vals.length; i++) {
            pointer.next = new ListNode(vals[i]);
            pointer = pointer.next;
        }
        if (cycle > -1 && cycle < vals.length - 1) {
            ListNode cycleTo = head;
            for (int i = 0; i < cycle; i++) {
                cycleTo = cycleTo.next;
            }
            pointer.next = cycleTo;
        }
        return head;
    }

    public static void printLinkedList(ListNode head) {
        ListNode pointer = head;
        while (pointer != null) {
            System.out.println(pointer.val);
            pointer = pointer.next;
        }
    }
}
