package edu.gmu.swe.gameproj.mechanics.cards.treasure;

import edu.gmu.swe.gameproj.mechanics.cards.CardType;

public class Gold extends Treasure {

	private final static Gold INSTANCE = new Gold();
	
	private Gold() {
		super(CardType.Gold, 3);
	}

    public static Gold getInstance(){
        return INSTANCE;
    }
}
