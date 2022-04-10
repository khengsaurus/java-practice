public class BSQns {
    public static void main(String[] args) {
        BSQns sln = new BSQns();
        int res = sln.guessNumber(2);
        System.out.println(res);
    }
    public BSQns(){};
    //    374. Guess Number Higher or Lower
    public int guess(int n, int guess) {
        if (n == guess) return 0;
        if (n > guess) return -1;
        return 1;
    }

    public int guessNumber(int n) {
        int min = 1, max = n, mid = min + (max - min) / 2;
        while (min <= max) {
            mid = min + (max - min) / 2;
            switch (guess(mid, 2)) {
                case 0:
                    return mid;
                case 1:
                    min = mid + 1;
                    break;
                case -1:
                    max = mid - 1;
                    break;
            }
        }
        return mid;
    }
}
