package edu.gmu.swe.gameproj.validator;


import edu.gmu.swe.gameproj.models.ActionVm;
import edu.gmu.swe.gameproj.util.ExtraStringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;

public class ActionVmValidator implements Validator {

    private String[] actionCardNames = new String[] {"Cellar", "Market", "Militia", "Mine", "Moat", "Remodel", "Smithy", "Village", "Woodcutter", "Workshop"};

    @Override
    public boolean supports(Class<?> aClass) {
        return ActionVm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ActionVm vm = (ActionVm)o;

        if(ExtraStringUtils.isNullOrBlank(vm.playedCardName)){
            errors.rejectValue("olayedCardName", "requiredvalue", "Please play a card.");
        }
        else{
            if(!Arrays.asList(actionCardNames).contains(vm.playedCardName)){
                errors.rejectValue("playedCardName", "invalidvalue", "Invalid card");
            }
        }

    }
}
