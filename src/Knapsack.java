
import static java.lang.Long.max;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

// start with just one person
public class Knapsack {
    int price[];    // 1 to 100
    int weight[];   // 1 to 30
    int maxWeight[];// 1 to 30
    long memo[][];
    static final int MAX_WEIGHT=30;
    Knapsack(int N)
    {
        memo = new long[N][MAX_WEIGHT+1];
        for (long[] m: memo)
            Arrays.fill(m, -1);
        price = new int[N];
        weight = new int[N];
        for (int i=0; i<N; i++) {
            price[i] = scan.nextInt();
            weight[i] = scan.nextInt();
        }
        int M=scan.nextInt(); //# of sacks, 1 to 100
        maxWeight = new int[M];
        for (int j=0; j<M; j++)
            maxWeight[j] = scan.nextInt();
        out.println(topdownValue(N-1, maxWeight[0]));
    }
    
    // item from 0 to N-1
    long topdownValue(int item, int allowedweight)
    {
        if (item<0)
            return 0;
        if ( allowedweight<0)
            return 0;
        if ( memo[item][allowedweight]>=0)
            return memo[item][allowedweight];
        memo[item][allowedweight]=topdownValue(item-1, allowedweight);
        if (allowedweight>=weight[item]) {
            long w = price[item]+topdownValue(item-1, allowedweight-weight[item]);
            if (memo[item][allowedweight]<w)
                memo[item][allowedweight]=w;
        }
        return memo[item][allowedweight];
    }
    static Scanner scan = new Scanner(System.in);
    public static void autoTest()
    {
        int TC = scan.nextInt();  // between 1 and 10^3
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();  // between 1 to 1000
            new Knapsack(N);
        }
    }
    public static void main(String[] args)
    {
        //scan = codechef.CodeChef.getFileScanner("cards0317.txt");
        //Instant start = Instant.now();
        autoTest();
        //Instant end = Instant.now();
        //out.println("usec "+ChronoUnit.MICROS.between(start, end));       
    } 
}
