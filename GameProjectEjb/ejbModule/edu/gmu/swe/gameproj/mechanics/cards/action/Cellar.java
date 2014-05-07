package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.Card;
import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.jpa.Player;

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
        
//        if(!super.gameProject.addActions(dto.player, addActionsCount)){
//        	throw new Exception("add actions failed");
//        }
//        if(!super.gameProject.discard(dto.player, dto.discardCards)){
//        	throw new Exception("discard failed");
//        }
//        if(super.gameProject.draw(dto.player, dto.discardCards.size()) == null){
//        	throw new Exception("draw failed");
//        }
//        
//        super.act(dto);
        
        Player p1 = super.gameProject.addActions(dto.player, addActionsCount);
        if(p1 == null) throw new Exception("add actions failed");
        
        Card c1 = super.gameProject.discard(p1, dto.discardCards);
        if(c1 == null) throw new Exception("discard failed");
        
        Player p2 = super.gameProject.draw(p1, dto.discardCards.size());
        if(p2 == null) throw new Exception("draw failed");
        
        super.cleanUp(p2);


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
