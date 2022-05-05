import java.util.*;

public class LinkedListQns {
    //    143. Reorder List
    public void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) return;
        List<ListNode> list = new ArrayList<>();
        int count = 0;
        while (head != null) {
            list.add(head);
            head = head.next;
            count++;
        }
        head = null;
        for (int i = 0; i < (count + 1) / 2; i++) {
            if (head == null) head = list.get(i);
            else {
                head.next = list.get(i);
                head = head.next;
            }
            if (i <= count / 2) {
                head.next = list.get(count - i - 1);
                head = head.next;
            }
        }
        head.next = null;
    }

    public void reorderListBetter(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) return;
        // Cut the list into two halves, prev will be the tail of 1st half, slow will be the head of 2nd half
        ListNode prev = null, slow = head, fast = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        prev.next = null;

        ListNode tail = reverseLinkedList(slow); // step 2. reverse the 2nd half
        merge(head, tail); // step 3. merge the two halves
    }

    ListNode reverseLinkedList(ListNode head) {
        ListNode prev = null, curr = head, next;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    void merge(ListNode l1, ListNode l2) {
        while (l1 != null) {
            ListNode n1 = l1.next, n2 = l2.next;
            l1.next = l2;
            if (n1 == null) break;
            l2.next = n1;
            l1 = n1;
            l2 = n2;
        }
    }

    //    2. Add Two Numbers
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        ListNode preRes = new ListNode(), head = null;
        while (l1 != null || l2 != null) {
            int sum = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val) + carry;
            int v = sum % 10;
            carry = sum / 10;
            if (head == null) {
                head = new ListNode(v);
                preRes.next = head;
            } else {
                head.next = new ListNode(v);
                head = head.next;
            }
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        return preRes.next;
    }

    //    1721
    public static ListNode swapNodes(ListNode head, int k) {
        if (head == null || head.next == null) return head;
        ListNode kth = head, kthFromBack = head, fast = head.next;
        int count = 1;
        while (fast != null) {
            if (count == k - 1) kth = fast;
            fast = fast.next;
            if (count++ >= k) kthFromBack = kthFromBack.next;
        }
        int t = kthFromBack.val;
        kthFromBack.val = kth.val;
        kth.val = t;
        return head;
    }

    // 83
    public static ListNode deleteDuplicates(ListNode head) {
        ListNode prev = new ListNode(-101, head);
        ListNode curr = prev;
        while (curr != null) {
            if (curr.next != null && curr.next.val == curr.val) {
                curr.next = curr.next.next;
            } else {
                curr = curr.next;
            }
        }
        return prev.next;
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

    //    23. Merge k Sorted Lists
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;
        if (lists.length == 1) return lists[0];

        PriorityQueue<ListNode> heap = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (ListNode head : lists) {
            while (head != null) {
                heap.offer(head);
                head = head.next;
            }
        }
        if (heap.isEmpty()) return null;
        ListNode head = heap.poll();
        ListNode pointer = head;
        while (!heap.isEmpty()) {
            ListNode newNode = heap.poll();
            newNode.next = null;
            pointer.next = newNode;
            pointer = pointer.next;
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
