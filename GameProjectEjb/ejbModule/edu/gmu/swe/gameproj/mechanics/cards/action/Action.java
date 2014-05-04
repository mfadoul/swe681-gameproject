package edu.gmu.swe.gameproj.mechanics.cards.action;

import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.mechanics.cards.Card;

public abstract class Action extends Card {

	protected Action(CardType cardType) {
		super(cardType);
	}

	public abstract void act (ActionDto dto);
	protected abstract boolean validate(ActionDto dto);
}
