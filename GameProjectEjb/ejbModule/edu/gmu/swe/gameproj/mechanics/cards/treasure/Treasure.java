package edu.gmu.swe.gameproj.mechanics.cards.treasure;

import edu.gmu.swe.gameproj.mechanics.cards.Card;
import edu.gmu.swe.gameproj.mechanics.cards.CardType;

public abstract class Treasure extends Card {
	protected Treasure(CardType cardType, int value) {
		super(cardType);
		this.value = value;
	}
	
	// The value of the card
	public int getValue () {
		return this.value;
	}
	public final int value;

}
