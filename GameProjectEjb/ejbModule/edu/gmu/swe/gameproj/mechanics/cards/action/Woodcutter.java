package edu.gmu.swe.gameproj.mechanics.cards.action;

import edu.gmu.swe.gameproj.mechanics.cards.CardType;

public class Woodcutter extends Action {

	public Woodcutter() {
		super(CardType.Woodcutter);
	}

	/*
    public void Act(ActionDto dto) {
        if(!Validate(dto)) throw new InvalidParameterException("dto");

        ICommand addBuys = new AddBuysCommand(dto.player, buyCount);
        ICommand addCoins = new AddCoinsCommand(dto.player, coinCount);

        addBuys.Execute();
        addCoins.Execute();
    }

    @Override
    protected boolean Validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");
        return true;
    }

	*/
}
