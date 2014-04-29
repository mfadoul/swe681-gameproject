package edu.gmu.swe.gameproj.mechanics.cards;

public abstract class Card {
	protected Card(CardType cardType) {
		this.cardType = cardType;
	}
	
	public final CardType cardType;
}
