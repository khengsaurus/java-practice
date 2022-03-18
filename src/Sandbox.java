import java.util.*;

public class Sandbox {
    public static void main(String[] arg) {
//        int[] nums = new int[]{3, -4, 1, 0, -1};
//        int[] nums = new int[]{-1, 0, 1, 0};
//        int[] nums = new int[]{-2, -2, 0, 0, 0, 0, 1, 1, 1, 2};
//        int[] height = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
//        String s = "pwwkew";
//        int res = lengthOfLongestSubstringBetter(s);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
//        node1.next(node2);
//        node2.next(node3);
//        node3.next(node4);
//        node4.next(node5);
//        node5.next(null);
//        Node res = copyRandomList(node1);

    }

    Map<Node, Node> map = new HashMap<>();

    public Node copyRandomList(Node head) {
        if (head == null) return null;
        Node newNode = new Node(head.val);
        map.put(head, newNode);
        newNode.next = copyRandomList(head.next);
        newNode.random = map.get(head.random);
        return newNode;
    }



    public static int maxArea(int[] height) {
        int globalMax = 0;
        int i = 0;
        int j = height.length - 1;
        while (i < j) {
            int h = Math.min(height[i], height[j]);
            globalMax = Math.max(globalMax, h * (j - i));
            if (height[i] == height[j]) {
                i++;
                j--;
            } else if (height[i] == h) {
                i++;
            } else {
                j--;
            }
        }
        return globalMax;
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length > 2) {
            Arrays.sort(nums);
            for (int i = 0; i < nums.length - 2; i++) {
                if (i > 0 && nums[i] == nums[i - 1]) {
                    // skip duplicates from left
                    continue;
                } else {
                    int l = i + 1;
                    int r = nums.length - 1;
                    while (l < r) {
                        int sum = nums[i] + nums[l] + nums[r];
                        if (sum == 0) {
                            res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                            r--;
                            while (r > l && nums[r] == nums[r + 1]) {
                                r--;
                            }
                        } else if (sum > 0) {
                            r--;
                        } else {
                            l++;
                        }
                    }
                }
            }
        }
        return res;
    }

    //    5, 1, 2, 3, 4 - l
//    3, 4, 5, 1, 2 - r
    public static int findMin(int[] nums) {
        // Array not rotated or has 1 element, 0th
        if (nums.length == 1 || nums[0] < nums[nums.length - 1]) {
            return nums[0];
        }
        int l = 0;
        int r = nums.length - 1;
        while (r > l) {
            if (r == l + 1) {
                // two numbers left, could be 1,2 or 2,1
                return Math.min(nums[l], nums[r]);
            }
            int mid = l + (r - l) / 2;
            if (nums[mid] >= nums[l]) {
                l = mid;
            } else {
                r = mid;
            }
        }
        return nums[r];
    }

    public static int maxProduct(int[] nums) {
        int localMax = nums[0];
        int localMin = nums[0];
        int globalMax = localMax;
        int tempMax;
        for (int i = 1; i < nums.length; i++) {
            tempMax = localMax;
            localMax = Math.max(nums[i], Math.max(tempMax * nums[i], localMin * nums[i]));
            localMin = Math.min(nums[i], Math.min(tempMax * nums[i], localMin * nums[i]));
            globalMax = Math.max(globalMax, localMax);
        }
        return globalMax;
    }

    public static int maxSubArray(int[] nums) {
        int maxSum = nums[0];
        int currSum = maxSum;
        for (int i = 1; i < nums.length; i++) {
            currSum = Math.max(nums[i] + currSum, nums[i]);
            maxSum = Math.max(currSum, maxSum);
        }
        return maxSum;
    }

    public static int[] ProductExceptSelf(int[] nums) {
        int[] ret = new int[nums.length];

        //Step 1:
        int temp = 1;
        for (int i = 0; i < nums.length; i++) {
            ret[i] = temp;
            temp *= nums[i];
        }

        temp = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            ret[i] *= temp;
            temp *= nums[i];
        }
        //Step 3:
        return ret;

    }

    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (!set.add(nums[i])) {
                return true;
            }
        }
        return false;
    }

    public static int maxProfit(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        if (prices.length == 2) {
            if (prices[1] > prices[0]) {
                return prices[1] - prices[0];
            }
            return 0;
        }
        int localMin = prices[0];
        int localMax = prices[1];
        int globalMax = Math.max(0, localMax - localMin);
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < localMin) {
                localMin = prices[i];
                localMax = 0;
            }
            if (prices[i] > localMax) {
                localMax = prices[i];
                globalMax = Math.max(globalMax, localMax - localMin);
            }
        }
        return globalMax;
    }

    public static void litmusSwap(int i, int j) {
        int t = i;
        i = j;
        j = t;
        System.out.println("IN THE FUNCTION:");
        System.out.println(i);
        System.out.println(j);
    }

    public static int removeDuplicates(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }
        int n = 0;
        int largest = nums[nums.length - 1];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == largest) {
                nums[n] = nums[i];
                n++;
                return n;
            }
            if (i == 0 || nums[i] != nums[i - 1]) {
                nums[n] = nums[i];
                n++;
            }
        }
        return n;
    }

    public static int removeElement(int[] nums, int val) {
        if (nums.length == 0) {
            return 0;
        }
        int p = 0;
        int i = 0;
        while (i < nums.length) {
            if (nums[i] != val) {
                nums[p] = nums[i];
                i++;
                p++;
            } else {
                i++;
            }
        }
        return p;
    }

    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null && list2 == null) {
            return null;
        }
        ListNode head = null;
        ListNode temp = null;
        if (list1 == null || (list2 != null && list2.val < list1.val)) {
            temp = list2.next;
            head = list2;
            list2 = temp;
        } else {
            temp = list1.next;
            head = list1;
            list1 = temp;
        }
        ListNode current = head;
        while (list1 != null || list2 != null) {
            if (list1 == null || (list2 != null && list2.val < list1.val)) {
                temp = list2.next;
                current.next = list2;
                current = current.next;
                list2 = temp;
            } else {
                temp = list1.next;
                current.next = list1;
                current = current.next;
                list1 = temp;
            }
        }
        return head;
    }
}
