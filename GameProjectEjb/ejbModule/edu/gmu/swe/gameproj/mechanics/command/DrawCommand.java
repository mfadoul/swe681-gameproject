package edu.gmu.swe.gameproj.mechanics.command;

import edu.gmu.swe.gameproj.jpa.Player;

public class DrawCommand implements ICommand{

    private int drawCount;
    private Player player;
    public DrawCommand(Player _player, int _drawCount)
    {
        if(_player == null) throw new NullPointerException("_player");
        this.drawCount = _drawCount;
    }
    @Override
    public void Execute() {
        player.draw(drawCount);
    }
}
