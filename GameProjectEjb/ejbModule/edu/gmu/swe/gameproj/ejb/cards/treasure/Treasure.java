package edu.gmu.swe.gameproj.ejb.cards.treasure;

import edu.gmu.swe.gameproj.ejb.Player;
import edu.gmu.swe.gameproj.ejb.cards.Card;

public abstract class Treasure extends Card {

    private int value;

    protected Treasure(int _value, String _name, int _cost) {
        super(_name, _cost);
        this.value = _value;
    }

    public int getValue() {
        return value;
    }

    public void AddTreasure(Player player)
    {
        //todo: implement
    }

}
