package edu.gmu.swe.gameproj.ejb.cards.action;

import edu.gmu.swe.gameproj.ejb.command.DrawCommand;
import edu.gmu.swe.gameproj.ejb.command.ICommand;

import java.security.InvalidParameterException;

public class Smithy extends Action{

    int drawCount = 3;
    public Smithy(){
        super("Smithy", 4);
    }

    @Override
    public void Act(ActionDto dto) {
        if(!Validate(dto)) throw new InvalidParameterException("dto");

        ICommand draw = new DrawCommand(dto.player, drawCount);
        draw.Execute();
    }

    @Override
    protected boolean Validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");
        return true;
    }
}
