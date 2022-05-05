package Sandbox;

import java.util.*;

public class Sandbox {
    public static void main(String[] arg) {

    }

    //    1041. Robot Bounded In Circle
    public boolean isRobotBounded(String instructions) {
        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int x = 0, y = 0, dir = 0;
        char[] chars = instructions.toCharArray();
        for (int iter = 0; iter < 4; iter++) {
            for (char c : chars) {
                switch (c) {
                    case 'L' -> dir = (dir + 3) % 4;
                    case 'R' -> dir = (dir + 1) % 4;
                    case 'G' -> {
                        x += dirs[dir][0];
                        y += dirs[dir][1];
                    }
                }
            }
            if (x == 0 && y == 0) return true;
        }
        return false;
    }

    /**
     * 215. Kth Largest Element in an Array
     * Smart! Think outside the box!
     * Quick select: https://www.youtube.com/watch?v=XEmy13g1Qxc
     */
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int num : nums) {
            heap.add(num);
            if (heap.size() > k) heap.poll();
        }
        return heap.peek();
    }

    //    202. Happy Number
    public boolean isHappy(int n) {
        Set<Integer> inLoop = new HashSet<>();
        int squareSum, remain;
        while (inLoop.add(n)) {
            squareSum = 0;
            while (n > 0) {
                remain = n % 10;
                squareSum += remain * remain;
                n /= 10;
            }
            if (squareSum == 1) return true;
            n = squareSum;

        }
        return false;
    }

    //    287
    public int findDuplicateBitSet(int[] nums) {
        BitSet bitset = new BitSet((int) Math.pow(10, 5));
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
}
