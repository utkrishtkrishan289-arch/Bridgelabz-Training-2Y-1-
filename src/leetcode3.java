import java.util.ArrayList;

public class leetcode3 {
    public static int lengthOfLongestSubstring(String s) {
        int start =0;
        int max =0;
        int end =0;
        ArrayList<Character> list = new ArrayList<>();
        while(end<s.length()){
            if(!list.contains(s.charAt(end))){
                list.add(s.charAt(end));
                end++;
                max=Math.max(max, list.size());
            }
            else{
                list.remove(Character.valueOf(s.charAt(start)));
                start++;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        String sc = "abcabcbb";
       int val =  lengthOfLongestSubstring(sc);
       System.out.print(val);



    }
}

