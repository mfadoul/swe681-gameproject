package edu.gmu.swe.gameproj.mechanics.cards.action;



import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.mechanics.cards.Card;

public abstract class Action extends Card {

	protected GameProjectRemote gameProject;
	protected Action(CardType _cardType, GameProjectRemote _gameProject) {
		super(_cardType);
		if(gameProject == null) throw new NullPointerException("_gameProject");
		gameProject = _gameProject;
	}

	public abstract void act (ActionDto dto) throws Exception;
	protected abstract boolean validate(ActionDto dto);
	
	
}
