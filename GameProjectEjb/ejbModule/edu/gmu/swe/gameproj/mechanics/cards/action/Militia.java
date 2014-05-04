package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.mechanics.command.*;

public class Militia extends Action {

    private final int addCoinsCount = 2;
    
	public Militia() {
		super(CardType.Militia);
	}
	
    @Override
    public void act(ActionDto dto) {
        if(!validate(dto)) throw new InvalidParameterException("dto");

        ICommand addCoins = new AddCoinsCommand(dto.player, addCoinsCount);
        addCoins.Execute();
        //TODO: Add observer for card draw down
    }

    @Override
    public boolean validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");
        return true;
    }

}
