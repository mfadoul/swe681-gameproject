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
    public void act(ActionDto dto) throws Exception {
        if(!validate(dto)) throw new InvalidParameterException("dto");
        
        if(!super.gameProject.addActions(dto.player, actionCount)){
        	throw new Exception("add actions failed");
        }
        if(super.gameProject.draw(dto.player, drawCount) == null){
        	throw new Exception("draw failed");
        }

//        ICommand addActions = new AddActionsCommand(dto.player, actionCount);
//        ICommand draw = new DrawCommand(dto.player, drawCount);
//
//        addActions.Execute();
//        draw.Execute();
    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) return false;
        if(dto.player == null) return false;
        return true;
    }

 
}
