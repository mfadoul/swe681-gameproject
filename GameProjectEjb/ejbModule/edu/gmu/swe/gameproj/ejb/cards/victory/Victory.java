package edu.gmu.swe.gameproj.ejb.cards.victory;

import edu.gmu.swe.gameproj.jpa.Player;
import edu.gmu.swe.gameproj.ejb.cards.Card;

public abstract class Victory extends Card {

    protected Victory(int _victoryPoints, String _name, int _cost){
        super(_name, _cost);
        this.victoryPoints = _victoryPoints;
    }

    private int victoryPoints;
    public int getVictoryPoints() {
        return victoryPoints;
    }


    public void AddVictoryPoints(Player player)
    {
        //todo: Implement
    }

}
