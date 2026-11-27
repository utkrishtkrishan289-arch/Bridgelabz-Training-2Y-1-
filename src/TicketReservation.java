package linkedlist;

// Problem 9: Online Ticket Reservation System using a Circular Linked List
public class TicketReservation {

    static class Ticket {
        int ticketId;
        String customerName;
        String movieName;
        int seatNumber;
        String bookingTime;
        Ticket next;

        Ticket(int ticketId, String customerName, String movieName, int seatNumber, String bookingTime) {
            this.ticketId = ticketId;
            this.customerName = customerName;
            this.movieName = movieName;
            this.seatNumber = seatNumber;
            this.bookingTime = bookingTime;
        }

        @Override
        public String toString() {
            return String.format("Ticket %d | %-8s | %-12s | Seat %-3d | %s", ticketId, customerName,
                    movieName, seatNumber, bookingTime);
        }
    }

    private Ticket head;
    private Ticket tail;     // tail.next = head
    private int count;

    // Add a reservation at the end
    public void addTicket(int id, String customer, String movie, int seat, String time) {
        Ticket node = new Ticket(id, customer, movie, seat, time);
        if (head == null) {
            head = tail = node;
            node.next = node;
        } else {
            tail.next = node;
            node.next = head;
            tail = node;
        }
        count++;
    }

    public boolean removeTicket(int id) {
        if (head == null) {
            System.out.println("No tickets booked.");
            return false;
        }
        Ticket prev = tail;
        Ticket temp = head;
        for (int i = 0; i < count; i++) {
            if (temp.ticketId == id) {
                if (count == 1) {
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
                temp.next = null;
                count--;
                return true;
            }
            prev = temp;
            temp = temp.next;
        }
        return false;
    }

    public void display() {
        if (head == null) {
            System.out.println("No tickets booked.");
            return;
        }
        Ticket temp = head;
        do {
            System.out.println(temp);
            temp = temp.next;
        } while (temp != head);
    }

    // Search by customer name or movie name
    public void search(String keyword, boolean byCustomer) {
        boolean found = false;
        if (head != null) {
            Ticket temp = head;
            do {
                String value = byCustomer ? temp.customerName : temp.movieName;
                if (value.equalsIgnoreCase(keyword)) {
                    System.out.println(temp);
                    found = true;
                }
                temp = temp.next;
            } while (temp != head);
        }
        if (!found) {
            System.out.println("No ticket found for '" + keyword + "'");
        }
    }

    public int totalTickets() {
        return count;
    }

    public static void main(String[] args) {
        TicketReservation system = new TicketReservation();
        system.addTicket(1, "Aman", "Inception", 12, "2026-11-24 18:00");
        system.addTicket(2, "Riya", "Dangal", 5, "2026-11-24 19:30");
        system.addTicket(3, "Karan", "Inception", 13, "2026-11-24 18:00");
        system.addTicket(4, "Neha", "3 Idiots", 8, "2026-11-25 20:00");

        System.out.println("--- Current tickets ---");
        system.display();
        System.out.println("Total booked tickets = " + system.totalTickets());

        System.out.println("\n--- Search by movie: Inception ---");
        system.search("Inception", false);
        System.out.println("--- Search by customer: Riya ---");
        system.search("Riya", true);
        System.out.println("--- Search by customer: Zoya ---");
        system.search("Zoya", true);

        System.out.println("\n--- Remove ticket 1 (first), 4 (last), 99 (missing) ---");
        System.out.println(system.removeTicket(1));
        System.out.println(system.removeTicket(4));
        System.out.println(system.removeTicket(99));
        system.display();
        System.out.println("Total booked tickets = " + system.totalTickets());

        System.out.println("\n--- Remove all remaining ---");
        system.removeTicket(2);
        system.removeTicket(3);
        system.display();
        System.out.println("Total booked tickets = " + system.totalTickets());
    }
}
