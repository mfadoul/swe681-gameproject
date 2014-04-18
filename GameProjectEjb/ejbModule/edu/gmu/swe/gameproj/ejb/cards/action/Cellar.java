package edu.gmu.swe.gameproj.ejb.cards.action;

import edu.gmu.swe.gameproj.ejb.NotValidatedException;
import edu.gmu.swe.gameproj.ejb.cards.Card;
import edu.gmu.swe.gameproj.ejb.cards.dtos.ActionDto;
import edu.gmu.swe.gameproj.ejb.cards.dtos.CellarDto;
import edu.gmu.swe.gameproj.ejb.command.AddActionsCommand;
import edu.gmu.swe.gameproj.ejb.command.DiscardCommand;
import edu.gmu.swe.gameproj.ejb.command.DrawCommand;
import edu.gmu.swe.gameproj.ejb.command.ICommand;

import java.security.InvalidParameterException;

public class Cellar extends Action {

    private int addActionsCount = 2;

    public Cellar()
    {
        super("Cellar", 2);

    }
    @Override
    public void Act(ActionDto dto) throws NotValidatedException{
        if(!Validate(dto)){
            throw new NotValidatedException();
        }

        CellarDto cellarDto = (CellarDto) dto;

        ICommand addActions = new AddActionsCommand(cellarDto.player, addActionsCount);
        ICommand draw = new DrawCommand(cellarDto.player, cellarDto.discardCards.size());
        ICommand discard = new DiscardCommand(cellarDto.player, cellarDto.discardCards);

        addActions.Execute();
        discard.Execute();
        draw.Execute();

    }

    @Override
    protected boolean Validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(!(dto instanceof CellarDto)) throw new InvalidParameterException("CellarDto expected");

        CellarDto cellarDto = (CellarDto) dto;

        if(cellarDto.player == null) throw new NullPointerException("dto.Player");
        if(cellarDto.discardCards == null) throw new NullPointerException("dto.discardCards");

        for(Card c : cellarDto.discardCards)
        {
            if(!cellarDto.player.hasCard(c)) {
                return false;
            }

        }

        return true;
    }
}
