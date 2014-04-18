package edu.gmu.swe.gameproj.ejb.cards.action;

import edu.gmu.swe.gameproj.ejb.NotValidatedException;
import edu.gmu.swe.gameproj.ejb.cards.dtos.ActionDto;
import edu.gmu.swe.gameproj.ejb.command.AddCoinsCommand;
import edu.gmu.swe.gameproj.ejb.command.ICommand;

public class Militia extends Action{

    private int addCoinsCount = 2;
    public Militia(){
        super("Militia", 4);

    }

    @Override
    public void Act(ActionDto dto) throws NotValidatedException {
        if(!Validate(dto)) throw new NotValidatedException();

        ICommand addCoins = new AddCoinsCommand(dto.player, addCoinsCount);
        addCoins.Execute();
        //TODO: Add observer for card draw down
    }

    @Override
    public boolean Validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");
        return true;
    }
}
