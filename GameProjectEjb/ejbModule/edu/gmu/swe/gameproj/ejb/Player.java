package edu.gmu.swe.gameproj.ejb;

import edu.gmu.swe.gameproj.ejb.cards.Card;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
    private int actionCount;
    private int buyCount;
    private int coinCount;
    private int treasure;
    private ArrayList<Card> hand;
    private ArrayList<Card> deck;
    private ArrayList<Card> discard;

//    public void setCoinCount(int value) {
//        coinCount = value;
//    }

    public int getCoinCount() {
        return coinCount;
    }

    public void addCoinCount(int addAmount){
        coinCount += addAmount;
    }

//    public void setBuyCount(int value) {
//        buyCount = value;
//    }

    public int getBuyCount() {
        return buyCount;
    }

    public void addBuyCount(int addAmount) {
        buyCount += addAmount;
    }

//    public void setActionCount(int value) {
//        actionCount = value;
//    }

    public int getActionCount() {
        return actionCount;
    }



    public void addActionCount(int addAmount)
    {
        actionCount += addAmount;
    }

    public void addTreasure(int amount) { treasure += amount;}

    public boolean hasCard(Card card)
    {
        if(card == null) throw new NullPointerException();
        return hand.contains(card);
    }

    public void draw(int drawCount)
    {
        int i = 0;
        while(i < drawCount)
        {
            if(deck.size() == 0) shuffle();
            Card card = deck.get(0);
            deck.remove(0);
            hand.add(card);
            i++;
        }
    }

    public void discard(ArrayList<Card> discards)
    {
        //TODO Implement
    }

    public void addCard(Card card)
    {
        //TODO Implement
    }

    public void trashCard(Card card)
    {
        //TODO Implement
    }

    private void shuffle()
    {
        deck = (ArrayList<Card>)discard.subList(0,(discard.size() -1));
        discard.clear();
        Collections.shuffle(deck);
    }

}
