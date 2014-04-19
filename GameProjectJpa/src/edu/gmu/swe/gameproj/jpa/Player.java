package edu.gmu.swe.gameproj.jpa;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
    private int actionCount;
    private int buyCount;
    private int coinCount;
    private int treasure;
    private ArrayList<String> hand;
    private ArrayList<String> deck;
    private ArrayList<String> discard;

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

    public boolean hasCard(String cardName)
    {
        if(cardName == null) throw new NullPointerException();
        return hand.contains(cardName);
    }

    public void draw(int drawCount)
    {
        int i = 0;
        while(i < drawCount)
        {
            if(deck.size() == 0) shuffle();
            String cardName = deck.get(0);
            deck.remove(0);
            hand.add(cardName);
            i++;
        }
    }

    public void discard(ArrayList<String> discardNames)
    {
        //TODO Implement
    }

    public void addCard(String cardName)
    {
        //TODO Implement
    }

    public void trashCard(String cardName)
    {
        //TODO Implement
    }

    public void buyCard(String cardName, int cost){
        discard.add(cardName);
        treasure -= cost;
        buyCount--;
    }

    private void shuffle()
    {
        deck = (ArrayList<String>)discard.subList(0,(discard.size() -1));
        discard.clear();
        Collections.shuffle(deck);
    }

}
