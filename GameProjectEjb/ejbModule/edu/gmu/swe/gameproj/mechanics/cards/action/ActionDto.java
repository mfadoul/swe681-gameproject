package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.util.ArrayList;

import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.jpa.Player;


public class ActionDto {
    public Player player;
    public int playerId;
    public ArrayList<CardType> discardCards;
    public CardType oldCard;
    public CardType newCard;
}
