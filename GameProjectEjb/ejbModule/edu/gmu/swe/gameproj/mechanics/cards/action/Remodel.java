package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.Card;
import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.jpa.Player;


public class Remodel extends Action {

	public Remodel(GameProjectRemote _gameProject) {
		super(CardType.Remodel, _gameProject);
	}

	@Override
    public void act(ActionDto dto) throws Exception {
        if(!validate(dto)) throw new InvalidParameterException("dto");
        
        //Card trashCard = dto.player.getFirstInstanceInHandByType(dto.oldCard);
        //Card newCard = null;//TODO: Retrieve new card from gamestate
//        if(!super.gameProject.trash(trashCard)){
//        	throw new Exception("trash failed");
//        }
//
//        if(!super.gameProject.addCardToDiscardFromGame(dto.player, newCard)){
//        	throw new Exception("discard failed");
//        }
//        super.act(dto);
        
        Player p1 = super.gameProject.trash(dto.player, dto.oldCard);
        if(p1 == null) throw new Exception("trash failed");
        
        Player p2 = super.gameProject.addCardToDiscardFromGame(dto.player, dto.newCard);
        if(p2 == null) throw new Exception("discard failed");
        
        super.cleanUp(p2);

    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) return false;
        
        if(dto.player == null) return false;
        if(dto.oldCard == null) return false;
        if(dto.newCard == null) return false;
        
        //TODO Verify oldcard exists in gamestate to add to hand
        
        if(dto.player.getFirstInstanceInHandByType(dto.oldCard) == null) return false;       

        if(dto.newCard.getCost() > (dto.oldCard.getCost() + 3))
            return false;

        return true;
    }
}
