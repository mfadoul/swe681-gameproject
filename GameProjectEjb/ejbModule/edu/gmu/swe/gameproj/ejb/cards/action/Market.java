package edu.gmu.swe.gameproj.ejb.cards.action;

import edu.gmu.swe.gameproj.ejb.NotValidatedException;
import edu.gmu.swe.gameproj.ejb.cards.dtos.ActionDto;
import edu.gmu.swe.gameproj.ejb.command.*;

public class Market extends Action{

    private ICommand addActions;
    private ICommand addBuys;
    private ICommand addCoins;
    private ICommand draw;

    private int addActionsCount = 1;
    private int addBuysCount = 1;
    private int addCoinsCount = 1;
    private int drawCount = 1;

    public Market()
    {
        super("Market", 5);
    }

    @Override
    public void Act(ActionDto dto) throws NotValidatedException {
        if(!Validate(dto)) throw new NotValidatedException();

        ICommand addActions= new AddActionsCommand(dto.player, addActionsCount);
        ICommand addBuys = new AddBuysCommand(dto.player, addBuysCount);
        ICommand addCoins = new AddCoinsCommand(dto.player, addCoinsCount);
        ICommand draw = new DrawCommand(dto.player, drawCount);

        addActions.Execute();
        addBuys.Execute();
        addCoins.Execute();
        draw.Execute();
    }

    @Override
    protected boolean Validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");

        return true;
    }
}

