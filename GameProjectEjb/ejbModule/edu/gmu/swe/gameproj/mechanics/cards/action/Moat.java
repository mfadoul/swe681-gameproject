package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.CardType;


public class Moat extends Action {

    private final int drawCount = 2;
    
	public Moat(GameProjectRemote _gameProject) {
		super(CardType.Moat, _gameProject);
	}
	@Override
    public void act(ActionDto dto) throws Exception {
        if(!validate(dto)) throw new InvalidParameterException("dto");
        
        if(super.gameProject.draw(dto.player, drawCount) == null){
        	throw new Exception("draw failed");
        }
        
        super.act(dto);

//        ICommand draw = new DrawCommand(dto.player, drawCount);
//        draw.Execute();

    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) return false;
        if(dto.player == null) return false;

        return true;
    }
}
