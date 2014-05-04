package edu.gmu.swe.gameproj.mechanics.cards;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.CardType;

public abstract class Card {
	protected Card(CardType cardType) {
		this.cardType = cardType;
	}
	
	public final CardType cardType;
	
}
