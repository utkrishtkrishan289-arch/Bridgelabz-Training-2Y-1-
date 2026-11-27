package linkedlist;

import java.util.ArrayList;
import java.util.List;

// Problem 7: Social Media Friend Connections using a Singly Linked List
public class SocialMediaFriends {

    static class User {
        int userId;
        String name;
        int age;
        List<Integer> friendIds = new ArrayList<>();   // list of friend IDs
        User next;

        User(int userId, String name, int age) {
            this.userId = userId;
            this.name = name;
            this.age = age;
        }
    }

    private User head;

    public void addUser(int userId, String name, int age) {
        User node = new User(userId, name, age);
        if (head == null) {
            head = node;
            return;
        }
        User temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = node;
    }

    public User searchById(int userId) {
        for (User temp = head; temp != null; temp = temp.next) {
            if (temp.userId == userId) {
                return temp;
            }
        }
        return null;
    }

    public User searchByName(String name) {
        for (User temp = head; temp != null; temp = temp.next) {
            if (temp.name.equalsIgnoreCase(name)) {
                return temp;
            }
        }
        return null;
    }

    // Add a two-way friend connection
    public boolean addFriend(int id1, int id2) {
        User a = searchById(id1);
        User b = searchById(id2);
        if (a == null || b == null || id1 == id2) {
            System.out.println("Invalid users for connection: " + id1 + ", " + id2);
            return false;
        }
        if (a.friendIds.contains(id2)) {
            System.out.println("Already friends: " + id1 + " and " + id2);
            return false;
        }
        a.friendIds.add(id2);
        b.friendIds.add(id1);
        return true;
    }

    public boolean removeFriend(int id1, int id2) {
        User a = searchById(id1);
        User b = searchById(id2);
        if (a == null || b == null || !a.friendIds.contains(id2)) {
            return false;
        }
        a.friendIds.remove(Integer.valueOf(id2));
        b.friendIds.remove(Integer.valueOf(id1));
        return true;
    }

    public List<Integer> mutualFriends(int id1, int id2) {
        List<Integer> mutual = new ArrayList<>();
        User a = searchById(id1);
        User b = searchById(id2);
        if (a == null || b == null) {
            return mutual;
        }
        for (int friendId : a.friendIds) {
            if (b.friendIds.contains(friendId)) {
                mutual.add(friendId);
            }
        }
        return mutual;
    }

    public void displayFriends(int userId) {
        User user = searchById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        System.out.print("Friends of " + user.name + ": ");
        if (user.friendIds.isEmpty()) {
            System.out.println("none");
            return;
        }
        for (int friendId : user.friendIds) {
            System.out.print(searchById(friendId).name + " ");
        }
        System.out.println();
    }

    public void countFriends() {
        for (User temp = head; temp != null; temp = temp.next) {
            System.out.println(temp.name + " (ID " + temp.userId + ") has " + temp.friendIds.size() + " friend(s)");
        }
    }

    public static void main(String[] args) {
        SocialMediaFriends network = new SocialMediaFriends();
        network.addUser(1, "Aman", 20);
        network.addUser(2, "Riya", 21);
        network.addUser(3, "Karan", 22);
        network.addUser(4, "Neha", 19);

        System.out.println("--- Add connections ---");
        network.addFriend(1, 2);
        network.addFriend(1, 3);
        network.addFriend(2, 3);
        network.addFriend(2, 4);
        network.addFriend(1, 2);     // duplicate
        network.addFriend(1, 9);     // missing user

        network.displayFriends(1);
        network.displayFriends(2);

        System.out.println("\n--- Mutual friends of Aman (1) and Neha (4) ---");
        System.out.println(network.mutualFriends(1, 4));
        System.out.println("Mutual friends of Aman (1) and Riya (2): " + network.mutualFriends(1, 2));

        System.out.println("\n--- Search ---");
        User byName = network.searchByName("karan");
        System.out.println("By name 'karan': " + (byName != null ? "ID " + byName.userId + ", age " + byName.age : "not found"));
        User byId = network.searchById(4);
        System.out.println("By ID 4: " + (byId != null ? byId.name : "not found"));

        System.out.println("\n--- Remove connection 1-2 ---");
        System.out.println(network.removeFriend(1, 2));
        network.displayFriends(1);

        System.out.println("\n--- Friend counts ---");
        network.countFriends();
    }
}
