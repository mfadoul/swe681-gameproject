package edu.gmu.swe.gameproj.mechanics.command;

import edu.gmu.swe.gameproj.jpa.Player;



public class AddActionsCommand implements ICommand{

    private int actionCount;
    private Player player;
    public AddActionsCommand(Player _player, int _actionCount)
    {
        if(_player == null) throw new NullPointerException("_player");
        player = _player;
        this.actionCount = _actionCount;
    }

    @Override
    public void Execute() {
        player.addActionCount(actionCount);
    }
}
