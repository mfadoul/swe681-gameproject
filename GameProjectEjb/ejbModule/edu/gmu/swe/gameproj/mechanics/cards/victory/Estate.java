package edu.gmu.swe.gameproj.mechanics.cards.victory;

import edu.gmu.swe.gameproj.jpa.CardType;

public class Estate extends Victory {
    private final static Estate INSTANCE = new Estate();

    private Estate(){
        super(CardType.Estate ,1);
   }

    public static Estate getInstance(){
        return INSTANCE;
    }
}
