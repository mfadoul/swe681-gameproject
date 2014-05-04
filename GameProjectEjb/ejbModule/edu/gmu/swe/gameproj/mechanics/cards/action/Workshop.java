package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.mechanics.command.*;

public class Workshop extends Action {

	public Workshop() {
		super(CardType.Workshop);
	}

	
    @Override
    public void act(ActionDto dto) {
        if(!validate(dto)) throw new InvalidParameterException("dto");

        ICommand addCard = new AddCardCommand(dto.player, dto.newCard);

        addCard.Execute();
    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");

        if(dto.newCard == null) throw new NullPointerException("dto.newCard");
        
        CardType newCardType = CardType.getCardType(dto.newCard.getCardType());

        if(newCardType.getCost() > 4)
            return false;

        return true;
    }

	 
}
