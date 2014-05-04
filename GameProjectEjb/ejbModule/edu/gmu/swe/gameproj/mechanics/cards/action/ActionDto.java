package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.util.ArrayList;

import edu.gmu.swe.gameproj.jpa.Card;
import edu.gmu.swe.gameproj.jpa.Player;


public class ActionDto {
    public Player player;
    public int playerId;
    public ArrayList<Card> discardCards;
    public Card oldCard;
    public Card newCard;
}
