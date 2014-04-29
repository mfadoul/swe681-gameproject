package edu.gmu.swe.gameproj.jpa;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the Players database table.
 * 
 */
@Entity
@Table(name="Players")
@NamedQuery(name="Player.findAll", query="SELECT p FROM Player p")
public class Player implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @TableGenerator(name="TABLE_GEN_PLAYER", table="SEQUENCE_TABLE", pkColumnName="SEQ_NAME",
    valueColumnName="SEQ_COUNT", pkColumnValue="PLAYER_SEQ")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="TABLE_GEN_PLAYER")
	private long id;

	private int actionCount;

	private int buyCount;

	private int coinCount;

	private int treasure;

	private int turn;

	//bi-directional many-to-one association to Card
	@OneToMany(mappedBy="player", fetch=FetchType.EAGER)
	private List<Card> cards;

	//bi-directional many-to-one association to GameState
	@ManyToOne
	@JoinColumn(name="gameStateId")
	private GameState gameState;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;

	public Player() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getActionCount() {
		return this.actionCount;
	}

	public void setActionCount(int actionCount) {
		this.actionCount = actionCount;
	}

	public int getBuyCount() {
		return this.buyCount;
	}

	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	public int getCoinCount() {
		return this.coinCount;
	}

	public void setCoinCount(int coinCount) {
		this.coinCount = coinCount;
	}

	public int getTreasure() {
		return this.treasure;
	}

	public void setTreasure(int treasure) {
		this.treasure = treasure;
	}

	public int getTurn() {
		return this.turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public List<Card> getCards() {
		return this.cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public Card addCard(Card card) {
		getCards().add(card);
		card.setPlayer(this);

		return card;
	}

	public Card removeCard(Card card) {
		getCards().remove(card);
		card.setPlayer(null);

		return card;
	}

	public GameState getGameState() {
		return this.gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}