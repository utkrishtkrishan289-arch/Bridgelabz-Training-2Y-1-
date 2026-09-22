public class leetcode209 {

    public static int minSubArrayLen(int target, int[] nums) {
        int start = 0;
        int sum = 0;
        int min = Integer.MAX_VALUE;

        for( int end = 0;end<nums.length;end++){
            sum = sum + nums[end];
            while(sum>=target){
                min = Math.min(min,end-start+1);
                sum = sum - nums[start];
                start++;
            }
        }
        if(min == Integer.MAX_VALUE){
            return 0;
        }
        return min;

    }

    public static void main(String[] args) {
        int [] arr = {2,3,1,2,4,3};
        int target = 7;
        int result = minSubArrayLen(target,arr);
        System.out.print(result);

    }
}
