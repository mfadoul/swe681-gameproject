package edu.gmu.swe.gameproj.mechanics.command;

import edu.gmu.swe.gameproj.mechanics.Player;

import java.util.ArrayList;

public class DiscardCommand implements ICommand{

    private ArrayList<String> discardNames;
    private Player player;
    public DiscardCommand(Player _player, ArrayList<String> _discardNames)
    {
        if(_player == null) throw new NullPointerException("_player");
        if(_discardNames == null) throw new NullPointerException("_discardNames");

        player = _player;
        this.discardNames = _discardNames;
    }
    @Override
    public void Execute() {
        player.discard(discardNames);
    }
}
