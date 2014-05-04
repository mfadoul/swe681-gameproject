package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.CardType;


public class Village extends Action {

    private final int actionCount = 2;
    private final int drawCount = 1;
    
	public Village(GameProjectRemote _gameProject) {
		super(CardType.Village, _gameProject);
	}

    @Override
    public void act(ActionDto dto) {
        if(!validate(dto)) throw new InvalidParameterException("dto");
        
        super.gameProject.addActions(dto.player, actionCount);
        super.gameProject.draw(dto.player, drawCount);

//        ICommand addActions = new AddActionsCommand(dto.player, actionCount);
//        ICommand draw = new DrawCommand(dto.player, drawCount);
//
//        addActions.Execute();
//        draw.Execute();
    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");
        return true;
    }

 
}
