import java.lang.reflect.Array;
import java.util.*;

public class Sandbox {
    public static void main(String[] arg) {
//        System.out.println(intToRoman(20));
//        System.out.println(brokenCalc(5, 8));
//        int[] r1 = new int[]{1, 1, 1, 1, 1, 1};
//        int[] r2 = new int[]{1, 1, 1, 1, 1, 1};
//        int[] r3 = new int[]{1, 1, 1, 1, 1, 1};
//        int[] r4 = new int[]{1, 1, 1, 1, 1, 1};
//        int[] res = kWeakestRows(new int[][]{r1, r2, r3, r4}, 1);
//        System.out.println(Arrays.toString(res));
//        int res = findDuplicateBetter(new int[]{2, 5, 9, 6, 9, 3, 8, 9, 7, 1});
        int res = findDuplicate(new int[]{1, 2, 3, 4, 2});
        System.out.println(res);
    }

    //    287
    public int findDuplicateBitSet(int[] nums) {
        BitSet bitset = new BitSet((int)Math.pow(10, 5));
        for (int val : nums) {
            if (bitset.get(val)) {
                return val;
            } else {
                bitset.set(val);
            }
        }
        return -1;
    }

    public static int findDuplicateTortHare(int[] nums) {
//        https://www.geeksforgeeks.org/find-any-one-of-the-multiple-repeating-elements-in-read-only-array-set-2
        int tortoise = nums[0], hare = nums[0];
        do {
            tortoise = nums[tortoise];
            hare = nums[nums[hare]];
        } while (tortoise != hare);

        tortoise = nums[0];
        while (tortoise != hare) {
            tortoise = nums[tortoise];
            hare = nums[hare];
        }
        return tortoise;
    }

    public static int findDuplicate(int[] nums) {
        boolean[] shown = new boolean[nums.length];
        for (int n : nums) {
            if (shown[n]) return n;
            shown[n] = true;
        }
        return nums[0];
    }

    //    1337
    public static int[] kWeakestRows(int[][] mat, int k) {
        Map<Integer, Queue<Integer>> map = new HashMap<>();
        int rows = mat.length, cols = mat[0].length;

        for (int row = 0; row < rows; row++) {
            int count = 0;
            int[] _row = mat[row];
            for (int j = 0; j < cols; j++) {
                if (_row[j] == 0) break;
                count++;
            }
            if (!map.containsKey(count)) {
                Queue<Integer> newQueue = new LinkedList<>();
                newQueue.add(row);
                map.put(count, newQueue);
            } else {
                map.get(count).add(row);
            }
        }

        int[] rv = new int[k];
        int count = 0;
        for (int i = 0; i < k; i++) {
            while (!map.containsKey(count) || map.get(count).isEmpty()) count++;
            rv[i] = map.get(count).remove();
        }

        return rv;
    }

    //    991 - change the target... why can't we change startValue :/
    public static int brokenCalc(int startValue, int target) {
        if (startValue >= target) return startValue - target;
        if (target % 2 == 0) {
            return 1 + brokenCalc(startValue, target / 2);
        }
        return 1 + brokenCalc(startValue, target + 1);
    }

    public static int brokenCalcIter(int startValue, int target) {
        int count = 0;
        while (target > startValue) {
            count++;
            if (target % 2 == 0) {
                target /= 2;
            } else {
                target++;
            }
        }
        return count + (startValue - target);
    }

    //    12
    public static String intToRoman(int num) {
//        19 ms	51 MB with hashmap
//        10 ms	43.4 MB when using String helper(int num) w switch case
//        Shd be faster with 2 arrays + indexing..
        Map<Integer, String> map = new HashMap<Integer, String>(Map.of(
                1, "I",
                4, "IV",
                5, "V",
                9, "IX",
                10, "X",
                40, "XL",
                50, "L",
                90, "XC",
                100, "C",
                400, "CD"
        ));
        map.put(500, "D");
        map.put(900, "CM");
        map.put(1000, "M");
        StringBuilder roman = new StringBuilder();
        int[] vals = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        for (int val : vals) {
            while (num > 0 && num >= val) {
                num -= val;
                String toAppend = map.get(val);
                roman.append(toAppend);
            }
        }
        return roman.toString();
    }

    //    763
    public static List<Integer> partitionLabelsBetter(String s) {
        int sLen = s.length();
        int[] last = new int[26];
        for (int i = 0; i < sLen; i++) {
            last[s.charAt(i) - 'a'] = i;
        }
        int left = 0;
        List<Integer> list = new ArrayList<>();

        while (left < sLen) {
            int right = last[s.charAt(left) - 'a'];
            for (int i = left; i < right; i++) {
                right = Math.max(right, last[s.charAt(i) - 'a']);
            }
            list.add(right - left + 1);
            left = right + 1;
        }
        return list;
    }

    public static List<Integer> partitionLabels(String s) {
        int sLen = s.length();
        int[] firstAppearances = new int[26]; // non 0 index of first appearance
        int[] memo = new int[sLen];
        for (int i = 0; i < sLen; i++) {
            int cValue = Character.getNumericValue(s.charAt(i)) - 10;
            int firstAppearance = firstAppearances[cValue];
            if (firstAppearance == 0) {
                firstAppearances[cValue] = i + 1;
                memo[i] = i;
            } else {
                int partitionRootIndex = firstAppearance - 1;
                int correctTo = memo[partitionRootIndex];
                for (int j = i; j > partitionRootIndex; j--) {
                    memo[j] = correctTo;
                }
            }
        }
        List<Integer> breaks = new ArrayList<>();
        int count = 1;
        for (int i = 1; i < sLen; i++) {
            if (memo[i] != memo[i - 1]) {
                breaks.add(count);
                count = 1;
            } else {
                count += 1;
            }
            if (i == sLen - 1) breaks.add(count);
        }
        return breaks;
    }

    //    1007
    public static int minDominoRotations(int[] tops, int[] bottoms) {
        int len = tops.length;
        for (int target : new int[]{tops[0], bottoms[0]}) {
            int rotateT = 0;
            int rotateB = 0;
            for (int i = 0; i < len; i++) {
                if (tops[i] != target && bottoms[i] != target) {
                    rotateT = -1;
                    break;
                }
                if (tops[i] != target) {
                    rotateT++;
                }
                if (bottoms[i] != target) {
                    rotateB++;
                }
            }
            if (rotateT != -1) {
                return Math.min(rotateT, rotateB);
            }
        }
        return -1;
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

    //    1663
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
