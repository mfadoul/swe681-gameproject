package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.jpa.Player;


public class Moat extends Action {

    private final int drawCount = 2;
    
	public Moat(GameProjectRemote _gameProject) {
		super(CardType.Moat, _gameProject);
	}
	@Override
    public void act(ActionDto dto) throws Exception {
        if(!validate(dto)) throw new InvalidParameterException("dto");
        
//        if(super.gameProject.draw(dto.player, drawCount) == null){
//        	throw new Exception("draw failed");
//        }
//        
//        super.act(dto);

        Player p1 = super.gameProject.draw(dto.player, drawCount);
        if(p1 == null) throw new Exception("draw failed");
        
        super.cleanUp(p1);

    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) return false;
        if(dto.player == null) return false;

        return true;
    }
}
