/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uvaadhoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author WXU
 */
// UVa 00462
public class BridgeHand {
    class Suit {
        public Suit(String s) {
            name = s;
        }
        public boolean stopped()
        {
            if (cards.isEmpty())
                return false;
            if (cards.get(0)==14)  // Ace
                return true;
            if (cards.get(0)==13) { // King + one card
                return cards.size()>1?true:false;
            }
            if (cards.get(0)==12) { // Queen + 2 cards
                return cards.size()>2?true:false;
            }
            return false;
        }
        public int getScoreNoTrump()
        {
            return scoreNoTrump;
        }
        public int length()
        {
            return cards.size();
        }
        
        public int scoreTrump()
        {
            int points=0;
            switch (cards.size()) {
                case 0:
                    return 2;
                case 1:     points += 2;    break;
                case 2:     points += 1;    break;
            }
            return points+scoreNoTrump();
        }
        private int scoreNoTrump()
        {
            if (cards.isEmpty() || sorted)
                return scoreNoTrump;
            int points=0;
            if ( !sorted) {
                Comparator<Integer> cmp = Comparator.comparingInt(i->i);
                cmp = cmp.reversed();
                Collections.sort(cards, cmp);
                sorted=true;
            }
            for (int val : cards) {
                switch (val) {
                    case 14:    
                        points += 4;
                        break;
                    case 13:    
                        points += 3;
                        if ( cards.size()==1)
                            points--;
                        break;
                    case 12:    
                        points += 2;
                        if ( cards.size()<=2)
                            points--;
                        break;
                    case 11:    
                        points += 1;
                        if ( cards.size()<=3)
                            points--;
                        break;
                }
                if ( val<11 )
                    break;
            }
            scoreNoTrump = points;
            return scoreNoTrump;
        }
        public void addCard(char c) {
            sorted=false;
            int cardVal=0;
            switch(c) {
                case 'A':   cardVal=14;     break;
                case 'K':   cardVal=13;     break;
                case 'Q':   cardVal=12;     break;
                case 'J':   cardVal=11;     break;
                case 'T':   cardVal=10;     break;
                case '9':   
                case '8':   
                case '7':   
                case '6':   
                case '5':   
                case '4':   
                case '3':   
                case '2':   
                    cardVal=c-'0';
                    break;
                default:
                    cardVal=c;
                    System.out.println("bad card " + c);
            }
            cards.add(cardVal);
        }
        List<Integer> cards = new ArrayList<>();// sorted from hight to low
        String name;
        boolean sorted=false;
        private int scoreNoTrump=0;
    }
    public String evaluateBid(String hand)
    {
        Suit suits[] = new Suit[]{new Suit("S"), new Suit("H"), new Suit("D"), new Suit("C")};
        String cards[] = hand.split(" ");
        if ( cards.length != 13)
            System.out.println("bad hand");
        for (String c: cards) {
            switch(c.charAt(1)) {
                case 'S':   suits[0].addCard(c.charAt(0));      break;
                case 'H':   suits[1].addCard(c.charAt(0));      break;
                case 'D':   suits[2].addCard(c.charAt(0));      break;
                case 'C':   suits[3].addCard(c.charAt(0));      break;
                default:
                    System.out.println("bad card "+ c);
            }
        }
        int totalScoreTrump=0;
        int totalScoreNoTrump=0;
        for (Suit s : suits) {
            totalScoreTrump += s.scoreTrump();
            totalScoreNoTrump += s.getScoreNoTrump();
        }
        if (totalScoreTrump<14)        
            return "PASS";
        if (totalScoreNoTrump>=16 ) {
            if (suits[0].stopped() && suits[1].stopped() && suits[2].stopped() && suits[3].stopped())
                return "BID NO_TRUMP";
        }
        String bid="";
        int len=0;
        for (Suit s : suits) {
            if(s.length()>len) {
                len = s.length();
                bid = "BID "+s.name;
            }
        }
        return bid;
    }
    public static void main(String[] args) {
        // TODO code application logic here
        BridgeHand bridge = new BridgeHand();
        System.out.println(bridge.evaluateBid("KS QS TH 8H 4H AC QC TC 5C KD QD JD 8D"));
        System.out.println(bridge.evaluateBid("AC 3C 4C AS 7S 4S AD TD 7D 5D AH 7H 5H "));         
    }
}
