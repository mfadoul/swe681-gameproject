package edu.gmu.swe.gameproj.mechanics.cards.victory;

public final class Estate extends Victory {
    private final static Estate INSTANCE = new Estate();
    private Estate(){
         super(1,"Estate",2);
    }

    public static Estate getInstance(){
        return INSTANCE;
    }
}
