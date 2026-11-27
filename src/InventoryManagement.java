package linkedlist;

// Problem 4: Inventory Management System using a Singly Linked List
public class InventoryManagement {

    static class Item {
        String name;
        int id;
        int quantity;
        double price;
        Item next;

        Item(String name, int id, int quantity, double price) {
            this.name = name;
            this.id = id;
            this.quantity = quantity;
            this.price = price;
        }

        @Override
        public String toString() {
            return String.format("ID %d | %-10s | Qty %-3d | Price %.2f", id, name, quantity, price);
        }
    }

    private Item head;
    private int size;

    public void addAtBeginning(String name, int id, int qty, double price) {
        Item node = new Item(name, id, qty, price);
        node.next = head;
        head = node;
        size++;
    }

    public void addAtEnd(String name, int id, int qty, double price) {
        Item node = new Item(name, id, qty, price);
        if (head == null) {
            head = node;
        } else {
            Item temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = node;
        }
        size++;
    }

    public void addAtPosition(int position, String name, int id, int qty, double price) {
        if (position < 1 || position > size + 1) {
            System.out.println("Invalid position: " + position);
            return;
        }
        if (position == 1) {
            addAtBeginning(name, id, qty, price);
            return;
        }
        Item temp = head;
        for (int i = 1; i < position - 1; i++) {
            temp = temp.next;
        }
        Item node = new Item(name, id, qty, price);
        node.next = temp.next;
        temp.next = node;
        size++;
    }

    public boolean removeById(int id) {
        if (head == null) {
            return false;
        }
        if (head.id == id) {
            head = head.next;
            size--;
            return true;
        }
        Item temp = head;
        while (temp.next != null && temp.next.id != id) {
            temp = temp.next;
        }
        if (temp.next == null) {
            return false;
        }
        temp.next = temp.next.next;
        size--;
        return true;
    }

    public boolean updateQuantity(int id, int newQuantity) {
        for (Item temp = head; temp != null; temp = temp.next) {
            if (temp.id == id) {
                temp.quantity = newQuantity;
                return true;
            }
        }
        return false;
    }

    public Item searchById(int id) {
        for (Item temp = head; temp != null; temp = temp.next) {
            if (temp.id == id) {
                return temp;
            }
        }
        return null;
    }

    public Item searchByName(String name) {
        for (Item temp = head; temp != null; temp = temp.next) {
            if (temp.name.equalsIgnoreCase(name)) {
                return temp;
            }
        }
        return null;
    }

    public double totalValue() {
        double total = 0;
        for (Item temp = head; temp != null; temp = temp.next) {
            total += temp.price * temp.quantity;
        }
        return total;
    }

    // Sort using merge sort. sortBy: "name" or "price"
    public void sort(String sortBy, boolean ascending) {
        head = mergeSort(head, sortBy, ascending);
    }

    private Item mergeSort(Item start, String sortBy, boolean ascending) {
        if (start == null || start.next == null) {
            return start;
        }
        // Find the middle using slow and fast pointers
        Item slow = start;
        Item fast = start.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Item second = slow.next;
        slow.next = null;
        Item left = mergeSort(start, sortBy, ascending);
        Item right = mergeSort(second, sortBy, ascending);
        return merge(left, right, sortBy, ascending);
    }

    private Item merge(Item a, Item b, String sortBy, boolean ascending) {
        Item dummy = new Item("", 0, 0, 0);
        Item tail = dummy;
        while (a != null && b != null) {
            int cmp = sortBy.equals("name") ? a.name.compareToIgnoreCase(b.name)
                    : Double.compare(a.price, b.price);
            if (!ascending) {
                cmp = -cmp;
            }
            if (cmp <= 0) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
    }

    public void display() {
        if (head == null) {
            System.out.println("Inventory is empty.");
            return;
        }
        for (Item temp = head; temp != null; temp = temp.next) {
            System.out.println(temp);
        }
    }

    public static void main(String[] args) {
        InventoryManagement inv = new InventoryManagement();
        inv.addAtEnd("Keyboard", 101, 10, 500.0);
        inv.addAtEnd("Mouse", 102, 25, 250.0);
        inv.addAtBeginning("Monitor", 103, 5, 8000.0);
        inv.addAtPosition(2, "Cable", 104, 50, 100.0);
        inv.display();

        System.out.println("\nTotal inventory value = " + inv.totalValue());

        System.out.println("\n--- Update quantity of 102 to 30 ---");
        System.out.println(inv.updateQuantity(102, 30));
        System.out.println("Search id 102   : " + inv.searchById(102));
        System.out.println("Search 'monitor': " + inv.searchByName("monitor"));
        System.out.println("Search id 999   : " + inv.searchById(999));

        System.out.println("\n--- Sort by name ascending ---");
        inv.sort("name", true);
        inv.display();
        System.out.println("\n--- Sort by price descending ---");
        inv.sort("price", false);
        inv.display();

        System.out.println("\n--- Remove 104 ---");
        System.out.println(inv.removeById(104));
        inv.display();
        System.out.println("Total inventory value = " + inv.totalValue());
    }
}
