package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.mechanics.command.*;

public class Woodcutter extends Action {

    private final int buyCount = 1;
    private final int coinCount = 2;
    
	public Woodcutter() {
		super(CardType.Woodcutter);
	}

	@Override
    public void act(ActionDto dto) {
        if(!validate(dto)) throw new InvalidParameterException("dto");

        ICommand addBuys = new AddBuysCommand(dto.player, buyCount);
        ICommand addCoins = new AddCoinsCommand(dto.player, coinCount);

        addBuys.Execute();
        addCoins.Execute();
    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");
        return true;
    }

	
}
