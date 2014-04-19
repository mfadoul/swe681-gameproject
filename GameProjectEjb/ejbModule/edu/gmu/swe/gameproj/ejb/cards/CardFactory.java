package edu.gmu.swe.gameproj.ejb.cards;

import edu.gmu.swe.gameproj.ejb.cards.Card;
import edu.gmu.swe.gameproj.ejb.cards.action.*;


/**
 * Created by chackett on 4/18/14.
 */
public class CardFactory {
    public static Card buildCard(String cardName){
        if(cardName == null) throw new NullPointerException("cardName");

        Card card = null;
        switch(cardName.toLowerCase()){
            case "cellar":
                card = new Cellar();
                break;
            case "market":
                card = new Market();
                break;
            case "militia":
                card = new Militia();
                break;
            case "mine":
                card = new Mine();
                break;
            case "moat":
                card = new Moat();
                break;
            case "remodel":
                card = new Remodel();
                break;
            case "smithy":
                card = new Smithy();
                break;
            case "village":
                card = new Village();
                break;
            case "woodcutter":
                card = new Woodcutter();
                break;
            case "workshop":
                card = new Workshop();
                break;
            default:
                break;

        }

        return card;
    }
}
