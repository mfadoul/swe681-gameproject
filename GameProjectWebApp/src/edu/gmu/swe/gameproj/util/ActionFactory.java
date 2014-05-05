package edu.gmu.swe.gameproj.util;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.mechanics.cards.action.*;

public class ActionFactory {
	public static Action buildCard(String[] input, GameProjectRemote gameProject){
	
	    Action card = null;
	    switch(input[1].toLowerCase()){
	        case "cellar":
	            card = new Cellar(gameProject);
	            break;
	        case "market":
	            card = new Market(gameProject);
	            break;
	        case "militia":
	            card = new Militia(gameProject);
	            break;
	        case "mine":
	            card = new Mine(gameProject);
	            break;
	        case "moat":
	            card = new Moat(gameProject);
	            break;
	        case "remodel":
	            card = new Remodel(gameProject);
	            break;
	        case "smithy":
	            card = new Smithy(gameProject);
	            break;
	        case "village":
	            card = new Village(gameProject);
	            break;
	        case "woodcutter":
	            card = new Woodcutter(gameProject);
	            break;
	        case "workshop":
	            card = new Workshop(gameProject);
	            break;
	        default:
	            break;
	
	    }
	
	    return card;
	}

}
