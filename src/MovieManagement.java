package linkedlist;

// Problem 2: Movie Management System using a Doubly Linked List
public class MovieManagement {

    static class Movie {
        String title;
        String director;
        int year;
        double rating;
        Movie next;
        Movie prev;

        Movie(String title, String director, int year, double rating) {
            this.title = title;
            this.director = director;
            this.year = year;
            this.rating = rating;
        }

        @Override
        public String toString() {
            return String.format("%-15s | %-18s | %d | %.1f", title, director, year, rating);
        }
    }

    private Movie head;
    private Movie tail;
    private int size;

    public void addAtBeginning(String title, String director, int year, double rating) {
        Movie node = new Movie(title, director, year, rating);
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    public void addAtEnd(String title, String director, int year, double rating) {
        Movie node = new Movie(title, director, year, rating);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    // Position starts from 1
    public void addAtPosition(int position, String title, String director, int year, double rating) {
        if (position < 1 || position > size + 1) {
            System.out.println("Invalid position: " + position);
            return;
        }
        if (position == 1) {
            addAtBeginning(title, director, year, rating);
        } else if (position == size + 1) {
            addAtEnd(title, director, year, rating);
        } else {
            Movie temp = head;
            for (int i = 1; i < position - 1; i++) {
                temp = temp.next;
            }
            Movie node = new Movie(title, director, year, rating);
            node.next = temp.next;
            node.prev = temp;
            temp.next.prev = node;
            temp.next = node;
            size++;
        }
    }

    // Remove by title
    public boolean removeByTitle(String title) {
        Movie temp = head;
        while (temp != null && !temp.title.equalsIgnoreCase(title)) {
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
        temp.next = null;
        temp.prev = null;
        size--;
        return true;
    }

    public void searchByDirector(String director) {
        boolean found = false;
        for (Movie temp = head; temp != null; temp = temp.next) {
            if (temp.director.equalsIgnoreCase(director)) {
                System.out.println(temp);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No movie found for director " + director);
        }
    }

    public void searchByRating(double rating) {
        boolean found = false;
        for (Movie temp = head; temp != null; temp = temp.next) {
            if (temp.rating == rating) {
                System.out.println(temp);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No movie found with rating " + rating);
        }
    }

    public boolean updateRating(String title, double newRating) {
        for (Movie temp = head; temp != null; temp = temp.next) {
            if (temp.title.equalsIgnoreCase(title)) {
                temp.rating = newRating;
                return true;
            }
        }
        return false;
    }

    public void displayForward() {
        if (head == null) {
            System.out.println("No movies.");
            return;
        }
        for (Movie temp = head; temp != null; temp = temp.next) {
            System.out.println(temp);
        }
    }

    public void displayReverse() {
        if (tail == null) {
            System.out.println("No movies.");
            return;
        }
        for (Movie temp = tail; temp != null; temp = temp.prev) {
            System.out.println(temp);
        }
    }

    public static void main(String[] args) {
        MovieManagement list = new MovieManagement();
        list.addAtEnd("Inception", "Christopher Nolan", 2010, 8.8);
        list.addAtBeginning("Dangal", "Nitesh Tiwari", 2016, 8.4);
        list.addAtEnd("Interstellar", "Christopher Nolan", 2014, 8.7);
        list.addAtPosition(2, "3 Idiots", "Rajkumar Hirani", 2009, 8.4);

        System.out.println("--- Forward ---");
        list.displayForward();
        System.out.println("\n--- Reverse ---");
        list.displayReverse();

        System.out.println("\n--- Search by director: Christopher Nolan ---");
        list.searchByDirector("Christopher Nolan");
        System.out.println("\n--- Search by rating: 8.4 ---");
        list.searchByRating(8.4);

        System.out.println("\n--- Update rating of Dangal to 9.0 ---");
        System.out.println(list.updateRating("Dangal", 9.0) ? "Updated" : "Movie not found");

        System.out.println("\n--- Remove 3 Idiots (middle) and Interstellar (tail) ---");
        System.out.println(list.removeByTitle("3 Idiots"));
        System.out.println(list.removeByTitle("Interstellar"));
        list.displayForward();
        System.out.println("--- Reverse after removal ---");
        list.displayReverse();
    }
}
