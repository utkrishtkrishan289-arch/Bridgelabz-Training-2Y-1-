import java.util.Scanner;

public class bubble_short {
   public static void main() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int [] nums = new int[n];
        for(int i =0;i<n;i++){
            nums[i]=sc.nextInt();
        }
        for(int  i= 0;i<nums.length;i++){
            for(int j =0;j<nums.length-1-i;j++){
                if(nums[j]>nums[j+1]){
                    int temp = nums[j];
                    nums[j]=nums[j+1];
                    nums[j+1]=temp;

                }
            }
        }
        for(int i = 0;i<nums.length;i++){
            System.out.print(nums[i]+" ");
        }

    }
}
