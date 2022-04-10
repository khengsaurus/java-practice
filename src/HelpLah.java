import java.util.Arrays;

public class HelpLah {
    public static void main(String[] args) {
        int res = maxProfit(new int[]{8, 1, 8, 2, 6}, 2);
        System.out.println(res);
    }

    //    714 - Best Time to Buy and Sell Stock with Transaction Fee
    public static int maxProfit(int[] prices, int fee) {
        int cash = 0, hold = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            cash = Math.max(cash, hold + prices[i] - fee);
            hold = Math.max(hold, cash - prices[i]);
        }
        return cash;
    }
}
