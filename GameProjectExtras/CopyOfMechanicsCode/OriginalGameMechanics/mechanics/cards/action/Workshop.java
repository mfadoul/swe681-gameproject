package edu.gmu.swe.gameproj.mechanics.cards.action;

import edu.gmu.swe.gameproj.mechanics.cards.Card;
import edu.gmu.swe.gameproj.mechanics.cards.CardFactory;
import edu.gmu.swe.gameproj.mechanics.command.AddCardCommand;
import edu.gmu.swe.gameproj.mechanics.command.ICommand;

import java.security.InvalidParameterException;

public class Workshop extends Action{

    public Workshop(){
        super("Workshop", 3);
    }

    @Override
    public void Act(ActionDto dto) {
        if(!Validate(dto)) throw new InvalidParameterException("dto");

        ICommand addCard = new AddCardCommand(dto.player, dto.newCardName);

        addCard.Execute();
    }

    @Override
    protected boolean Validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");

        if(dto.newCardName == null) throw new NullPointerException("dto.newCardName");

        Card newCard = CardFactory.buildCard(dto.newCardName);

        if(newCard.getCost() > 4)
            return false;

        return true;
    }
}
