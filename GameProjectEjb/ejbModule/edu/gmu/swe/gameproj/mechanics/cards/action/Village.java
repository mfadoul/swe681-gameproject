package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.jpa.Player;


public class Village extends Action {

    private final int actionCount = 2;
    private final int drawCount = 1;
    
	public Village(GameProjectRemote _gameProject) {
		super(CardType.Village, _gameProject);
	}

    @Override
    public void act(ActionDto dto) throws Exception {
        if(!validate(dto)) throw new InvalidParameterException("dto");
        
//        if(!super.gameProject.addActions(dto.player, actionCount)){
//        	throw new Exception("add actions failed");
//        }
//        if(super.gameProject.draw(dto.player, drawCount) == null){
//        	throw new Exception("draw failed");
//        }
//
//        super.act(dto);
        
        Player p1 = super.gameProject.addActions(dto.player, actionCount);
        if(p1 == null) throw new Exception("add actions failed");
        
        Player p2 = super.gameProject.draw(p1, drawCount);
        if(p2 == null) throw new Exception("draw failed");
        
        super.cleanUp(p2);
    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) return false;
        if(dto.player == null) return false;
        return true;
    }

 
}
