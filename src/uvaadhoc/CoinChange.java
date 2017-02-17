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
    public int coins(int cents, int[]coinType)
    {
        this.coinType = coinType;
        //Arrays.sort(coinType); // should reverse sort
        Instant start = Instant.now();
        change(cents, 0);
        Instant end = Instant.now();
        out.println("takes "+ChronoUnit.MICROS.between(start, end)+" min coins "+coinCount+" ways "+ways+answer);
        return 0;
    }
    
    public static void main(String[] args)
    {
        new CoinChange().coins(26, new int[]{50, 25, 10, 5,1});
    }
}

