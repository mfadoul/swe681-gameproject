package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.Card;
import edu.gmu.swe.gameproj.jpa.CardType;

public class Cellar extends Action {
	
    private final int addActionsCount = 2;

	public Cellar(GameProjectRemote _gameProject) {
		super(CardType.Cellar, _gameProject);
	}
    @Override
    public void act(ActionDto dto) throws Exception {
        if(!validate(dto)){
            throw new InvalidParameterException("dto");
        }
        
        if(!super.gameProject.addActions(dto.player, addActionsCount)){
        	throw new Exception("add actions failed");
        }
        if(!super.gameProject.discard(dto.player, dto.discardCards)){
        	throw new Exception("discard failed");
        }
        if(super.gameProject.draw(dto.player, dto.discardCards.size()) == null){
        	throw new Exception("draw failed");
        }
        

//        ICommand addActions = new AddActionsCommand(dto.player, addActionsCount);
//        ICommand draw = new DrawCommand(dto.player, dto.discardCards.size());
//        ICommand discard = new DiscardCommand(dto.player, dto.discardCards);
//
//        addActions.Execute();
//        discard.Execute();
//        draw.Execute();


    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) return false;

        if(dto.player == null) return false;
        if(dto.discardCards == null) return false;
        
        
        ArrayList<Card> hand = dto.player.getHand();
        for(CardType ct : dto.discardCards){
        	if(dto.player.getFirstInstanceInHandByType(ct) == null){
        		return false;
        	}
        }
        

        return true;
    }
    
}
