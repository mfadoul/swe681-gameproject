package edu.gmu.swe.gameproj.ejb.command;

import edu.gmu.swe.gameproj.ejb.cards.Card;
import edu.gmu.swe.gameproj.ejb.cards.CardFactory;
import edu.gmu.swe.gameproj.jpa.Player;

import java.security.InvalidParameterException;

public class BuyCommand implements ICommand {

    private Player player;
    private String cardNameToBuy;

    public BuyCommand(Player _player, String _cardNameToBuy){
        if(_player == null) throw new NullPointerException("_player");
        if(_cardNameToBuy == null) throw new NullPointerException("_cardNameToBuy");

        player = _player;
        cardNameToBuy = _cardNameToBuy;
    }
    @Override
    public void Execute() {
        Card cardToBuy = CardFactory.buildCard(cardNameToBuy);

        if(player.getCoinCount() < cardToBuy.getCost()) throw new InvalidParameterException("Not enough gold");
        if(player.getBuyCount() < 1) throw new InvalidParameterException("Not enough buys");

        player.buyCard(cardNameToBuy, cardToBuy.getCost());

    }
}
