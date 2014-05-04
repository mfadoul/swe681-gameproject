package edu.gmu.swe.gameproj.jpa;

public enum CardType {
	Unknown (0, "unknown", 0),
	
	// Action
	Cellar(101, "Cellar", 2),
	Market(102, "Market", 5),
	Militia(103, "Militia", 4),
	Mine(104, "Mine", 5),
	Moat(105, "Moat", 2),
	Remodel(106, "Remodel", 4),
	Smithy(107, "Smithy", 4),
	Village(108, "Village", 3),
	Woodcutter(109, "Woodcutter", 3),
	Workshop(110, "Workshop", 3),
	
	// Treasure
	Copper(201, "Copper", 0),
	Silver(202, "Silver", 3),
	Gold(203, "Gold", 6),
	
	// Victory
	Estate(301, "Estate", 2),
	Duchy(302, "Duchy", 5),
	Province(303, "Province", 8);

	
	private CardType (int cardTypeId, String cardName, int cardCost) {
		this.cardTypeId = cardTypeId;
		this.cardName   = cardName;
		this.cardCost = cardCost;
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
	
	// Don't really need the public methods, since the members are public
	public String getName() {
		return this.cardName;
	}
	
	public int getCost () {
		return this.cardCost;
	}
	
	final public int cardTypeId; // Persist to the database as the cardTypeId.
	final public String cardName;
	final public int cardCost;
}
