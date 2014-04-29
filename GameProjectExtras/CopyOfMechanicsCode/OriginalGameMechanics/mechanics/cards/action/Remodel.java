package edu.gmu.swe.gameproj.mechanics.cards.action;

import edu.gmu.swe.gameproj.mechanics.cards.Card;
import edu.gmu.swe.gameproj.mechanics.cards.CardFactory;
import edu.gmu.swe.gameproj.mechanics.command.AddCardCommand;
import edu.gmu.swe.gameproj.mechanics.command.ICommand;
import edu.gmu.swe.gameproj.mechanics.command.TrashCardCommand;

import java.security.InvalidParameterException;


public class Remodel extends Action {
    public Remodel(){
        super("Remodel", 4);
    }

    @Override
    public void Act(ActionDto dto) {
        if(!Validate(dto)) throw new InvalidParameterException("dto");

        ICommand trash = new TrashCardCommand(dto.player, dto.oldCardName);
        ICommand add = new AddCardCommand(dto.player, dto.newCardName);

        trash.Execute();
        add.Execute();

    }

    @Override
    protected boolean Validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        
        if(dto.player == null) throw new NullPointerException("dto.Player");
        if(dto.oldCardName == null) throw new NullPointerException("dto.oldCardName");
        if(dto.newCardName == null) throw new NullPointerException("dto.newCardName");

        Card newCard = CardFactory.buildCard(dto.newCardName);
        Card oldCard = CardFactory.buildCard(dto.oldCardName);

        if(newCard.getCost() > (oldCard.getCost() + 3))
            return false;

        return true;
    }
}
