package edu.gmu.swe.gameproj.mechanics.cards.action;

import edu.gmu.swe.gameproj.mechanics.command.AddActionsCommand;
import edu.gmu.swe.gameproj.mechanics.command.DiscardCommand;
import edu.gmu.swe.gameproj.mechanics.command.DrawCommand;
import edu.gmu.swe.gameproj.mechanics.command.ICommand;

import java.security.InvalidParameterException;

public class Cellar extends Action {

    private int addActionsCount = 2;

    public Cellar()
    {
        super("Cellar", 2);

    }
    @Override
    public void Act(ActionDto dto) {
        if(!Validate(dto)){
            throw new InvalidParameterException("dto");
        }

        ICommand addActions = new AddActionsCommand(dto.player, addActionsCount);
        ICommand draw = new DrawCommand(dto.player, dto.discardCardNames.size());
        ICommand discard = new DiscardCommand(dto.player, dto.discardCardNames);

        addActions.Execute();
        discard.Execute();
        draw.Execute();

    }

    @Override
    protected boolean Validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");

        if(dto.player == null) throw new NullPointerException("dto.Player");
        if(dto.discardCardNames == null) throw new NullPointerException("dto.discardCardNames");

        for(String s : dto.discardCardNames)
        {
            if(!dto.player.hasCard(s)) {
                return false;
            }

        }

        return true;
    }
}
