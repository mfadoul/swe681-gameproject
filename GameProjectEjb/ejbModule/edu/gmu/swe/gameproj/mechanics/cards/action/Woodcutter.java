package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.jpa.Player;


public class Woodcutter extends Action {

    private final int buyCount = 1;
    private final int coinCount = 2;
    
	public Woodcutter(GameProjectRemote _gameProject) {
		super(CardType.Woodcutter, _gameProject);
	}

	@Override
    public void act(ActionDto dto) throws Exception {
        if(!validate(dto)) throw new InvalidParameterException("dto");
        
//        if(!super.gameProject.addBuys(dto.player, buyCount)){
//        	throw new Exception("add buys failed");
//        }
//        if(!super.gameProject.addCoins(dto.player, coinCount)){
//        	throw new Exception("add coins failed");
//        }
//        
//        super.act(dto);

        Player p1 = super.gameProject.addBuys(dto.player, buyCount);
        if(p1 == null) throw new Exception("add buys failed");
        
        Player p2 = super.gameProject.addCoins(p1, coinCount);
        if(p2 == null) throw new Exception("add coins failed");
        
        super.cleanUp(p2);
    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) return false;
        if(dto.player == null) return false;
        return true;
    }

	
}
