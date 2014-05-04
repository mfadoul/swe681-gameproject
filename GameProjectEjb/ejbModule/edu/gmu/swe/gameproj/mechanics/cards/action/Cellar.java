package edu.gmu.swe.gameproj.mechanics.cards.action;

import java.security.InvalidParameterException;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.Card;
import edu.gmu.swe.gameproj.jpa.CardType;

public class Cellar extends Action {
	
    private final int addActionsCount = 2;

	public Cellar(GameProjectRemote _gameProject) {
		super(CardType.Cellar, _gameProject);
	}
    @Override
    public void act(ActionDto dto) {
        if(!validate(dto)){
            throw new InvalidParameterException("dto");
        }
        
        super.gameProject.addActions(dto.player, addActionsCount);
        super.gameProject.discard(dto.discardCards);
        super.gameProject.draw(dto.player, dto.discardCards.size());
        

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
        if(dto == null) throw new NullPointerException("dto");

        if(dto.player == null) throw new NullPointerException("dto.Player");
        if(dto.discardCards == null) throw new NullPointerException("dto.discardCards");

        for(Card c : dto.discardCards)
        {
        	//TODO test not just precense of card but in correct location (e.g. hand)
            if(!dto.player.hasCard(c)) {
                return false;
            }

        }

        return true;
    }
}
