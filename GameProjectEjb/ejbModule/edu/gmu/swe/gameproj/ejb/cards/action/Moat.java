package edu.gmu.swe.gameproj.ejb.cards.action;

import edu.gmu.swe.gameproj.ejb.NotValidatedException;
import edu.gmu.swe.gameproj.ejb.cards.dtos.ActionDto;
import edu.gmu.swe.gameproj.ejb.command.DrawCommand;
import edu.gmu.swe.gameproj.ejb.command.ICommand;


public class Moat extends Action {

    private int drawCount = 2;

    public Moat(){
        super("Moat",2);

    }

    @Override
    public void Act(ActionDto dto) throws NotValidatedException {
        if(!Validate(dto)) throw new NotValidatedException();

        ICommand draw = draw = new DrawCommand(dto.player, drawCount);
        draw.Execute();

    }

    @Override
    protected boolean Validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");

        return true;
    }
}
