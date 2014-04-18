package edu.gmu.swe.gameproj.ejb.cards.action;

import edu.gmu.swe.gameproj.ejb.NotValidatedException;
import edu.gmu.swe.gameproj.ejb.cards.Card;
import edu.gmu.swe.gameproj.ejb.cards.dtos.ActionDto;


public abstract class Action extends Card {

    protected Action(String _name, int _cost) {
        super(_name, _cost);
    }

    public abstract void Act(ActionDto dto) throws NotValidatedException;
    protected abstract boolean Validate(ActionDto dto);
}
