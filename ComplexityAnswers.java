import java.util.Arrays;

/**
 * DSA Practice Sheet - Time & Space Complexity (TC-1..TC-3, SC-1..SC-3)
 * Answers are written in the comments above each method.
 */
public class ComplexityAnswers {

    /*
     * ================= TC-1: Linear Search Time Complexity =================
     * Sample array: {8, 61, 2, 3, 4, 0}   (n = 6)
     *
     * 1) Best Case: target = 8. It is at index 0, so only 1 comparison is done.
     *    Time = O(1).
     *
     * 2) Worst Case: target = 0 (last element) or 5 (not present).
     *    All n = 6 elements are checked, so n comparisons are done.
     *    Time = O(n).
     *
     * 3) Average Case: target is at a random position. On average about
     *    (1 + 2 + ... + n) / n = (n + 1) / 2 comparisons are done.
     *    (n + 1) / 2 is proportional to n, so Time = O(n).
     */
    static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /*
     * ================= TC-2: Iterative Binary Search =================
     * Sorted array: {0, 2, 3, 4, 8, 61}   indexes 0..5, target = 4
     *
     * 1) Step by step:
     *    Step 1: low=0, high=5, mid=(0+5)/2=2, arr[2]=3.  3 < 4 -> go right, low=3
     *    Step 2: low=3, high=5, mid=(3+5)/2=4, arr[4]=8.  8 > 4 -> go left,  high=3
     *    Step 3: low=3, high=3, mid=3,         arr[3]=4.  Found at index 3.
     *    Search range: 6 elements -> 3 elements -> 1 element.
     *
     * 2) Worst case equation:
     *    Each step halves the search space:  n, n/2, n/4, ..., n/2^k
     *    The search stops when the space is 1:  n / 2^k = 1
     *        => 2^k = n
     *        => k = log2(n)
     *    So the worst case needs about floor(log2 n) + 1 comparisons.
     *    Time = O(log n).   (Best case O(1), when target is the first mid.)
     */
    static int binarySearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /*
     * ================= TC-3: Merge Sort Time Complexity =================
     * Sample array: {8, 61, 2, 3, 4, 0}
     *
     * 1) Division / split tree:
     *
     *                  [8, 61, 2, 3, 4, 0]
     *                 /                   \
     *          [8, 61, 2]               [3, 4, 0]
     *          /       \                /       \
     *      [8, 61]     [2]           [3, 4]     [0]
     *      /    \                    /    \
     *    [8]   [61]                [3]    [4]
     *
     *    Merge back up:
     *    [8],[61] -> [8,61]   then [8,61],[2] -> [2,8,61]
     *    [3],[4]  -> [3,4]    then [3,4],[0]  -> [0,3,4]
     *    [2,8,61],[0,3,4] -> [0,2,3,4,8,61]
     *
     * 2) Recurrence:   T(n) = 2T(n/2) + n,   T(1) = 1
     *    Recursion tree (work per level = cost of merging):
     *      Level 0: 1 problem  of size n    -> work = n
     *      Level 1: 2 problems of size n/2  -> work = 2 * (n/2) = n
     *      Level 2: 4 problems of size n/4  -> work = 4 * (n/4) = n
     *      ...
     *      Level k: 2^k problems of size n/2^k -> work = n
     *    Tree height: n / 2^k = 1 => k = log2(n), so there are (log2 n + 1) levels.
     *    Total work = n * log2(n)   (+ n for the leaves)
     *    Time = O(n log n)   (the same for best, average and worst case).
     */
    static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];   // extra memory
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            temp[k++] = (arr[i] <= arr[j]) ? arr[i++] : arr[j++];
        }
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        for (int x = 0; x < temp.length; x++) {
            arr[left + x] = temp[x];
        }
    }

    /*
     * ================= SC-1: Linear Search Space Complexity =================
     * 1) Auxiliary space = O(1).
     *    Iterative linear search only uses a few variables (i, target, result)
     *    no matter how big the array is. No new array and no recursion are used.
     *    (The input array itself is not counted as auxiliary space.)
     *
     * ================= SC-2: Binary Search Space Complexity =================
     * 1) Iterative Binary Search auxiliary space = O(1).
     *    Only low, high and mid are used, and they do not grow with n.
     *    (Recursive binary search would use O(log n) because of the call stack.)
     *
     * ================= SC-3: Merge Sort Space Complexity =================
     * 1) Merging two sorted halves cannot be done safely in the same array without
     *    overwriting values that are not yet compared. So a temporary array is
     *    created to hold the merged result, and then copied back.
     *    At the last merge this temp array has size n.
     *    Auxiliary space = O(n)  (plus O(log n) recursion stack, which is smaller
     *    than n, so the total is still O(n)).
     *
     * 2) Comparison with in-place sorting:
     *    - Insertion Sort: O(1) extra space (shifts elements inside the array).
     *    - Quick Sort:     O(log n) extra space on average (recursion stack only).
     *    - Merge Sort:     O(n) extra space, so it uses the most memory of the three.
     *    Merge Sort is guaranteed O(n log n) time, but it pays for it with extra
     *    memory. Insertion/Quick Sort are better when memory is limited.
     */

    public static void main(String[] args) {
        int[] arr = {8, 61, 2, 3, 4, 0};

        System.out.println("--- TC-1 Linear Search ---");
        System.out.println("target 8 (best)  -> index " + linearSearch(arr, 8));
        System.out.println("target 0 (worst) -> index " + linearSearch(arr, 0));
        System.out.println("target 5 (absent)-> index " + linearSearch(arr, 5));

        System.out.println("--- TC-2 Binary Search ---");
        int[] sorted = {0, 2, 3, 4, 8, 61};
        System.out.println("target 4 -> index " + binarySearch(sorted, 4));

        System.out.println("--- TC-3 Merge Sort ---");
        System.out.println("Before: " + Arrays.toString(arr));
        mergeSort(arr, 0, arr.length - 1);
        System.out.println("After : " + Arrays.toString(arr));
    }
}
