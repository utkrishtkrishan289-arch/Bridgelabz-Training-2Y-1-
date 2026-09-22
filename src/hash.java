
import java.util.*;
public class hash {
    static void main(String[] args) {
        HashMap<String, Integer> studentAges = new HashMap<>();
        studentAges.put("Rahul", 21);
        studentAges.put("Mayank", 22);
        studentAges.put("Ayushi", 20);
        System.out.println("Ayushi age: " + studentAges.get("Ayushi"));
        studentAges.put("Ayushi",21);

    }
}