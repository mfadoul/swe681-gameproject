package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.CardType;

public class Militia extends Action {

    private final int addCoinsCount = 2;
    
	public Militia(GameProjectRemote _gameProject) {
		super(CardType.Militia, _gameProject);
	}
	
    @Override
    public void act(ActionDto dto) throws Exception {
        if(!validate(dto)) throw new InvalidParameterException("dto");
        
        if(!super.gameProject.addCoins(dto.player, addCoinsCount)){
        	throw new Exception("add coins failed");
        }
      //TODO: Add observer for card draw down
        
//        ICommand addCoins = new AddCoinsCommand(dto.player, addCoinsCount);
//        addCoins.Execute();
        
    }

    @Override
    public boolean validate(ActionDto dto) {
        if(dto == null) return false;
        if(dto.player == null) return false;
        return true;
    }

}
