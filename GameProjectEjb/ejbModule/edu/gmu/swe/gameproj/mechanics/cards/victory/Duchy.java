package edu.gmu.swe.gameproj.mechanics.cards.victory;

import edu.gmu.swe.gameproj.jpa.CardType;

public class Duchy extends Victory {
    private final static Duchy INSTANCE = new Duchy();

    private Duchy() {
		super(CardType.Duchy, 3);
	}

    public static Duchy getInstance(){
        return INSTANCE;
    }
}
