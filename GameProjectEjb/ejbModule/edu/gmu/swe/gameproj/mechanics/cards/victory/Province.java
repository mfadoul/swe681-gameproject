package edu.gmu.swe.gameproj.mechanics.cards.victory;

import edu.gmu.swe.gameproj.jpa.CardType;

public class Province extends Victory {
    private final static Province INSTANCE = new Province();

    private Province(){
        super(CardType.Province ,6);
   }

    public static Province getInstance(){
        return INSTANCE;
    }
}
