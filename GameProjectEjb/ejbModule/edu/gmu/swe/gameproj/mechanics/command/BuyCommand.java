package edu.gmu.swe.gameproj.mechanics.command;


import edu.gmu.swe.gameproj.jpa.Card;
import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.jpa.Player;

import java.security.InvalidParameterException;

public class BuyCommand implements ICommand {

    private Player player;
    private Card cardToBuy;

    public BuyCommand(Player _player, Card _cardToBuy){
        if(_player == null) throw new NullPointerException("_player");
        if(_cardToBuy == null) throw new NullPointerException("_cardToBuy");

        player = _player;
        cardToBuy = _cardToBuy;
    }
    @Override
    public void Execute() {
    	
    	CardType cardType = CardType.getCardType(cardToBuy.getCardType());

        if(player.getCoinCount() < cardType.getCost()) throw new InvalidParameterException("Not enough gold");
        if(player.getBuyCount() < 1) throw new InvalidParameterException("Not enough buys");

        player.buyCard(cardToBuy);

    }
}
