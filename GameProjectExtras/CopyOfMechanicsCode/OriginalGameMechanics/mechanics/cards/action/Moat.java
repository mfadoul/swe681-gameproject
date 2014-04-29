package edu.gmu.swe.gameproj.mechanics.cards.action;

import edu.gmu.swe.gameproj.mechanics.command.DrawCommand;
import edu.gmu.swe.gameproj.mechanics.command.ICommand;

import java.security.InvalidParameterException;


public class Moat extends Action {

    private int drawCount = 2;

    public Moat(){
        super("Moat",2);

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
