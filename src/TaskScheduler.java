package linkedlist;

// Problem 3: Task Scheduler using a Circular Linked List
public class TaskScheduler {

    static class Task {
        int taskId;
        String name;
        int priority;
        String dueDate;
        Task next;

        Task(int taskId, String name, int priority, String dueDate) {
            this.taskId = taskId;
            this.name = name;
            this.priority = priority;
            this.dueDate = dueDate;
        }

        @Override
        public String toString() {
            return String.format("ID %d | %-12s | Priority %d | Due %s", taskId, name, priority, dueDate);
        }
    }

    private Task head;
    private Task tail;      // tail.next always points to head
    private Task current;   // task being viewed
    private int size;

    public void addAtBeginning(int id, String name, int priority, String dueDate) {
        Task node = new Task(id, name, priority, dueDate);
        if (head == null) {
            head = tail = node;
            node.next = node;
            current = node;
        } else {
            node.next = head;
            head = node;
            tail.next = head;       // keep the list circular
        }
        size++;
    }

    public void addAtEnd(int id, String name, int priority, String dueDate) {
        Task node = new Task(id, name, priority, dueDate);
        if (head == null) {
            head = tail = node;
            node.next = node;
            current = node;
        } else {
            tail.next = node;
            node.next = head;
            tail = node;
        }
        size++;
    }

    public void addAtPosition(int position, int id, String name, int priority, String dueDate) {
        if (position < 1 || position > size + 1) {
            System.out.println("Invalid position: " + position);
            return;
        }
        if (position == 1) {
            addAtBeginning(id, name, priority, dueDate);
        } else if (position == size + 1) {
            addAtEnd(id, name, priority, dueDate);
        } else {
            Task temp = head;
            for (int i = 1; i < position - 1; i++) {
                temp = temp.next;
            }
            Task node = new Task(id, name, priority, dueDate);
            node.next = temp.next;
            temp.next = node;
            size++;
        }
    }

    public boolean removeById(int id) {
        if (head == null) {
            System.out.println("Scheduler is empty.");
            return false;
        }
        Task prev = tail;
        Task temp = head;
        // Visit each node once (stop after one full cycle)
        for (int i = 0; i < size; i++) {
            if (temp.taskId == id) {
                if (size == 1) {
                    head = tail = current = null;
                } else {
                    prev.next = temp.next;
                    if (temp == head) {
                        head = temp.next;
                    }
                    if (temp == tail) {
                        tail = prev;
                    }
                    if (temp == current) {
                        current = temp.next;
                    }
                }
                size--;
                return true;
            }
            prev = temp;
            temp = temp.next;
        }
        return false;
    }

    // View the current task and move to the next one
    public void viewCurrentAndMoveNext() {
        if (current == null) {
            System.out.println("No tasks.");
            return;
        }
        System.out.println("Current task: " + current);
        current = current.next;
        System.out.println("Moved to next: " + current.name);
    }

    public void display() {
        if (head == null) {
            System.out.println("No tasks.");
            return;
        }
        Task temp = head;
        do {
            System.out.println(temp);
            temp = temp.next;
        } while (temp != head);
    }

    public void searchByPriority(int priority) {
        boolean found = false;
        if (head != null) {
            Task temp = head;
            do {
                if (temp.priority == priority) {
                    System.out.println(temp);
                    found = true;
                }
                temp = temp.next;
            } while (temp != head);
        }
        if (!found) {
            System.out.println("No task with priority " + priority);
        }
    }

    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.addAtEnd(1, "Write report", 2, "2026-12-01");
        scheduler.addAtEnd(2, "Fix bug", 1, "2026-11-28");
        scheduler.addAtBeginning(3, "Team meeting", 3, "2026-11-25");
        scheduler.addAtPosition(2, 4, "Code review", 1, "2026-11-30");

        System.out.println("--- All tasks from head ---");
        scheduler.display();

        System.out.println("\n--- View current and move next (5 times, loops back) ---");
        for (int i = 0; i < 5; i++) {
            scheduler.viewCurrentAndMoveNext();
        }

        System.out.println("\n--- Search priority 1 ---");
        scheduler.searchByPriority(1);

        System.out.println("\n--- Remove task 3 (head) and 2 ---");
        System.out.println(scheduler.removeById(3));
        System.out.println(scheduler.removeById(2));
        System.out.println(scheduler.removeById(77));
        scheduler.display();
    }
}
