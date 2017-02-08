/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uvaadhoc;

import static java.lang.System.out;
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
    static void permute(int []v, int n, int i) // 1 to n 
    {
        if (i==n) {
            if ( validateQueens(v)) {
                for (int k:v)
                    out.print(k+" ");
                out.println();
            }
        }
        else 
            for (int j=i; j<n; j++) {
                swap(v, i, j);
                permute(v, n, i+1);
                swap(v, i, j);
            }
    }
    void fullSearch(int firstPos)
    {
        Arrays.fill(board, 0);
        board[0] = firstPos;
        List<Integer> columnsOpen = new LinkedList<>();
        Collections.fill(columnsOpen, 0);
        for (int i=1; i<board.length; i++) { // try to find position for next row
            for (int j=0; j<i; j++) { // 
                
            }
        }
    }
    
    public static void main(String[] args)
    {
        permute(new int[]{1,2,3,4,5,6,7,8}, 8, 0);
    }
}
