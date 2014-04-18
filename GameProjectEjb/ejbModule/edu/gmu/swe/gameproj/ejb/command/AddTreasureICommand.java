package edu.gmu.swe.gameproj.ejb.command;

import edu.gmu.swe.gameproj.ejb.Player;

public class AddTreasureICommand implements ICommand {

    private Player player;
    private int treasure;

    public AddTreasureICommand(Player _player, int _treasure)
    {
        if(_player == null) throw new NullPointerException("_player");
        player = _player;
        treasure = _treasure;
    }

    @Override
    public void Execute() {
         player.addTreasure(treasure);
    }
}
