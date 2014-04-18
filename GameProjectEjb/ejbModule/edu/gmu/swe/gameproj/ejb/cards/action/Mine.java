package edu.gmu.swe.gameproj.ejb.cards.action;

import edu.gmu.swe.gameproj.ejb.NotValidatedException;
import edu.gmu.swe.gameproj.ejb.cards.dtos.ActionDto;
import edu.gmu.swe.gameproj.ejb.cards.dtos.MineDto;
import edu.gmu.swe.gameproj.ejb.command.AddCardCommand;
import edu.gmu.swe.gameproj.ejb.command.ICommand;
import edu.gmu.swe.gameproj.ejb.command.TrashCardCommand;

import java.security.InvalidParameterException;

public class Mine extends Action {


    public Mine()
    {
        super("Mine", 5);
    }

    @Override
    public void Act(ActionDto dto) throws NotValidatedException {
        if(!Validate(dto)) throw new NotValidatedException();

        MineDto mineDto = (MineDto) dto;

        ICommand trash =  new TrashCardCommand(mineDto.player, mineDto.OldTreasure);
        ICommand add = new AddCardCommand(mineDto.player, mineDto.NewTreasure);

        trash.Execute();
        add.Execute();

    }

    @Override
    protected boolean Validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(!(dto instanceof MineDto)) throw new InvalidParameterException("MineDto expected");

        MineDto mineDto = (MineDto) dto;

        if(mineDto.player == null) throw new NullPointerException("dto.player");
        if(mineDto.OldTreasure == null) throw new NullPointerException("dto.OldTreasure");
        if(mineDto.NewTreasure == null) throw new NullPointerException("dto.NewTreasure");

        if(mineDto.NewTreasure.getCost() > (mineDto.OldTreasure.getCost() + 3))
            return false;

        return true;
    }
}
