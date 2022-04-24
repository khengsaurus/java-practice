package ClassQuestions;

import java.util.Stack;

//    232. Implement Queue using Stacks
class MyQueue {
    private Stack<Integer> hold;
    private Stack<Integer> temp;

    public MyQueue() {
        hold = new Stack<>();
        temp = new Stack<>();
    }

    public void push(int x) {
        hold.push(x);
    }

    public int pop() {
        int r;
        while (!hold.isEmpty()) temp.add(hold.pop());
        r = temp.pop();
        while (!temp.isEmpty()) hold.add(temp.pop());
        return r;
    }

    public int peek() {
        int r;
        while (!hold.isEmpty()) temp.add(hold.pop());
        r = temp.peek();
        while (!temp.isEmpty()) hold.add(temp.pop());
        return r;
    }

    public boolean empty() {
        return hold.isEmpty();
    }
}

/**
 * Your ClassQuestions.MyQueue object will be instantiated and called as such:
 * ClassQuestions.MyQueue obj = new ClassQuestions.MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */