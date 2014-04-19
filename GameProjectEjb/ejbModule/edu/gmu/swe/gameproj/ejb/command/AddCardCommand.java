package edu.gmu.swe.gameproj.ejb.command;


import edu.gmu.swe.gameproj.jpa.Player;

public class AddCardCommand implements ICommand {

    private Player player;
    private String cardName;

    public AddCardCommand(Player _player, String _cardName)
    {
        if(_player == null) throw new NullPointerException("_player");
        if(_cardName == null) throw new NullPointerException("_cardname");

        player = _player;
        cardName = _cardName;
    }

    @Override
    public void Execute() {
        player.addCard(cardName);
    }
}
