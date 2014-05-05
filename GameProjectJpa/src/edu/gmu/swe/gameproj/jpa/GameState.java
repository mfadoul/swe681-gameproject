package edu.gmu.swe.gameproj.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the GameStates database table.
 * 
 */
@Entity
@Table(name="GameStates")
@NamedQuery(name="GameState.findAll", query="SELECT g FROM GameState g")
public class GameState implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @TableGenerator(name="TABLE_GEN_GAMESTATE", table="SEQUENCE_TABLE", pkColumnName="SEQ_NAME",
    valueColumnName="SEQ_COUNT", pkColumnValue="GAMESTATE_SEQ")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="TABLE_GEN_GAMESTATE")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date beginDate;

	private byte completed;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	private int turn;

	private int phase;

	private long winnerId;

	//bi-directional many-to-one association to Card
	@OneToMany(mappedBy="gameState", fetch=FetchType.EAGER)
	private List<Card> cards;

	//bi-directional many-to-one association to Player
	@OneToMany(mappedBy="gameState", fetch=FetchType.EAGER)
	private List<Player> players;

	public GameState() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public byte getCompleted() {
		return this.completed;
	}

	public void setCompleted(byte completed) {
		this.completed = completed;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getTurn() {
		return this.turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public long getWinnerId() {
		return this.winnerId;
	}

	public void setWinnerId(long winnerId) {
		this.winnerId = winnerId;
	}

	public List<Card> getCards() {
		return this.cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public Card addCard(Card card) {
		getCards().add(card);
		card.setGameState(this);

		return card;
	}

	public Card removeCard(Card card) {
		getCards().remove(card);
		card.setGameState(null);

		return card;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Player addPlayer(Player player) {
		getPlayers().add(player);
		player.setGameState(this);

		return player;
	}

	public Player removePlayer(Player player) {
		getPlayers().remove(player);
		player.setGameState(null);

		return player;
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}

}