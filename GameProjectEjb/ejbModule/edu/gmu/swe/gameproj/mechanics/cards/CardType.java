package edu.gmu.swe.gameproj.mechanics.cards;

public enum CardType {
	Unknown (0, "unknown"),
	
	// Action
	Cellar(101, "Cellar"),
	Market(102, "Market"),
	Militia(103, "Militia"),
	Mine(104, "Mine"),
	Moat(105, "Moat"),
	Remodel(106, "Remodel"),
	Smithy(107, "Smithy"),
	Village(108, "Village"),
	Woodcutter(109, "Woodcutter"),
	Workshop(110, "Workshop"),
	
	// Treasure
	Copper(201, "Copper"),
	Silver(202, "Silver"),
	Gold(203, "Gold"),
	
	// Victory
	Estate(301, "Estate"),
	Duchy(302, "Duchy"),
	Province(303, "Province");

	
	private CardType (int cardTypeId, String cardName) {
		this.cardTypeId = cardTypeId;
		this.cardName   = cardName;
	}
	
	public static CardType getCardType(int cardTypeId) {
		for (CardType cardType: values()) {
			if (cardType.cardTypeId == cardTypeId) {
				return cardType;
			}
		}
		return CardType.Unknown;
	}
	
	public static CardType getCardType(String cardName) {
		for (CardType cardType: values()) {
			if (cardType.cardName == cardName) {
				return cardType;
			}
		}
		return CardType.Unknown;
	}
	
	final public int cardTypeId;
	final public String cardName;
}
