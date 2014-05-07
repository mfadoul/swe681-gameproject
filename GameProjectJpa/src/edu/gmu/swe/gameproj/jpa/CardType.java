package edu.gmu.swe.gameproj.jpa;

public enum CardType {
	Unknown (0, "unknown", 0, 0),
	
	// Action
	Cellar(101, "Cellar", 2, 0),
	Market(102, "Market", 5, 0),
	Militia(103, "Militia", 4, 0),
	Mine(104, "Mine", 5, 0),
	Moat(105, "Moat", 2, 0),
	Remodel(106, "Remodel", 4, 0),
	Smithy(107, "Smithy", 4, 0),
	Village(108, "Village", 3, 0),
	Woodcutter(109, "Woodcutter", 3, 0),
	Workshop(110, "Workshop", 3, 0),
	
	// Treasure
	Copper(201, "Copper", 0, 1),
	Silver(202, "Silver", 3, 2),
	Gold(203, "Gold", 6, 3),
	
	// Victory
	Estate(301, "Estate", 2, 1),
	Duchy(302, "Duchy", 5, 3),
	Province(303, "Province", 8, 6);

	
	private CardType (int cardTypeId, String cardName, int cardCost, int cardValue) {
		this.cardTypeId = cardTypeId;
		this.cardName   = cardName;
		this.cardCost = cardCost;
		this.cardValue = cardValue;
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
			if (cardType.cardName.equals(cardName)) {
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
	final public int cardValue;
}
