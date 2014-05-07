package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.Card;
import edu.gmu.swe.gameproj.jpa.CardType;


public class Workshop extends Action {

	public Workshop(GameProjectRemote _gameProject) {
		super(CardType.Workshop, _gameProject);
	}

	
    @Override
    public void act(ActionDto dto) throws Exception {
        if(!validate(dto)) throw new InvalidParameterException("dto");
        
        Card newCard = null;//TODO get card from gamestate
        if(!super.gameProject.addCardToDiscardFromGame(dto.player, newCard)){
        	throw new Exception("add card to discard failed");
        }
        super.act(dto);

//        ICommand addCard = new AddCardCommand(dto.player, dto.newCard);
//
//        addCard.Execute();
    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) return false;
        if(dto.player == null) return false;

        if(dto.newCard == null) return false;
        
        //TODO Verify card available in gamestate
        

        if(dto.newCard.getCost() > 4)
            return false;

        return true;
    }

	 
}
