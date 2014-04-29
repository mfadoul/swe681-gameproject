package edu.gmu.swe.gameproj.mechanics.cards;

import java.security.InvalidParameterException;

public abstract class Card {

    private final static int MIN_COST = 1;
    private final static int MAX_COST = 8;
    protected Card(String _name, int _cost)
    {
        if(_cost < MIN_COST || _cost > MAX_COST) throw new InvalidParameterException("_cost");
        if(_name == null) throw new NullPointerException("_name");
        this.name = _name;
        this.cost = _cost;
    }

    private String name;
    public String getName() {
        return name;
    }

    private int cost;
    public int getCost() {
        return cost;
    }

}
