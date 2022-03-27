public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        ListNode curr = this;
        while (curr != null){
            str.append(curr.val + ", ");
            curr = curr.next;
        }
        return str.toString();
    }
}