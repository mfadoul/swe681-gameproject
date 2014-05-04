package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.jpa.Card;
import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.mechanics.command.*;

public class Cellar extends Action {
	
    private final int addActionsCount = 2;

	public Cellar(CardType cardType) {
		super(CardType.Cellar);
	}
    @Override
    public void act(ActionDto dto) {
        if(!validate(dto)){
            throw new InvalidParameterException("dto");
        }

        ICommand addActions = new AddActionsCommand(dto.player, addActionsCount);
        ICommand draw = new DrawCommand(dto.player, dto.discardCards.size());
        ICommand discard = new DiscardCommand(dto.player, dto.discardCards);

        addActions.Execute();
        discard.Execute();
        draw.Execute();

    }

    @Override
    protected boolean validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");

        if(dto.player == null) throw new NullPointerException("dto.Player");
        if(dto.discardCards == null) throw new NullPointerException("dto.discardCards");

        for(Card c : dto.discardCards)
        {
            if(!dto.player.hasCard(c)) {
                return false;
            }

        }

        return true;
    }
}
