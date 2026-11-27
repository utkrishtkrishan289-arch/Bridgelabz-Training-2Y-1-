package linkedlist;

// Problem 1: Student Record Management using a Singly Linked List
public class StudentRecords {

    // Node of the singly linked list
    static class Student {
        int rollNo;
        String name;
        int age;
        String grade;
        Student next;

        Student(int rollNo, String name, int age, String grade) {
            this.rollNo = rollNo;
            this.name = name;
            this.age = age;
            this.grade = grade;
        }
    }

    private Student head;   // first student; last node's next is null
    private int size;

    // Add at the beginning
    public void addAtBeginning(int rollNo, String name, int age, String grade) {
        Student node = new Student(rollNo, name, age, grade);
        node.next = head;
        head = node;
        size++;
    }

    // Add at the end
    public void addAtEnd(int rollNo, String name, int age, String grade) {
        Student node = new Student(rollNo, name, age, grade);
        if (head == null) {
            head = node;
        } else {
            Student temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = node;
        }
        size++;
    }

    // Add at a position (1 = beginning, size + 1 = end)
    public void addAtPosition(int position, int rollNo, String name, int age, String grade) {
        if (position < 1 || position > size + 1) {
            System.out.println("Invalid position: " + position);
            return;
        }
        if (position == 1) {
            addAtBeginning(rollNo, name, age, grade);
            return;
        }
        Student temp = head;
        for (int i = 1; i < position - 1; i++) {
            temp = temp.next;
        }
        Student node = new Student(rollNo, name, age, grade);
        node.next = temp.next;
        temp.next = node;
        size++;
    }

    // Delete by roll number
    public boolean deleteByRollNo(int rollNo) {
        if (head == null) {
            System.out.println("List is empty.");
            return false;
        }
        if (head.rollNo == rollNo) {
            Student removed = head;
            head = head.next;
            removed.next = null;
            size--;
            return true;
        }
        Student temp = head;
        while (temp.next != null && temp.next.rollNo != rollNo) {
            temp = temp.next;
        }
        if (temp.next == null) {
            return false;
        }
        Student removed = temp.next;
        temp.next = removed.next;
        removed.next = null;
        size--;
        return true;
    }

    // Search by roll number
    public Student search(int rollNo) {
        Student temp = head;
        while (temp != null) {
            if (temp.rollNo == rollNo) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    // Update grade by roll number
    public boolean updateGrade(int rollNo, String newGrade) {
        Student student = search(rollNo);
        if (student == null) {
            return false;
        }
        student.grade = newGrade;
        return true;
    }

    // Display all records
    public void display() {
        if (head == null) {
            System.out.println("No student records.");
            return;
        }
        System.out.println("Roll No | Name       | Age | Grade");
        Student temp = head;
        while (temp != null) {
            System.out.printf("%-7d | %-10s | %-3d | %s%n", temp.rollNo, temp.name, temp.age, temp.grade);
            temp = temp.next;
        }
    }

    public static void main(String[] args) {
        StudentRecords list = new StudentRecords();

        System.out.println("--- Add records ---");
        list.addAtEnd(2, "Aman", 19, "B");
        list.addAtBeginning(1, "Riya", 18, "A");
        list.addAtEnd(4, "Karan", 20, "C");
        list.addAtPosition(3, 3, "Neha", 19, "A");
        list.addAtPosition(10, 9, "Wrong", 0, "F");
        list.display();

        System.out.println("\n--- Search roll no 3 ---");
        Student found = list.search(3);
        System.out.println(found != null ? "Found: " + found.name + ", grade " + found.grade : "Not found");

        System.out.println("\n--- Update grade of roll no 4 to B ---");
        System.out.println(list.updateGrade(4, "B") ? "Updated" : "Roll number not found");
        list.display();

        System.out.println("\n--- Delete roll no 1 (head) and 99 (missing) ---");
        System.out.println("Delete 1: " + list.deleteByRollNo(1));
        System.out.println("Delete 99: " + list.deleteByRollNo(99));
        list.display();
    }
}
