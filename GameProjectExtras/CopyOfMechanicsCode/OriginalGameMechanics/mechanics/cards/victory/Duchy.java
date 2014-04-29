package edu.gmu.swe.gameproj.mechanics.cards.victory;

public final class Duchy extends Victory {
    private final static Duchy INSTANCE = new Duchy();
    private Duchy() {
        super(3, "Duchy", 5);
    }

    public static Duchy getInstance(){
        return INSTANCE;
    }
}
