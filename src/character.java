import java.util.HashMap;
import java.util.Scanner;

public class character {
    static void main() {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        HashMap<Character,Integer> map = new HashMap<>();
        for(int i = 0;i<s.length();i++){
            char ch =s.charAt(i);
            map.put(ch,map.getOrDefault(ch,0)+1);


        }
        for(char r :map.keySet()){
            System.out.println(map.get(r));
        }


    }
}
