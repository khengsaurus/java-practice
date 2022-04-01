package LeetcodePlans;

import java.util.Stack;

public class MyQueue {
    private Stack _stack;

    public MyQueue() {
        this._stack = new Stack();
    }

    public void push(int x) {
        _stack.add(0, x);
    }

    public int pop() {
        return (int) _stack.pop();
    }

    public int peek() {
        return (int) _stack.peek();
    }

    public boolean empty() {
        return _stack.isEmpty();
    }
}
