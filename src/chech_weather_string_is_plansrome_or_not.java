import java.util.Scanner;

     public class chech_weather_string_is_plansrome_or_not {
         public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int start = 0;
        int end = s.length() - 1;
        boolean ispalendrome = true;
    while(start<end)

        {
            if (s.charAt(start) != s.charAt(end)) {
                ispalendrome = false;
            }
            start++;
            end--;
        }
    System.out.print(ispalendrome);

    }
}
