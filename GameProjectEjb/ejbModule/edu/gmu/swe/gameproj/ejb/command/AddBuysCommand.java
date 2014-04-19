package edu.gmu.swe.gameproj.ejb.command;

import edu.gmu.swe.gameproj.jpa.Player;

public class AddBuysCommand implements ICommand {
    private int buyCount;
    private Player player;

    public AddBuysCommand(Player _player, int _buyCount)
    {
        if(_player == null) throw new NullPointerException("_player");
        player = _player;
        buyCount = _buyCount;
    }
    @Override
    public void Execute() {
        player.addBuyCount(buyCount);
    }
}
