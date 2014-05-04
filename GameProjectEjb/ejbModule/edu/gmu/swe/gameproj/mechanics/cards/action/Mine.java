package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.ejb.GameProject;
import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.Card;
import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.mechanics.command.*;

public class Mine extends Action {

	public Mine() {
		super(CardType.Mine);
	}
	
    @Override
    public void act(ActionDto dto) {
        if(!validate(dto)) throw new InvalidParameterException("dto");

        ICommand trash =  new TrashCardCommand(dto.player, dto.oldCard);
        ICommand add = new AddCardCommand(dto.player, dto.newCard);

        trash.Execute();
        add.Execute();

    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");

        if(dto.player == null) throw new NullPointerException("dto.player");
        if(dto.oldCard == null) throw new NullPointerException("dto.oldCard");
        if(dto.newCard == null) throw new NullPointerException("dto.newCard");
        
        CardType oldCardType = CardType.getCardType(dto.oldCard.getCardType());
        CardType newCardType = CardType.getCardType(dto.newCard.getCardType());

        if(newCardType.getCost() > (oldCardType.getCost() + 3))
            return false;

        return true;
    }

}
