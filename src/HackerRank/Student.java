package HackerRank;

class Student implements Comparable<Student> {
    private final int id;
    private final String name;
    private double cgpa;

    public Student(int id, String name, double cgpa) {
        this.id = id;
        this.name = name;
        this.cgpa = cgpa;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCgpa() {
        return cgpa;
    }

    @Override
    public int compareTo(Student o) {
        return this.cgpa == o.cgpa
                ? this.name.equals(o.name)
                ? this.id - o.id // ? this.id < o.id ? -1 : 1
                : this.name.compareTo(o.name)
                : this.cgpa > o.cgpa ? -1 : 1;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Name: " + this.getName() + ", ID: " + this.getId() + " CGPA: " + this.getCgpa());
        return str.toString();
    }
}