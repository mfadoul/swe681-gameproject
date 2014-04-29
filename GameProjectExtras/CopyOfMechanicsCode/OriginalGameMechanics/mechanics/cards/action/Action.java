package edu.gmu.swe.gameproj.mechanics.cards.action;

import edu.gmu.swe.gameproj.mechanics.cards.Card;


public abstract class Action extends Card {

    protected Action(String _name, int _cost) {
        super(_name, _cost);
    }

    public abstract void Act(ActionDto dto);
    protected abstract boolean Validate(ActionDto dto);
}
