package edu.gmu.swe.gameproj.mechanics.cards.action;

import edu.gmu.swe.gameproj.mechanics.cards.CardType;

public class Village extends Action {

	public Village() {
		super(CardType.Village);
	}
/*
    @Override
    public void Act(ActionDto dto) {
        if(!Validate(dto)) throw new InvalidParameterException("dto");

        ICommand addActions = new AddActionsCommand(dto.player, actionCount);
        ICommand draw = new DrawCommand(dto.player, drawCount);

        addActions.Execute();
        draw.Execute();
    }

    @Override
    protected boolean Validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");
        return true;
    }

 */
}
