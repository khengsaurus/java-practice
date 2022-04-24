import java.util.Arrays;

public class BinarySearches {
    public static void main(String[] args) {
    }

    public static int findValOrNextSmallest(int[] nums, int target) {
        int l = 0, r = nums.length;
        while (l < r) {
            int m = (l + r) / 2;
            if (nums[m] == target) return m;
            if (nums[m] > target) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return l - 1;
    }

    public static int findFirst(int[] nums, int val) {
        int l = 0, r = nums.length - 1;
        boolean found = false;
        while (l < r) {
            int mid = (l + r) / 2;
            if (nums[mid] == val) found = true;
            if (nums[mid] >= val) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return found ? l : -1;
    }

    public static int findLast(int[] nums, int target) {
        int l = 0, r = nums.length;
        boolean found = false;
        while (l <= r) {
            int m = (l + r) / 2;
            if (nums[m] == target) found = true;
            if (nums[m] > target) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return found ? l : -1;
    }

//    public static int findLast(int[] nums, int val) {
//        int index = -1, l = 0, r = nums.length - 1;
//        while (l <= r) {
//            int mid = (l + r) / 2;
//            if (nums[mid] <= val) {
//                l = mid + 1;
//            } else {
//                r = mid - 1;
//            }
//            if (nums[mid] == val) index = mid;
//        }
//
//        return index;
//    }

    public static int binarySearch(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (nums[m] == target) return m;
            if (nums[m] > target) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return -1;
    }
}
