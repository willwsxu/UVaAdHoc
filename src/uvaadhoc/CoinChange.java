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
    int ways=0;
    Set<Integer> answer = new TreeSet<>();
    
    // cfind the total number of different ways of making changes
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
    int change2(int remain)
    {
        if (remain==0) {
            return 0;
        }
        if (remain <0)
            return Integer.MAX_VALUE;
        int result=Integer.MAX_VALUE;
        for (int i=0; i<coinType.length; i++) {
            int res = change2(remain-coinType[i]);
            if ( res < result)
                result = res;
        }
        return 1+result;
    }
    
    public int coins(int cents, int[]coinType)
    {
        this.coinType = coinType;
        //Arrays.sort(coinType); // should reverse sort
        Instant start = Instant.now();
        //change(cents, 0);
        coinCount = change2(cents);
        Instant end = Instant.now();
        out.println("takes "+ChronoUnit.MICROS.between(start, end)+" min coins "+coinCount+" ways "+ways+answer);
        return 0;
    }
    
    public static void main(String[] args)
    {
        new CoinChange().coins(27, new int[]{50, 25, 10, 5,1});
    }
}

