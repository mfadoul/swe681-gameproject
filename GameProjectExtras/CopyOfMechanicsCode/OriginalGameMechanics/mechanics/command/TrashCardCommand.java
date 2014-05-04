package edu.gmu.swe.gameproj.mechanics.command;

import edu.gmu.swe.gameproj.jpa.Card;
import edu.gmu.swe.gameproj.jpa.Player;



public class TrashCardCommand implements ICommand{

    private Player player;
    private Card card;

    public TrashCardCommand(Player _player, Card _card)
    {
        if(_player == null) throw new NullPointerException("_player");
        if(_card == null) throw new NullPointerException("_card");

        player = _player;
        card = _card;
    }
    @Override
    public void Execute() {
        player.removeCard(card);
    }
}
