package edu.gmu.swe.gameproj.jpa;

import java.io.Serializable;

import javax.persistence.*;


import java.util.ArrayList;
import java.util.Collections;
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
	
	private static final int DECK_LOCATION = 1;
	private static final int HAND_LOCATION = 2;
	private static final int DISCARD_LOCATION = 3;

	@Id
    @TableGenerator(name="TABLE_GEN_PLAYER", table="SEQUENCE_TABLE", pkColumnName="SEQ_NAME",
    valueColumnName="SEQ_COUNT", pkColumnValue="PLAYER_SEQ")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="TABLE_GEN_PLAYER")
	private long id;

	private int actionCount;

	private int buyCount;

	private int coinCount;

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
		//TODO need to handle where to remove to (trash, discard)
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
	
//	@Transient
//	private ArrayList<Card> getDeck(){
//		ArrayList<Card> deck = new ArrayList<Card>();
//		for(Card c : cards){
//			if(c.getLocation() == DECK_LOCATION) deck.add(c);
//		}
//		
//		return deck;
//	}
//	
//	@Transient
//	private ArrayList<Card> getHand(){
//		ArrayList<Card> hand = new ArrayList<Card>();
//		for(Card c : cards){
//			if(c.getLocation() == HAND_LOCATION) hand.add(c);
//		}
//		return hand;
//	}
//	
//	@Transient
//	private ArrayList<Card> getDiscard(){
//		ArrayList<Card> discard = new ArrayList<Card>();
//		for(Card c : cards){
//			if(c.getLocation() == DISCARD_LOCATION) discard.add(c);
//		}
//		
//		return discard;
//	}
//	
//	
//	@Transient
//    private void shuffle()
//    {
//		ArrayList<Card> cards = getDiscard();
//		for(Card c : cards){
//			c.setLocation(DECK_LOCATION);
//		}
//
//        Collections.shuffle(cards);
//    }
	
	@Transient
	public boolean hasCard(Card card){
		return cards.contains(card);
	}
	
	@Transient
	public void addActionCount(int count){
		this.actionCount += count;
	}
	
	@Transient
	public void addCoinCount(int count){
		this.coinCount += count;
	}
	
	@Transient
	public void addBuyCount(int count){
		this.buyCount += count;
	}
	
//	@Transient
//	public void draw(int drawCount){
//		int i = 0;
//		ArrayList<Card> deck = getDeck();
//        while(i < drawCount)
//        {
//            if(deck.size() == 0) {
//            	shuffle();
//            	deck = getDeck();
//            }
//            Card card = deck.get(0);
//            card.setLocation(HAND_LOCATION);
//            i++;
//        }
//	}
	

	
	

}