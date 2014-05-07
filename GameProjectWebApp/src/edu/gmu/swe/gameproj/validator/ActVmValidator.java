package edu.gmu.swe.gameproj.validator;

import java.util.Arrays;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.gmu.swe.gameproj.util.ActVm;

public class ActVmValidator implements Validator{
	private final String pattern = "^([a-zA-Z]{3,4})( [a-zA-Z]{0,10})?( [a-zA-Z,]*)?( [a-zA-Z]{0,10})?$";
	private final String topPattern = "[a-zA-Z, ]+";
	private final String tokenPattern = "[a-zA-Z]+";
	private final String tokenPatternList = "[a-zA-Z,]+";
	private final String[] validCommands = new String[] {
		"play", "buy", "done"
	};
    private String[] actionCardNames = new String[] {
	"Cellar", "Market", "Militia", "Mine", "Moat", "Remodel", "Smithy", "Village", "Woodcutter", "Workshop"
	};
    private String[] treasureCardNames = new String[]{
    		"Copper", "Silver", "Gold"
    };
    private String[] victoryCardNames = new String[]{
    		"Estate", "Duchy", "Province"
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
			errors.rejectValue("command", "Failed Regex");
		}
		//Command is too long
		else if(command.length() > 100){
			errors.rejectValue("command", "Too Long");
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
				errors.rejectValue("command", "Unknown command");
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
		if(commandAry.length < 2){
			errors.rejectValue("command", "Invalid input");
		}
		if(!Arrays.asList(actionCardNames).contains(commandAry[1])){
			errors.rejectValue("command", "Unknown card");
		}

	}
	
	private void handleBuy(String[] commandAry, Errors errors){
		//Buy should only have 2 tokens
		if(commandAry.length != 2){
			errors.rejectValue("command", "Invalid input");
		}

		
		//Second token must be one of the known cards
		if(!Arrays.asList(actionCardNames).contains(commandAry[1])){
			if(!Arrays.asList(treasureCardNames).contains(commandAry[1])){
				if(!Arrays.asList(victoryCardNames).contains(commandAry[1])){
					errors.rejectValue("command", "Unknown card");
				}
			}
		}
	}
	
	private void handleDone(String[] commandAry, Errors errors){
		if(commandAry.length != 1){
			errors.rejectValue("command", "Invalid input");
		}
	}
	

}
