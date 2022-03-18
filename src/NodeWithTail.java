public class NodeWithTail extends Node{
    private Node previous;

    public NodeWithTail(int value) {
        super(value);
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }
}
