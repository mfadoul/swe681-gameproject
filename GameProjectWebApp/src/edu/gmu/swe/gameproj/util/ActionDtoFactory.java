package edu.gmu.swe.gameproj.util;

import java.util.ArrayList;

import edu.gmu.swe.gameproj.mechanics.cards.action.ActionDto;
import edu.gmu.swe.gameproj.jpa.CardType;

public class ActionDtoFactory {
	public static ActionDto buildDto(String[] input){
		
	    ActionDto dto = new ActionDto();
	    switch(input[1].toLowerCase()){
	        case "cellar":
	        	String[] discardArray = input[2].split(",");
	        	dto.discardCards = new ArrayList<CardType>();
	        	for(String s : discardArray){
	        		CardType cardType = CardType.getCardType(s);
	        		if(cardType == CardType.Unknown){
	        			return null;
	        		}
	        		else{
	        			dto.discardCards.add(cardType);
	        		}
	        	}
	            break;
	        case "market":
	            //No data to add
	            break;
	        case "militia":
	            //No data to add
	            break;
	        case "mine":
	            CardType oldTreasureCardType = CardType.getCardType(input[2]);
	            if(oldTreasureCardType == CardType.Unknown){
        			return null;
        		}
	            else{
	            	dto.oldCard = oldTreasureCardType;
	            }
	            
	            CardType newTreasureCardType = CardType.getCardType(input[3]);
	            if(newTreasureCardType == CardType.Unknown){
        			return null;
        		}
	            else{
	            	dto.newCard = newTreasureCardType;
	            }
	            break;
	        case "moat":
	            //No data to add
	            break;
	        case "remodel":
	        	CardType oldCardType = CardType.getCardType(input[2]);
	            if(oldCardType == CardType.Unknown){
        			return null;
        		}
	            else{
	            	dto.oldCard = oldCardType;
	            }
	            
	            CardType newCardType = CardType.getCardType(input[3]);
	            if(newCardType == CardType.Unknown){
        			return null;
        		}
	            else{
	            	dto.newCard = newCardType;
	            }
	            break;
	        case "smithy":
	            //No data to add
	            break;
	        case "village":
	            //No data to add
	            break;
	        case "woodcutter":
	            //No data to add
	            break;
	        case "workshop":
	            CardType newCardType2 = CardType.getCardType(input[2]);
	            if(newCardType2 == CardType.Unknown){
        			return null;
        		}
	            else{
	            	dto.newCard = newCardType2;
	            }
	            break;
	        default:
	            break;
	
	    }
	
	    return dto;
	}
}
