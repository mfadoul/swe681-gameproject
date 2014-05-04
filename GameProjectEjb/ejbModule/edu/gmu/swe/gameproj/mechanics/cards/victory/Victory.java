package edu.gmu.swe.gameproj.mechanics.cards.victory;

import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.mechanics.cards.Card;

public abstract class Victory extends Card {

	protected Victory(CardType cardType, int victoryPoints) {
		super(cardType);
		this.victoryPoints = victoryPoints;
	}
	
	private final int victoryPoints;
	public int getVictoryPoints() {
	    return victoryPoints;
	}
}
