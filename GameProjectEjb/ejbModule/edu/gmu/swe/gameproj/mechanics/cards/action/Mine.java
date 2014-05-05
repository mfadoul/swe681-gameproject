package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import edu.gmu.swe.gameproj.ejb.GameProject;
import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.Card;
import edu.gmu.swe.gameproj.jpa.CardType;


public class Mine extends Action {

	public Mine(GameProjectRemote _gameProject) {
		super(CardType.Mine, _gameProject);
	}
	
    @Override
    public void act(ActionDto dto) throws Exception {
        if(!validate(dto)) throw new InvalidParameterException("dto");
        
        Card trashCard = dto.player.getFirstInstanceInHandByType(dto.oldCard);
        Card newCard = null;//TODO Get card from game instance;
        
        if(!super.gameProject.trash(trashCard)){
        	throw new Exception("trash failed");
        }
        if(!super.gameProject.addCardToHandFromGame(dto.player, newCard)){
        	throw new Exception("add card to hand failed");
        }

//        ICommand trash =  new TrashCardCommand(dto.player, dto.oldCard);
//        ICommand add = new AddCardCommand(dto.player, dto.newCard);
//
//        trash.Execute();
//        add.Execute();

    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) return false;

        if(dto.player == null) return false;
        if(dto.oldCard == null) return false;
        if(dto.newCard == null) return false;
    	
    	if(dto.player.getFirstInstanceInHandByType(dto.oldCard) == null) return false;
    	
        //TODO Verify sufficient cards in game state for newCard

        if(dto.newCard.getCost() > (dto.oldCard.getCost() + 3))
            return false;

        return true;
    }

}
