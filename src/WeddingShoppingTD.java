import static java.lang.Integer.max;
import static java.lang.System.out;
import java.util.Scanner;

// Dynamic programming
class WeddingShoppingTD {
    int M;  // money available, 1<= M <= 200
    int C;  // garments to buy, 1 <= C <= 20
    int [][] price= new int[25][25];//price per garment per model, 1<=model<=20
    
    final int MAX_M = 210;
    final int MAX_G = 25;
    
    void init(int M, int C)
    {
        this.M = M;
        this.C = C;
        for (int i=0; i< C; i++) {
            price[i][0] = scan.nextInt(); // use first elem to store # of models
            for (int j=1; j<=price[i][0]; j++)
                price[i][j] = scan.nextInt();
        }
    }
    static Scanner scan = new Scanner(System.in);    
    static void autotest(WeddingShoppingTD shop)
    {
        int TC = scan.nextInt();
        for (int i=0; i<TC; i++) {
            int M = scan.nextInt();
            int C = scan.nextInt();
            shop.run(M, C);
        }        
    }
    
    int[][] memo = new int[MAX_M][MAX_G];

    // money available to shop for garment g
    int shop(int money, int g)
    {
        if (money<0)  // not enough money
            return -100000000;
        if (g==C)     // bought all
            return M-money;  // money used to buy all garments
        if ( memo[money][g] != -1)
            return memo[money][g];  // seen this money on this garment before
        int ans = -1;
        for (int i=1; i<=price[g][0]; i++) // price[g][0] contains # of models
            ans = max(ans, shop(money-price[g][i], g+1));
        return memo[money][g]=ans;
    }
    
    void run(int M, int C)
    {
        init(M, C);
        for (int i=0; i<MAX_M; i++)
            for (int j=0; j<MAX_G; j++)
                memo[i][j]=-1;
        int maxUsed = shop(M, 0);
        if (maxUsed<0)
            out.println("no solution");
        else
            out.println(maxUsed);
    }
    
    public static void main(String[] args)
    {
        autotest(new WeddingShoppingTD());
    }
}

