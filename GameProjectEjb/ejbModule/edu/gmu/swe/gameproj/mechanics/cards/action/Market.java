package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.mechanics.command.*;

public class Market extends Action {

    private ICommand addActions;
    private ICommand addBuys;
    private ICommand addCoins;
    private ICommand draw;

    private final int addActionsCount = 1;
    private final int addBuysCount = 1;
    private final int addCoinsCount = 1;
    private final int drawCount = 1;
    
	public Market() {
		super(CardType.Market);
	}
	
    @Override
    public void act(ActionDto dto) {
        if(!validate(dto)) throw new InvalidParameterException("dto");

        ICommand addActions= new AddActionsCommand(dto.player, addActionsCount);
        ICommand addBuys = new AddBuysCommand(dto.player, addBuysCount);
        ICommand addCoins = new AddCoinsCommand(dto.player, addCoinsCount);
        ICommand draw = new DrawCommand(dto.player, drawCount);

        addActions.Execute();
        addBuys.Execute();
        addCoins.Execute();
        draw.Execute();
    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");

        return true;
    }

}
