package edu.gmu.swe.gameproj.mechanics.command;

import edu.gmu.swe.gameproj.jpa.Player;

public class AddCoinsCommand implements ICommand {
    private int coinCount;
    private Player player;

    public AddCoinsCommand(Player _player, int _coinCount)
    {
        if(_player == null) throw new NullPointerException("_player");

        player = _player;
        coinCount = _coinCount;
    }
    @Override
    public void Execute() {
        player.addCoinCount(coinCount);
    }
}
