package edu.gmu.swe.gameproj.mechanics.cards.treasure;

public final class Copper extends Treasure {
    private final static Copper instance = new Copper();
    private Copper(){
        super(1, "Copper", 0);
    }

    public static Copper getInstance() {
        return instance;
    }

}
