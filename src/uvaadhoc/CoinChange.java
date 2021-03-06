/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uvaadhoc;

import static java.lang.System.out;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author WXU
 */
public class CoinChange {
    int []coinType;
    int coinCount=Integer.MAX_VALUE;
    long ways=0;
    Set<Integer> answer = new TreeSet<>();
    
    // find the total number of different ways of making changes
    void change(int remain, int count)
    {
        if (remain==0) {
            ways++;
            answer.add(count);
            if (coinCount>count)
                coinCount=count;
            return;
        }
        if (remain <0)
            return;
        for (int i=0; i<coinType.length; i++)
            change(remain-coinType[i], count+1);
    }
    
    // change to get minimal coins
    // too slow for 100cents coins, without using dynamic programming
    int dpCount[];
    private int changeMin(int remain)
    {
        if (remain <0)
            return Integer.MAX_VALUE;
        if (dpCount[remain]>=0)   // dynamic programming
            return dpCount[remain];
        int result=Integer.MAX_VALUE;
        for (int i=0; i<coinType.length; i++) {
            int res = changeMin(remain-coinType[i]);
            if ( res < result)
                result = res;
        }
        dpCount[remain] = result==Integer.MAX_VALUE?Integer.MAX_VALUE:1+result;
        return dpCount[remain];
    }
    public int coinChangeRecur(int[] coins, int amount) // recursive
    {
        this.coinType = coins;
        dpCount = new int[amount+1];
        Arrays.fill(dpCount, -1);
        dpCount[0]=0;
        return changeMin(amount)==Integer.MAX_VALUE?-1:changeMin(amount);
    }
    
    // given coins of different denominations and a total amount of money
    // find fewest coins to make up that amount
    public int coinChange(int[] coins, int amount) {  // beat 95% leetcode
        int MAX_VAL=amount+1;
        int dp[]=new int[MAX_VAL]; // index on all money amount
        dp[0]=0;
        for (int i=1; i<=amount; i++) {
            int count=MAX_VAL;
            for (int j=0; j<coins.length; j++) {
                if (coins[j]>i)  // coin value too big
                    continue;
                count = Integer.min(count, 1+dp[i-coins[j]]);
            }
            dp[i]=count;
        }
        //System.out.println(Arrays.toString(dp));
        return dp[amount]>amount?-1:dp[amount];
    }
    
    // ways of making changes
    // implementation without recursion, Dynamic programming
    long changeWaysNoRecur(int cents, int[]coinType)
    {
        long D[] = new long[cents+1];
        D[0]=D[1]=1;
        for (int i=2; i<=cents; i++) {
            D[i]=0;
            for (int j=0; j<coinType.length; j++) {
                if (i>=coinType[j])
                    D[i] += D[i-coinType[j]];
            }
            //out.println("cents "+i+" ways "+D[i]);
        }
        return D[cents];
    }
    
    public int coins(int cents, int[]coinType)
    {
        this.coinType = coinType;
        //Arrays.sort(coinType); // should reverse sort
        //change(cents, 0);
        //out.println("change "+ways+" min count "+coinCount+answer);
        
        //coinCount = changeMin(cents);
        //out.println("changeMin "+coinCount);
        
        ways = changeWaysNoRecur(cents, coinType);
        out.println("changeWaysNoRecur "+ways);
        return 0;
    }
    
    public static void main(String[] args)
    {
        Instant start = Instant.now();
        // overflows from cents=146
        new CoinChange().coins(145, new int[]{50, 25, 10, 5,1});
        Instant end = Instant.now();
        out.println("change takes "+ChronoUnit.MICROS.between(start, end));
        
        System.out.println(new CoinChange().coinChange(new int[]{1,2,5}, 11)==3);
        System.out.println(new CoinChange().coinChange(new int[]{7,5}, 11)==-1);
        System.out.println(new CoinChange().coinChange(new int[]{1,7,5}, 4)==4);
        System.out.println(new CoinChange().coinChange(new int[]{1,5, 10, 25}, 100)==4);
        
        System.out.println(new CoinChange().coinChangeRecur(new int[]{1,2,5}, 11)==3);
        System.out.println(new CoinChange().coinChangeRecur(new int[]{7,5}, 11)==-1);
        System.out.println(new CoinChange().coinChangeRecur(new int[]{1,7,5}, 4)==4);
        System.out.println(new CoinChange().coinChangeRecur(new int[]{1,5, 10, 25}, 100)==4);
    }
}

