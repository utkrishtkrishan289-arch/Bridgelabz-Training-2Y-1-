import java.util.*;
public class remove_element {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for(int i =0;i<n;i++){
            nums[i] =sc.nextInt();
        }
        int k = 0;
        for(int i =1;i<nums.length;i++){
            if(nums[i]!=nums[k]){
                nums[k+1]=nums[i];
                k++;
            }
        }
        for(int i = 0;i<=k;i++){
            System.out.print(nums[i]+" ");

        }

    }
}
