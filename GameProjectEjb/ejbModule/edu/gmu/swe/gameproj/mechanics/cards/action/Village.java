package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.mechanics.command.*;

public class Village extends Action {

    private final int actionCount = 2;
    private final int drawCount = 1;
    
	public Village() {
		super(CardType.Village);
	}

    @Override
    public void act(ActionDto dto) {
        if(!validate(dto)) throw new InvalidParameterException("dto");

        ICommand addActions = new AddActionsCommand(dto.player, actionCount);
        ICommand draw = new DrawCommand(dto.player, drawCount);

        addActions.Execute();
        draw.Execute();
    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");
        return true;
    }

 
}
