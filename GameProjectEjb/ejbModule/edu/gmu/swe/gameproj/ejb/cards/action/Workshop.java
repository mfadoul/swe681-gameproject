package edu.gmu.swe.gameproj.ejb.cards.action;

import edu.gmu.swe.gameproj.ejb.NotValidatedException;
import edu.gmu.swe.gameproj.ejb.cards.dtos.ActionDto;
import edu.gmu.swe.gameproj.ejb.cards.dtos.WorkshopDto;
import edu.gmu.swe.gameproj.ejb.command.AddCardCommand;
import edu.gmu.swe.gameproj.ejb.command.ICommand;

import java.security.InvalidParameterException;

public class Workshop extends Action{

    public Workshop(){
        super("Workshop", 3);
    }

    @Override
    public void Act(ActionDto dto) throws NotValidatedException {
        if(!Validate(dto)) throw new NotValidatedException();

        WorkshopDto workshopDto = (WorkshopDto) dto;

        ICommand addCard = new AddCardCommand(workshopDto.player, workshopDto.NewCard);

        addCard.Execute();
    }

    @Override
    protected boolean Validate(ActionDto dto) {
        if(dto == null) throw new NullPointerException("dto");
        if(dto.player == null) throw new NullPointerException("dto.player");

        if(!(dto instanceof WorkshopDto)) throw new InvalidParameterException("WorkshopDto expected");

        WorkshopDto workshopDto = (WorkshopDto) dto;

        if(workshopDto.NewCard == null) throw new NullPointerException("dto.NewCard");

        if(workshopDto.NewCard.getCost() > 4)
            return false;

        return true;
    }
}
