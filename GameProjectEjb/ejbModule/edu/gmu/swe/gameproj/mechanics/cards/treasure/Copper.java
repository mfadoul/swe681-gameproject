package edu.gmu.swe.gameproj.mechanics.cards.treasure;

import edu.gmu.swe.gameproj.jpa.CardType;

public class Copper extends Treasure {

	private final static Copper INSTANCE = new Copper();
	
	private Copper() {
		super(CardType.Copper, 1);
	}

    public static Copper getInstance(){
        return INSTANCE;
    }
}
