package linkedlist;

// Problem 5: Library Management System using a Doubly Linked List
public class LibraryManagement {

    static class Book {
        String title;
        String author;
        String genre;
        int bookId;
        boolean available;
        Book next;
        Book prev;

        Book(String title, String author, String genre, int bookId, boolean available) {
            this.title = title;
            this.author = author;
            this.genre = genre;
            this.bookId = bookId;
            this.available = available;
        }

        @Override
        public String toString() {
            return String.format("ID %d | %-18s | %-16s | %-8s | %s", bookId, title, author, genre,
                    available ? "Available" : "Issued");
        }
    }

    private Book head;
    private Book tail;
    private int count;

    public void addAtBeginning(String title, String author, String genre, int id, boolean available) {
        Book node = new Book(title, author, genre, id, available);
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        count++;
    }

    public void addAtEnd(String title, String author, String genre, int id, boolean available) {
        Book node = new Book(title, author, genre, id, available);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        count++;
    }

    public void addAtPosition(int position, String title, String author, String genre, int id, boolean available) {
        if (position < 1 || position > count + 1) {
            System.out.println("Invalid position: " + position);
            return;
        }
        if (position == 1) {
            addAtBeginning(title, author, genre, id, available);
        } else if (position == count + 1) {
            addAtEnd(title, author, genre, id, available);
        } else {
            Book temp = head;
            for (int i = 1; i < position - 1; i++) {
                temp = temp.next;
            }
            Book node = new Book(title, author, genre, id, available);
            node.next = temp.next;
            node.prev = temp;
            temp.next.prev = node;
            temp.next = node;
            count++;
        }
    }

    public boolean removeById(int id) {
        Book temp = head;
        while (temp != null && temp.bookId != id) {
            temp = temp.next;
        }
        if (temp == null) {
            return false;
        }
        if (temp.prev != null) {
            temp.prev.next = temp.next;
        } else {
            head = temp.next;
        }
        if (temp.next != null) {
            temp.next.prev = temp.prev;
        } else {
            tail = temp.prev;
        }
        temp.next = temp.prev = null;
        count--;
        return true;
    }

    // Search by title or author (partial, case-insensitive match)
    public void search(String keyword, boolean byTitle) {
        boolean found = false;
        for (Book temp = head; temp != null; temp = temp.next) {
            String value = byTitle ? temp.title : temp.author;
            if (value.toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(temp);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No book found for '" + keyword + "'");
        }
    }

    public boolean updateAvailability(int id, boolean available) {
        for (Book temp = head; temp != null; temp = temp.next) {
            if (temp.bookId == id) {
                temp.available = available;
                return true;
            }
        }
        return false;
    }

    public void displayForward() {
        if (head == null) {
            System.out.println("Library is empty.");
        }
        for (Book temp = head; temp != null; temp = temp.next) {
            System.out.println(temp);
        }
    }

    public void displayReverse() {
        if (tail == null) {
            System.out.println("Library is empty.");
        }
        for (Book temp = tail; temp != null; temp = temp.prev) {
            System.out.println(temp);
        }
    }

    public int totalBooks() {
        return count;
    }

    public static void main(String[] args) {
        LibraryManagement lib = new LibraryManagement();
        lib.addAtEnd("Wings of Fire", "A. P. J. Kalam", "Biography", 1, true);
        lib.addAtEnd("Clean Code", "Robert Martin", "Tech", 2, true);
        lib.addAtBeginning("The Alchemist", "Paulo Coelho", "Fiction", 3, true);
        lib.addAtPosition(2, "Java Basics", "Herbert Schildt", "Tech", 4, false);

        System.out.println("--- Forward ---");
        lib.displayForward();
        System.out.println("\n--- Reverse ---");
        lib.displayReverse();

        System.out.println("\n--- Search title 'clean' ---");
        lib.search("clean", true);
        System.out.println("--- Search author 'paulo' ---");
        lib.search("paulo", false);

        System.out.println("\n--- Issue book 2 ---");
        System.out.println(lib.updateAvailability(2, false));
        System.out.println("\n--- Remove book 3 (head) and 1 (tail) ---");
        System.out.println(lib.removeById(3));
        System.out.println(lib.removeById(1));
        lib.displayForward();
        System.out.println("Reverse:");
        lib.displayReverse();
        System.out.println("\nTotal books = " + lib.totalBooks());
    }
}
