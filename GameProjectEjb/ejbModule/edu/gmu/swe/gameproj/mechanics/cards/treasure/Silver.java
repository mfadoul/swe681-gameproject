package edu.gmu.swe.gameproj.mechanics.cards.treasure;

import edu.gmu.swe.gameproj.jpa.CardType;

public class Silver extends Treasure {

	private final static Silver INSTANCE = new Silver();
	
	private Silver() {
		super(CardType.Silver, 2);
	}

    public static Silver getInstance(){
        return INSTANCE;
    }
}
