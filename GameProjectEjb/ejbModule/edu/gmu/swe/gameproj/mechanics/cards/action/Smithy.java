package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.mechanics.command.*;

public class Smithy extends Action {

    private final int drawCount = 3;
	public Smithy() {
		super(CardType.Smithy);
	}

    @Override
    public void act(ActionDto dto) {
        if(!validate(dto)) throw new InvalidParameterException("dto");

        ICommand draw = new DrawCommand(dto.player, drawCount);
        draw.Execute();
    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");
        return true;
    }
}
