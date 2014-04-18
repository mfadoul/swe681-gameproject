package edu.gmu.swe.gameproj.ejb.cards.action;

import edu.gmu.swe.gameproj.ejb.NotValidatedException;
import edu.gmu.swe.gameproj.ejb.cards.dtos.ActionDto;
import edu.gmu.swe.gameproj.ejb.command.AddActionsCommand;
import edu.gmu.swe.gameproj.ejb.command.DrawCommand;
import edu.gmu.swe.gameproj.ejb.command.ICommand;

public class Village extends Action{

    private int actionCount = 2;
    private int drawCount = 1;
    public Village(){
        super("Village", 3);
    }

    @Override
    public void Act(ActionDto dto) throws NotValidatedException {
        if(!Validate(dto)) throw new NotValidatedException();

        ICommand addActions = new AddActionsCommand(dto.player, actionCount);
        ICommand draw = new DrawCommand(dto.player, drawCount);

        addActions.Execute();
        draw.Execute();
    }

    @Override
    protected boolean Validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");
        return true;
    }
}
