import java.util.*;

public class Algo {
    public static void main(String[] args) {
    }

    // 567 - Check if one of s1's permutations is in a substring of s2
    public boolean checkInclusion(String s1, String s2) {
        int len1 = s1.length();
        if (s2.length() < s1.length()) return false;
        int[] freq1 = charFrequency(s1);
        int[] freq2 = charFrequency(s2.substring(0, len1));
        if (match(freq1, freq2)) return true;
        for (int i = len1; i < s2.length(); i++) {
            freq2[s2.charAt(i) - 'a']++;
            freq2[s2.charAt(i - len1) - 'a']--;
            if (match(freq1, freq2)) return true;
        }
        return false;
    }

    public int[] charFrequency(String s) {
        int[] cs = new int[26];
        for (int i = 0; i < s.length(); i++) cs[s.charAt(i) - 'a']++;
        return cs;
    }

    public boolean match(int[] freq1, int[] freq2) {
        for (int i = 0; i < freq1.length; i++) {
            if (freq1[i] != freq2[i]) return false;
        }
        return true;
    }

    //    557
    public static void reverse(char[] chars, int start, int end) {
        while (start <= end) {
            char t = chars[start];
            chars[start] = chars[end];
            chars[end] = t;
            start++;
            end--;
        }
    }

    public static String reverseWordsBetter(String s) {
        char[] chars = s.toCharArray();
        int left = 0, right = 0;
        while (right < chars.length) {
            if (chars[right] == ' ') {
                reverse(chars, left, right - 1);
                left = right + 1;
            }
            right++;
        }
        reverse(chars, left, right - 1);
        return new String(chars);
    }

    public static String reverseWords(String s) {
        int len = s.length();
        int left = 0;
        int right = 0;
        char[] chars = new char[len];
        while (right < len) {
            if (s.charAt(right) == ' ') {
                chars[right] = ' ';
                for (int i = 0; i < right - left; i++) {
                    chars[left + i] = s.charAt(right - i - 1);
                }
                left = right + 1;
            }
            right++;
        }
        if (left < right) {
            for (int i = 0; i < right - left; i++) {
                chars[left + i] = s.charAt(right - i - 1);
            }
        }
        return new String(chars);
    }

    // 344
    public static void reverseString(char[] s) {
        int len = s.length;
        for (int i = 0; i < len / 2; i++) {
            char t = s[i];
            s[i] = s[len - i - 1];
            s[len - i - 1] = t;
        }
    }

    //    180
    public static void rotate(int[] nums, int k) {
        int len = nums.length;
        k = k % len;
        if (k != 0) {
            int[] holders = new int[k];
            for (int i = 0; i < k; i++) {
                holders[k - i - 1] = nums[len - 1 - i];
            }
            for (int i = len - 1; i >= 0; i--) {
                if (i < k) {
                    nums[i] = holders[i];
                } else {
                    nums[i] = nums[i - k];
                }
            }
        }
    }

    //    977
    public static int[] sortedSquares(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        int index = end;
        int[] res = new int[nums.length];
        while (start <= end) {
            if (Math.abs(nums[start]) > Math.abs(nums[end])) {
                res[index--] = nums[start] * nums[start];
                start++;
            } else {
                res[index--] = nums[end] * nums[end];
                end--;
            }
        }
        return res;
    }

    //    35
    public static int searchInsert(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) { //
            int mid = l + ((r - l) / 2);
            if (nums[mid] == target) return mid;
            if (target < nums[mid]) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return r + 1;
    }

    //    278
    public static boolean isBadVersion(int n) {
        return n >= 2;
    }

    public static int firstBadVersion(int n) {
        int start = 1;
        int end = n;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (isBadVersion(mid)) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }

    //    704
    public static int binarySearch(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) return mid;
            if (nums[mid] > target) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return -1;
    }

    public static int binarySearch(int[] nums, int start, int end, int target) {
        int mid = start + ((end - start) / 2);
        if (nums[mid] == target) return mid;
        if (end <= start) return -1;
        return target < nums[mid]
                ? binarySearch(nums, start, mid - 1, target)
                : binarySearch(nums, mid + 1, end, target);
    }

    //    TODO: 4 - https://www.youtube.com/watch?v=q6IEA26hvXc
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            int[] t = nums1;
            nums1 = nums2;
            nums2 = t;
        }
        if (nums1.length == 0) {
            int len2 = nums2.length;
            return len2 % 2 == 0
                    ? (double) (nums2[len2 / 2] + nums2[len2 / 2 + 1]) / 2
                    : nums2[len2 / 2];
        }

        int partSmallLen = Math.max(1, nums1.length / 2);
        int partBigLen = (nums1.length + nums2.length) / 2 - partSmallLen;

        int intAfterSmall = partSmallLen == nums1.length ? nums2[partBigLen] : nums1[partSmallLen];
        int intAfterBig = partBigLen == nums2.length ? nums1[partSmallLen] : nums2[partBigLen];
        boolean _canShrinkSmall = partSmallLen != 0 && partBigLen != nums2.length;
        boolean _canShrinkBig = partBigLen != 0 && partSmallLen != nums1.length;

        while (_canShrinkBig) {
            if (nums1[partSmallLen] < nums2[partBigLen]) {
                partSmallLen++;
                partBigLen--;
            } else if (nums2[partBigLen] < nums1[partSmallLen]) {
                partBigLen++;
                partSmallLen--;
            }
            _canShrinkBig = partSmallLen != nums1.length && partBigLen != nums2.length;
        }
        return 0;
    }

    //    122
    public static int maxProfit2(int[] prices) {
        int max = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            int priceToday = prices[i];
            int priceTmr = prices[i + 1];
            if (priceTmr > priceToday) max += (priceTmr - priceToday);
        }
        return max;
    }

    //    121
    public static int maxProfit(int[] prices) {
//        int max = 0, left = 0, right = 1;
//        while (right < prices.length) {
//            int diff = prices[right] - prices[left];
//            if (diff > 0) {
//                max = Math.max(max, diff);
//            } else {
//                left = right;
//            }
//            right++;
//        }
        int max = 0, localMin = Integer.MAX_VALUE, localMax = 0;
        for (int i = 0; i < prices.length; i++) {
            int dayPrice = prices[i];
            if (dayPrice < localMin) {
                localMin = dayPrice;
                localMax = 0;
            } else if (dayPrice > localMax) {
                localMax = dayPrice;
                max = Math.max(max, localMax - localMin);
            }
        }
        return max;
    }

    //    350
    public static int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0, j = 0, k = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                nums1[k++] = nums1[i++];
                j++;
            } else if (nums2[j] > nums1[i]) {
                i++;
            } else {
                j++;
            }
        }

        return Arrays.copyOfRange(nums1, 0, k);
//        return res.stream().mapToInt(Integer::intValue).toArray();

//        int[] result = new int[res.size()];
//        for (int k = 0; k < res.size(); j++) {
//            result[k] = res.get(k);
//        }
//        return result;
    }
}
