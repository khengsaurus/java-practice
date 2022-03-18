package HackerRank;

import java.util.ArrayList;
import java.util.List;

public class HackerRank {
    public static void main(String[] args) {
        Priorities heap = new Priorities();
        List<String> ins = new ArrayList<>();
//        ins.add("12");
//        ins.add("ENTER John 3.75 50");
//        ins.add("ENTER Mark 3.8 24");
//        ins.add("ENTER Shafaet 3.7 35");
//        ins.add("SERVED");
//        ins.add("SERVED");
//        ins.add("ENTER Samiha 3.85 36");
//        ins.add("SERVED");
//        ins.add("ENTER Ashley 3.9 42");
//        ins.add("ENTER Maria 3.6 46");
//        ins.add("ENTER Anik 3.95 49");
//        ins.add("ENTER Dan 3.95 50");
//        ins.add("SERVED");
        ins.add("ENTER Bidhan 3.75 50");
        ins.add("ENTER Rijul 3.8 24");
        ins.add("ENTER Shafaet 3.7 35");
        ins.add("SERVED");
        ins.add("SERVED");
        ins.add("ENTER Ratul 3.9 42");
        ins.add("ENTER Tanvir 3.6 46");
        ins.add("ENTER Anik 3.95 49");
        ins.add("ENTER Nisha 3.95 50");
        ins.add("SERVED");
        ins.add("SERVED");
        ins.add("SERVED");
        ins.add("SERVED");
        ins.add("SERVED");
        ins.add("SERVED");
        ins.add("SERVED");
        ins.add("SERVED");
        ins.add("SERVED");
        ins.add("SERVED");
        ins.add("SERVED");
        ins.add("SERVED");
        List<Student> sorted = heap.getStudents(ins);

        for(Student s: sorted){
            System.out.println(s.getName());
        }
    }
}

