package linkedlist;

// Problem 6: Round Robin CPU Scheduling using a Circular Linked List
public class RoundRobinScheduler {

    static class Process {
        int pid;
        int burstTime;
        int priority;
        int remaining;
        Process next;

        Process(int pid, int burstTime, int priority) {
            this.pid = pid;
            this.burstTime = burstTime;
            this.priority = priority;
            this.remaining = burstTime;
        }
    }

    private Process head;
    private Process tail;   // tail.next = head
    private int size;

    // Add a process at the end of the circular list
    public void addProcess(int pid, int burstTime, int priority) {
        Process node = new Process(pid, burstTime, priority);
        if (head == null) {
            head = tail = node;
            node.next = node;
        } else {
            tail.next = node;
            node.next = head;
            tail = node;
        }
        size++;
    }

    // Remove a process by ID (after its execution)
    public boolean removeProcess(int pid) {
        if (head == null) {
            return false;
        }
        Process prev = tail;
        Process temp = head;
        for (int i = 0; i < size; i++) {
            if (temp.pid == pid) {
                if (size == 1) {
                    head = tail = null;
                } else {
                    prev.next = temp.next;
                    if (temp == head) {
                        head = temp.next;
                    }
                    if (temp == tail) {
                        tail = prev;
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

    // Display the processes in the circular queue
    public void displayQueue() {
        if (head == null) {
            System.out.println("   Queue: (empty)");
            return;
        }
        StringBuilder sb = new StringBuilder("   Queue: ");
        Process temp = head;
        do {
            sb.append("P").append(temp.pid).append("(rem=").append(temp.remaining).append(") ");
            temp = temp.next;
        } while (temp != head);
        System.out.println(sb);
    }

    // Simulate round robin (all processes arrive at time 0)
    public void simulate(int quantum) {
        int n = size;
        int[] waiting = new int[1000];
        int[] turnaround = new int[1000];
        int time = 0;
        int round = 1;
        double totalWaiting = 0;
        double totalTurnaround = 0;

        while (head != null) {
            System.out.println("Round " + round + ":");
            int processesInRound = size;
            Process current = head;
            for (int i = 0; i < processesInRound; i++) {
                Process next = current.next;     // save before a possible removal
                int run = Math.min(quantum, current.remaining);
                System.out.println("   P" + current.pid + " runs from " + time + " to " + (time + run));
                time += run;
                current.remaining -= run;
                if (current.remaining == 0) {
                    turnaround[current.pid] = time;                        // arrival time = 0
                    waiting[current.pid] = time - current.burstTime;
                    totalTurnaround += turnaround[current.pid];
                    totalWaiting += waiting[current.pid];
                    System.out.println("   P" + current.pid + " finished at " + time + " -> removed");
                    removeProcess(current.pid);
                }
                current = next;
            }
            displayQueue();
            round++;
        }

        System.out.println("\nPID | Waiting | Turn-around");
        // Print results for the processes in input order
        for (Process p : originalOrder) {
            System.out.printf("P%-2d | %-7d | %d%n", p.pid, waiting[p.pid], turnaround[p.pid]);
        }
        System.out.printf("Average waiting time    = %.2f%n", totalWaiting / n);
        System.out.printf("Average turn-around time = %.2f%n", totalTurnaround / n);
    }

    private final java.util.List<Process> originalOrder = new java.util.ArrayList<>();

    public static void main(String[] args) {
        RoundRobinScheduler scheduler = new RoundRobinScheduler();
        int[][] input = {{1, 10, 2}, {2, 5, 1}, {3, 8, 3}};    // pid, burst, priority
        for (int[] p : input) {
            scheduler.addProcess(p[0], p[1], p[2]);
            scheduler.originalOrder.add(scheduler.tail);
        }
        int quantum = 3;

        System.out.println("Time quantum = " + quantum);
        scheduler.displayQueue();
        System.out.println();
        scheduler.simulate(quantum);
    }
}
