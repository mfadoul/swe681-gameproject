package edu.gmu.swe.gameproj.ejb.cards.action;

import edu.gmu.swe.gameproj.ejb.NotValidatedException;
import edu.gmu.swe.gameproj.ejb.cards.dtos.ActionDto;
import edu.gmu.swe.gameproj.ejb.command.AddBuysCommand;
import edu.gmu.swe.gameproj.ejb.command.AddCoinsCommand;
import edu.gmu.swe.gameproj.ejb.command.ICommand;

public class Woodcutter extends Action {

    private int buyCount = 1;
    private int coinCount = 2;

    public Woodcutter(){
        super("Woodcutter", 3);
    }

    @Override
    public void Act(ActionDto dto) throws NotValidatedException {
        if(!Validate(dto)) throw new NotValidatedException();

        ICommand addBuys = new AddBuysCommand(dto.player, buyCount);
        ICommand addCoins = new AddCoinsCommand(dto.player, coinCount);

        addBuys.Execute();
        addCoins.Execute();
    }

    @Override
    protected boolean Validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");
        return true;
    }
}
