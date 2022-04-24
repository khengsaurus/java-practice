import java.util.*;

public class FBQns {
    public static void main(String[] args) {
    }

    public int getUniformIntegerCountInInterval(long A, long B) {
        // Write your code here
        long tmp = 0;
        int ans = 0;
        long numDigits = (long) Math.log10(B) + 1L;
        for (int i = 0; i < numDigits; i++) {
            tmp = tmp * 10 + 1; // 1, 11, 111...
            if (tmp * 9 >= A) {
                for (int j = 1; j < 10; j++) {
                    Long t = tmp * j;
                    if (A <= t && t <= B) {
                        ans++;
                    }
                }
            }
        }
        return ans;
    }

    /**
     * N = 8
     * C = .PBAAP.B
     * X = 1
     * Y = 3
     * Expected Return Value = 3
     * <p>
     * N = 5
     * C = APABA
     * X = 1
     * Y = 2
     * Expected Return Value = 1
     */
    public static long getArtisticPhotographCount(int N, String C, int X, int Y) {
        // Write your code here
        long[] prefixP = new long[N + 1];
        long[] prefixB = new long[N + 1];
        long[] prefixA = new long[N + 1];
        long[] prefixI = new long[N + 1];
        for (int i = 0; i < N; i++) {
            prefixP[i + 1] = prefixP[i] + (C.charAt(i) == 'P' ? 1 : 0);
            prefixB[i + 1] = prefixB[i] + (C.charAt(i) == 'B' ? 1 : 0);
            prefixA[i + 1] = C.charAt(i) == 'A' ? 1 : 0;
            prefixI[i + 1] = prefixI[i] + 1;
        }
        long count = 0;
        for (int i = 0; i < N; i++) {
            if (C.charAt(i) == 'A') {
                int leftS = Math.max(i - Y, 0); // prefix sum, need to compare the diff between end of range (last index) and before STARt of range (0 - 1)th index
                int leftE = Math.max(i - X + 1, 0);
                int rightS = Math.min(i + X, N);
                int rightE = Math.min(i + Y + 1, N);
                count += (prefixP[leftE] - prefixP[leftS]) * (prefixB[rightE] - prefixB[rightS]);
                count += (prefixB[leftE] - prefixB[leftS]) * (prefixP[rightE] - prefixP[rightS]);
            }
        }
        return count;
    }


    public static int getArtisticPhotographCount2(int N, String C, int X, int Y) {
        // Write your code here
        int count = 0;
        int[] P = new int[N + 1];
        int[] B = new int[N + 1];
        for (int i = 1; i < N + 1; i++) {
            Character c = C.charAt(i - 1);
            P[i] = P[i - 1] + (c == 'P' ? 1 : 0);
            B[i] = B[i - 1] + (c == 'B' ? 1 : 0);
        }
        for (int i = 0; i < N; i++) {
            if (C.charAt(i) == 'A') {
                int fstart = (i + X) <= N ? (i + X) : N;
                int fend = (i + Y + 1) <= N ? (i + Y + 1) : N;
                int bend = (i - X + 1) >= 0 ? (i - X + 1) : 0;
                int bstart = (i - Y) >= 0 ? (i - Y) : 0;
                count += (P[fend] - P[fstart]) * (B[bend] - B[bstart]);
                count += (B[fend] - B[fstart]) * (P[bend] - P[bstart]);
            }
        }
        return count;
    }

    public int getArtisticPhotographCount1(int N, String C, int X, int Y) {
        // Write your code here
        int count = 0;
        int lenC = C.length();
        for (int S = 0; S < lenC - X; S++) {
            Character end = '.';
            if (C.charAt(S) == 'P') {
                end = 'B';
            } else if (C.charAt(S) == 'B') {
                end = 'P';
            }
            if (end != '.') {
                for (int A = S + X; A <= S + Y && A < lenC; A++) {
                    if (C.charAt(A) == 'A') {
                        for (int E = A + X; E <= A + Y && E < lenC; E++) {
                            if (C.charAt(E) == end) {
                                count++;
                                continue;
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    public static int getMinProblemCount(int N, int[] S) {
        // Write your code here
        int max = 0;
        int hasOdd = 0;
        for (int s : S) {
            max = Math.max(max, s);
            if (s % 2 == 1) {
                hasOdd = 1;
            }
        }
        return (max / 2) + hasOdd;
    }

    public static long getMinCodeEntryTime(int N, int M, int[] C) {
        // Write your code here
        int curr = 1;
        int time = 0;
        for (int i : C) {
            time += getSeconds(curr, i, N);
            curr = i;
        }
        return time;
    }

    private static int getSeconds(int from, int to, int max) {
        if (from == to) {
            return 0;
        }
        int diff = Math.abs(from - to);
        return diff <= (max / 2) ? diff : max % diff;
    }

    public static int getMaximumEatenDishCount(int N, int[] D, int K) {
        // Write your code here
        int count = 0;
        // N traversal
        // + O(K): time exceeded when using LL only, with K lookup
        // O(1): LL add/remove, hashmap lookup
        Queue<Integer> FIFO = new LinkedList<>();
        Map<Integer, Boolean> withinK = new HashMap<>();
        for (int i : D) {
//            if (!FIFO.contains(i)) { // K lookup
            if (withinK.get(i) == null) {
                if (FIFO.size() == K) {
                    int toRemove = FIFO.remove();
                    withinK.remove(toRemove);
                }
                withinK.put(i, true);
                FIFO.add(i);
                count++;
            }
        }

        return count;
    }

    public static long getMaxAdditionalDinersCount(long N, long K, int M, long[] S) {
        if (M == 0) {
            return helper(0, M, K);
        } else {
            Arrays.sort(S);
            long before = helper(1, S[0] - K - 1, K);
            long after = helper(S[M - 1] + K + 1, N, K);
            long more = before + after;
            for (int i = 0; i < M - 1; i++) {
                long n = helper(S[i] + K + 1, S[i + 1] - K - 1, K);
                more += n;
            }
            return more;
        }
    }

    public static long helper(long start, long end, long K) {
//        long division - int division used, i.e.round down
//        +1 to 'ceil'
        return end >= start ? 1 + (end - start) / (K + 1) : 0;
    }

    public static double getHitProbability(int R, int C, int[][] G) {
        double ships = 0;
        for (int[] i : G) {
            for (int j : i) {
                ships += j;
            }
        }
        return ships / (Double.valueOf(R) * Double.valueOf(C));
    }
}
