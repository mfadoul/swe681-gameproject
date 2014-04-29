package edu.gmu.swe.gameproj.mechanics.cards.action;

import edu.gmu.swe.gameproj.mechanics.cards.Card;
import edu.gmu.swe.gameproj.mechanics.cards.CardType;

public abstract class Action extends Card {

	protected Action(CardType cardType) {
		super(cardType);
	}

	// public abstract void act ()
	// protected abstract boolean validate(ActionDto dto);
}
