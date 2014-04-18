package edu.gmu.swe.gameproj.ejb.cards.treasure;

public final class Silver extends Treasure {
    private final static Silver INSTANCE = new Silver();
    private Silver()
    {
        super(2,"Silver",3);
    }

    public static Silver getInstance()
    {
        return INSTANCE;
    }
}
