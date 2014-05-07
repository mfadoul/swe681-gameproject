package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.jpa.Player;


public class Market extends Action {


    private final int addActionsCount = 1;
    private final int addBuysCount = 1;
    private final int addCoinsCount = 1;
    private final int drawCount = 1;
    
	public Market(GameProjectRemote _gameProject) {
		super(CardType.Market, _gameProject);
	}
	
    @Override
    public void act(ActionDto dto) throws Exception {
        if(!validate(dto)) throw new InvalidParameterException("dto");
        
//        if(!super.gameProject.addActions(dto.player, addActionsCount)){
//        	throw new Exception("add actions failed");
//        }
//        if(!super.gameProject.addBuys(dto.player, addBuysCount)){
//        	throw new Exception("add actions failed");
//        }
//        if(super.gameProject.addCoins(dto.player, addCoinsCount)){
//        	throw new Exception("add coins failed");
//        }
//        if(super.gameProject.draw(dto.player, drawCount) == null){
//        	throw new Exception("draw failed");
//        }
      //super.act(dto);
        
        Player p1 = super.gameProject.addActions(dto.player, addActionsCount);
        if(p1 == null) throw new Exception("add actions failed");
        
        Player p2 = super.gameProject.addBuys(p1, addBuysCount);
        if(p2 == null) throw new Exception("add actions failed");
        
        Player p3 = super.gameProject.addCoins(p2, addCoinsCount);
        if(p3 == null) throw new Exception("add coins failed");
        
        Player p4 = super.gameProject.draw(p3, drawCount);
        if(p4 == null) throw new Exception("draw failed");
        
        super.cleanUp(p4);
        
        


    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) return false;
        if(dto.player == null) return false;

        return true;
    }

}
