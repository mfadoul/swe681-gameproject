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
    public void act(ActionDto dto) {
        if(!validate(dto)) throw new InvalidParameterException("dto");
        
        super.gameProject.addActions(dto.player, addActionsCount);
        super.gameProject.addBuys(dto.player, addBuysCount);
        super.gameProject.addCoins(dto.player, addCoinsCount);
        super.gameProject.draw(dto.player, drawCount);

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
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");

        return true;
    }

}
