package edu.gmu.swe.gameproj.ejb.cards.treasure;

public final class Gold extends Treasure {
    private final static Gold INSTANCE = new Gold();
    private Gold(){
        super(3,"Gold",6);
    }

    public static Gold getInstance(){
        return INSTANCE;
    }
}
