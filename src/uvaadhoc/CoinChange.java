/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uvaadhoc;

import static java.lang.System.out;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
    // too slow for 100cents coins
    int changeMin(int remain)
    {
        if (remain==0) {
            return 0;
        }
        if (remain <0)
            return Integer.MAX_VALUE;
        int result=Integer.MAX_VALUE;
        for (int i=0; i<coinType.length; i++) {
            int res = changeMin(remain-coinType[i]);
            if ( res < result)
                result = res;
        }
        return 1+result;
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
    }
}

