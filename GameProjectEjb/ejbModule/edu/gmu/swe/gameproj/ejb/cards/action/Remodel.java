package edu.gmu.swe.gameproj.ejb.cards.action;

import edu.gmu.swe.gameproj.ejb.NotValidatedException;
import edu.gmu.swe.gameproj.ejb.cards.dtos.ActionDto;
import edu.gmu.swe.gameproj.ejb.cards.dtos.RemodelDto;
import edu.gmu.swe.gameproj.ejb.command.AddCardCommand;
import edu.gmu.swe.gameproj.ejb.command.ICommand;
import edu.gmu.swe.gameproj.ejb.command.TrashCardCommand;

import java.security.InvalidParameterException;


public class Remodel extends Action {
    public Remodel(){
        super("Remodel", 4);
    }

    @Override
    public void Act(ActionDto dto) throws NotValidatedException {
        if(!Validate(dto)) throw new NotValidatedException();

        RemodelDto remodelDto = (RemodelDto) dto;

        ICommand trash = new TrashCardCommand(remodelDto.player, remodelDto.OldCard);
        ICommand add = new AddCardCommand(remodelDto.player, remodelDto.NewCard);

        trash.Execute();
        add.Execute();

    }

    @Override
    protected boolean Validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(!(dto instanceof RemodelDto)) throw new InvalidParameterException("RemodelDto expected");

        RemodelDto remodelDto = (RemodelDto) dto;

        if(remodelDto.player == null) throw new NullPointerException("dto.Player");
        if(remodelDto.OldCard == null) throw new NullPointerException("dto.OldCard");
        if(remodelDto.NewCard == null) throw new NullPointerException("dto.NewCard");

        if(remodelDto.NewCard.getCost() > (remodelDto.OldCard.getCost() + 3))
            return false;

        return true;
    }
}
