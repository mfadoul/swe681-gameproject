package edu.gmu.swe.gameproj.mechanics.command;


import java.util.ArrayList;

import edu.gmu.swe.gameproj.jpa.Card;
import edu.gmu.swe.gameproj.jpa.Player;

public class DiscardCommand implements ICommand{

    private ArrayList<Card> discards;
    private Player player;
    public DiscardCommand(Player _player, ArrayList<Card> _discards)
    {
        if(_player == null) throw new NullPointerException("_player");
        if(_discards == null) throw new NullPointerException("_discards");

        player = _player;
        this.discards = _discards;
    }
    @Override
    public void Execute() {
    	
        player.discard(discards);
    }
}
