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
    public void act(ActionDto dto) {
        if(!validate(dto)) throw new InvalidParameterException("dto");
        
        super.gameProject.draw(dto.player, drawCount);

//        ICommand draw = new DrawCommand(dto.player, drawCount);
//        draw.Execute();

    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");

        return true;
    }
}
