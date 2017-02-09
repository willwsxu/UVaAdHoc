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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author WXU
 */
public class NQueen {
    
    int []board;  // rows, store values of column
    int n;
    NQueen(int n)
    {
        board = new int[n];
        this.n = n;
    }
    
    static void swap(int []v, int i, int j)
    {
        int temp = v[j];
        v[j]=v[i];
        v[i]=temp;
    }
    static boolean validateQueens(int[] v)
    {
        for (int i=0; i<v.length-1; i++) {
            for (int j=i+1; j<v.length; j++) {
                int vdist = j-i;
                int hdist = v[i]-v[j];
                if (hdist==vdist || hdist == -vdist)
                    return false;
            }
        }
        return true;
    }
    static void print(int []v)
    {/*
        for (int k:v)
            out.print(k+" ");
        out.println();
        */
    }
    int loops=0;
    int soln=0;
    void permute(int i) // i to n 
    {
        loops++;
        if (i==n) {
            if ( validateQueens(board)) {
                print(board);
                soln++;
            }
        }
        else 
            for (int j=i; j<n; j++) {
                swap(board, i, j);
                permute(i+1);
                swap(board, i, j);
            }
    }
    void solve()
    {
        for (int i=0; i<n;i++)
            board[i] = i+1;
        Instant start = Instant.now();
        permute(0);
        Instant end = Instant.now();
        out.println("permute takes "+ChronoUnit.MICROS.between(start, end)+"nsec, loops "+loops+" answers "+soln);
    }
}

class NQueen2 extends NQueen
{
    public NQueen2(int n)
    {
        super(n);
    }
    static boolean validateQueens(int[] v, int filledRows, int candidateCol)
    {
        for (int i=0; i<filledRows; i++) {
            if (v[i]==candidateCol)
                return false;  // column is already picked
            int hdist = filledRows-i;
            int vdist = v[i]-candidateCol;
            if (hdist==vdist || hdist == -vdist)
                return false;
        }
        return true;
    }
    // enhanced backtracking
    void permute(int i)
    {
        loops++;
        if (i==n) {
            print(board);
            soln++;
        }
        else {
            for (int c=0; c<n; c++) {
                if (validateQueens(board, i, c)) {
                    board[i]=c;
                    permute(i+1);
                }
            }
        }
    }
}
    
/**
 *  Use diagonal property that r+c is same for squares on backward diagonal
 *  r-c is same for squares on forward diagonal, add n-1 to make it positive
**/
class NQueen3 extends NQueen
{
    boolean colFilled[]; 
    boolean fd[]; //foward diagnoal, r-c is same
    boolean bd[]; // backward diagonal, r+c same
    public NQueen3(int n1)
    {
        super(n1);
        colFilled = new boolean[n];
        fd = new boolean[2*n-1];
        bd = new boolean[2*n-1];
    }

    void permute(int i)
    {
        loops++;
        if (i==n) {
            print(board);
            soln++;
        }
        else {
            for (int c=0; c<n; c++) {
                if (colFilled[c])
                    continue;
                if (bd[c+i])
                    continue;
                if (fd[n-1+c-i])
                    continue;
                bd[c+i] = true;
                fd[n-1+c-i] = true;
                colFilled[c] = true;
                board[i]=c;
                permute(i+1);
                bd[c+i] = false;  // restore
                fd[n-1+c-i] = false;
                colFilled[c] = false;
            }
        }
    }
   
    
    public static void main(String[] args)
    {
        new NQueen(8).solve();
        //3-4 msec when n is 8, 9 sec when n is 12
        
        new NQueen2(12).solve();
        //1 msec when n is 8, 116 msec when n is 12, 3.9 sec when n is 14
        
        new NQueen3(14).solve();
        //0 msec when n is 8, 70 msec when n is 12, 2.4 sec when n is 14
    }
}
