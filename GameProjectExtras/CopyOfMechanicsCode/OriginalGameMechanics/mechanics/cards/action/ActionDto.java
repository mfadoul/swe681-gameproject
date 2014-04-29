package edu.gmu.swe.gameproj.mechanics.cards.action;

import edu.gmu.swe.gameproj.mechanics.Player;

import java.util.ArrayList;

public class ActionDto {
    public Player player;
    //private Card playedCard;
    public ArrayList<String> discardCardNames;
    public String oldCardName;
    public String newCardName;


//    public ActionDto(Player _player){
//        player = _player;
//        //playedCard = _playedCard;
//    }
//
//    public ActionDto(Player _player, ArrayList<Card> _discardCards){
//        player = _player;
//        //playedCard = _playedCard;
//        discardCardNames = _discardCards;
//    }
//
//    public ActionDto(Player _player, Card _oldCard, Card _newCard){
//        player = _player;
//        //playedCard = _playedCard;
//        oldCardName = _oldCard;
//        newCardName = _newCard;
//    }
//
//    public Player getPlayer() {
//        return player;
//    }
////    public Card getPlayedCard() {
////        return playedCard;
////    }
//    public ArrayList<Card> getDiscardCards() {
//        return discardCardNames;
//    }
//    public Card getOldCard() {
//        return oldCardName;
//    }
//    public Card getNewCard() {
//        return newCardName;
//    }
}
