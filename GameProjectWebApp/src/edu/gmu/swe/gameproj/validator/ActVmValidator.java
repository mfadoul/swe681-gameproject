package edu.gmu.swe.gameproj.validator;

import java.util.Arrays;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.gmu.swe.gameproj.util.ActVm;

public class ActVmValidator implements Validator{
	
	private final String pattern = "[a-zA-Z,]+";
	private final String[] validCommands = new String[] {
		"play", "buy", "done"
	};
    private String[] actionCardNames = new String[] {
	"Cellar", "Market", "Militia", "Mine", "Moat", "Remodel", "Smithy", "Village", "Woodcutter", "Workshop"
	};
	

	@Override
	public boolean supports(Class<?> arg0) {
		return ActVm.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ActVm actVm = (ActVm)obj;
		String command = actVm.getCommand();
		
		//Command contains illegal characters
		if(!command.matches(pattern)){
			errors.rejectValue("command", "Invalid input");
		}
		//Command is too long
		else if(command.length() > 100){
			errors.rejectValue("command", "Invalid input");
		}
		else{
			//Split the command on spaces
			String[] commandAry = command.split(" ");
			
			//Command contains too many inputs
			if(commandAry.length > 4){
				errors.rejectValue("command", "Invalid input");
			}
			
			//First input is valid (e.g. "play", "buy", "done")
			else if(!Arrays.asList(validCommands).contains(commandAry[0])){
				errors.rejectValue("command", "Unrecognized command");
			}
			else{
				//Play command
				if(commandAry[0].equals("play")){
					handlePlay(commandAry, errors);
				}
				//Buy command
				else if(commandAry[0].equals("buy")){
					handleBuy(commandAry, errors);
				}
				//Done command
				else if(commandAry[0].equals("done")){
					handleDone(commandAry, errors);
				}
			}
		}
		
		
	}
	
	private void handlePlay(String[] commandAry, Errors errors){
		if(!Arrays.asList(actionCardNames).contains(commandAry[1])){
			errors.rejectValue("command", "Unknown card");
		}

	}
	
	private void handleBuy(String[] commandAry, Errors errors){
		
	}
	
	private void handleDone(String[] commandAry, Errors errors){
		
	}
	

}
