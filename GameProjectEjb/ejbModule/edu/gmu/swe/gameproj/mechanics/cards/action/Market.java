package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.CardType;


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
        
        if(!super.gameProject.addActions(dto.player, addActionsCount)){
        	throw new Exception("add actions failed");
        }
        if(!super.gameProject.addBuys(dto.player, addBuysCount)){
        	throw new Exception("add actions failed");
        }
        if(super.gameProject.addCoins(dto.player, addCoinsCount)){
        	throw new Exception("add coins failed");
        }
        if(super.gameProject.draw(dto.player, drawCount) == null){
        	throw new Exception("draw failed");
        }

//        ICommand addActions= new AddActionsCommand(dto.player, addActionsCount);
//        ICommand addBuys = new AddBuysCommand(dto.player, addBuysCount);
//        ICommand addCoins = new AddCoinsCommand(dto.player, addCoinsCount);
//        ICommand draw = new DrawCommand(dto.player, drawCount);
//
//        addActions.Execute();
//        addBuys.Execute();
//        addCoins.Execute();
//        draw.Execute();
    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) return false;
        if(dto.player == null) return false;

        return true;
    }

}
