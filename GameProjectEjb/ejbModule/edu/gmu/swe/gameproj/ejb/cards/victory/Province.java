package edu.gmu.swe.gameproj.ejb.cards.victory;

public final class Province extends Victory {
    private final static Province INSTANCE = new Province();
    private Province() {
        super(6, "Province", 8);
    }

    public static Province getInstance(){
        return INSTANCE;
    }
}
