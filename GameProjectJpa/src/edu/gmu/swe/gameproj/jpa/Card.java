package edu.gmu.swe.gameproj.jpa;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the Cards database table.
 * 
 */
@Entity
@Table(name="Cards")
@NamedQuery(name="Card.findAll", query="SELECT c FROM Card c")
public class Card implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @TableGenerator(name="TABLE_GEN_CARD", table="SEQUENCE_TABLE", pkColumnName="SEQ_NAME",
    valueColumnName="SEQ_COUNT", pkColumnValue="CARD_SEQ")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="TABLE_GEN_CARD")
	private long id;

	private int cardType;

	private int location;

	//bi-directional many-to-one association to GameState
	@ManyToOne
	@JoinColumn(name="gameStateId")
	private GameState gameState;

	//bi-directional many-to-one association to Player
	@ManyToOne
	@JoinColumn(name="playerId")
	private Player player;

	public Card() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCardType() {
		return this.cardType;
	}

	public void setCardType(int cardType) {
		this.cardType = cardType;
	}

	public int getLocation() {
		return this.location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public GameState getGameState() {
		return this.gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	
	@Transient
	public CardType getType(){
		return CardType.getCardType(this.cardType);
	}
	
	@Transient
	public int getCost(){
		return getType().getCost();
	}

}