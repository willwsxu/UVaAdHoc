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
    
    int []board;
    NQueen(int n)
    {
        board = new int[n];
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
                int hdist = j-i;
                int vdist = v[i]-v[j];
                if (hdist==vdist || hdist == -vdist)
                    return false;
            }
        }
        return true;
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
    static void print(int []v)
    {/*
        for (int k:v)
            out.print(k+" ");
        out.println();
        */
    }
    static int loops=0;
    static void permute(int []v, int n, int i) // i to n 
    {
        if (i==n) {
            if ( validateQueens(v)) 
                print(v);
        }
        else 
            for (int j=i; j<n; j++) {
                loops++;
                swap(v, i, j);
                permute(v, n, i+1);
                swap(v, i, j);
            }
    }
    static int loops2=0;
    static void enhancedPermute(int []v, int n, int i)
    {
        if (i==n)
            print(v);
        else {
            for (int c=0; c<n; c++) {
                loops2++;
                if (validateQueens(v, i, c)) {
                    v[i]=c;
                    enhancedPermute(v, n, i+1);
                }
            }
        }
    }
    
    public static void main(String[] args)
    {
        Instant start = Instant.now();
        permute(new int[]{1,2,3,4,5,6,7,8}, 8, 0);
        Instant end = Instant.now();
        out.println("permute takes "+ChronoUnit.MICROS.between(start, end)+" loops "+loops);    //3-4 msec
        start = Instant.now();
        enhancedPermute(new int[]{1,2,3,4,5,6,7,8}, 8, 0);
        end = Instant.now();
        out.println("enhancedPermute takes "+ChronoUnit.MICROS.between(start, end)+" loops "+loops2);  //1 msec
    }
}