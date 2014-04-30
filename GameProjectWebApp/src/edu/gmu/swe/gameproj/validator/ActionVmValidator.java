package edu.gmu.swe.gameproj.validator;

import java.util.Arrays;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.gmu.swe.gameproj.util.ExtraStringUtils;

public class ActionVmValidator implements Validator{
    private String[] actionCardNames = new String[] {
    		"Cellar", "Market", "Militia", "Mine", "Moat", "Remodel", "Smithy", "Village", "Woodcutter", "Workshop"
    		};

	public ActionVmValidator  () {
		// TODO Auto-generated constructor stub
	}

	@Override
    public boolean supports(Class<?> aClass) {
        // return ActionVm.class.isAssignableFrom(aClass);
		return true;
    }

	@Override
	public void validate(Object arg0, Errors errors) {
//        ActionVm vm = (ActionVm)o;
//
//        if(ExtraStringUtils.isNullOrBlank(vm.playedCardName)){
//            errors.rejectValue("olayedCardName", "requiredvalue", "Please play a card.");
//        }
//        else{
//            if(!Arrays.asList(actionCardNames).contains(vm.playedCardName)){
//                errors.rejectValue("playedCardName", "invalidvalue", "Invalid card");
//            }
//        }		
	}

}
