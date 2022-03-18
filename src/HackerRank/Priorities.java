package HackerRank;

import java.util.ArrayList;
import java.util.List;

class Priorities {
    private ArrayList<Student> students;

    public Priorities() {
        this.students = new ArrayList<>();
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

    // O(1)
    private void swap(int firstPosition, int secondPosition) {
        Student tmp = students.get(firstPosition);
        students.set(firstPosition, students.get(secondPosition));
        students.set(secondPosition, tmp);
    }

    // O(log N)
    private void heapify(int pos) {
        if (students.size() > 0) {
            Student curr = students.get(pos);
            Student left = leftChild(pos) < students.size() ? students.get(leftChild(pos)) : null;
            Student right = rightChild(pos) < students.size() ? students.get(rightChild(pos)) : null;
            if ((left != null && curr.compareTo(left) > 0) || (right != null && curr.compareTo(right) > 0)) {
                if (right == null || left.compareTo(right) < 0) {
                    swap(pos, leftChild(pos));
                    heapify(leftChild(pos));
                } else {
                    swap(pos, rightChild(pos));
                    heapify(rightChild(pos));
                }
            }
        }
    }

    public void insert(Student newStudent) {
        students.add(newStudent);
        int current = students.size() - 1;
        while (students.get(current).compareTo(students.get(parent(current))) < 0) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public Student serve() {
        Student popped = null;
        if (students.size() > 0) {
            popped = students.get(0);
            if (students.size() > 1) {
                students.set(0, students.get(students.size() - 1));
                students.remove(students.size() - 1);
                heapify(0);
            } else {
                students.remove(0);
            }
        }
        return popped;
    }

    public List<Student> getStudents(List<String> events) {
        for (String i : events) {
            if (i.equals("SERVED")) {
                if (!students.isEmpty()) this.serve();
            } else {
                String[] arr = i.split(" ");
                Student newStudent = new Student(Integer.parseInt(arr[3]), arr[1], Double.parseDouble(arr[2]));
                this.insert(newStudent);
            }
        }
        List<Student> sorted = new ArrayList<>();
        while (!students.isEmpty()) {
            Student toServe = this.serve();
            sorted.add(toServe);
        }
        return sorted;
    }

    public void printTree() {
        for (int i = 0; i <= students.size() - 1; i++) {
            System.out.println(students.get(i));
        }
    }
}