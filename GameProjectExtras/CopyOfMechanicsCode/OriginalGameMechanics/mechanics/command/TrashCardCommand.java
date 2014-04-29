package edu.gmu.swe.gameproj.mechanics.command;

import edu.gmu.swe.gameproj.mechanics.Player;

public class TrashCardCommand implements ICommand{

    private Player player;
    private String cardName;

    public TrashCardCommand(Player _player, String _cardName)
    {
        if(_player == null) throw new NullPointerException("_player");
        if(_cardName == null) throw new NullPointerException("_cardName");

        player = _player;
        cardName = _cardName;
    }
    @Override
    public void Execute() {
        player.trashCard(cardName);
    }
}
