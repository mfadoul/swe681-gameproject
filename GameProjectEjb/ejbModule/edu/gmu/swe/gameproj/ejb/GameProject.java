package edu.gmu.swe.gameproj.ejb;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Transient;

import edu.gmu.swe.gameproj.jpa.Card;
import edu.gmu.swe.gameproj.jpa.CardEvent;
import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.jpa.FailedLogin;
import edu.gmu.swe.gameproj.jpa.GameState;
import edu.gmu.swe.gameproj.jpa.Player;
import edu.gmu.swe.gameproj.jpa.User;
import edu.gmu.swe.gameproj.jpa.UserRole;

/**
 * Session Bean implementation class GameProject
 */
@Stateless
@LocalBean
public class GameProject implements GameProjectRemote {
	@PersistenceContext(unitName="GameProjectJpa_pu")
	private EntityManager entityManager;
	
	private static final int DECK_LOCATION = 1;
	private static final int HAND_LOCATION = 2;
	private static final int DISCARD_LOCATION = 3;
	
    /**
     * Default constructor. 
     */
    public GameProject() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public Set<User> getAllUsers() {
	    Query q = entityManager.createQuery(" from User u ");
	    Set<User> resultSet = new HashSet<User>();
	    @SuppressWarnings("unchecked")
		List<User> resultList = (List<User>) q.getResultList();
	    resultSet.addAll(resultList);
		return resultSet;
	}

	@Override
	public boolean doesUserExist(String email) {
		if (getUserByEmail(email)!=null)
			return true;
		else 
			return false;
	}

	@Override
	public User getUserByEmail(String email) {
		final String jpaQlQuery = " from User u where u.email=:email";
		
		Query query = entityManager.createQuery(jpaQlQuery);
		query.setParameter("email", email);

		User user = null;
		try {
			user = (User) query.getSingleResult();
		} catch (Exception e) {
			System.out.println ("User " + email + " does not exist");			
		}

		return user;
	}

	@Override
	public User getUserById(int userId) {
		try {
			return entityManager.find(User.class, userId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean addUser(User user) {
		if (user == null) {
			return false;
		}
		try {
			if (user.getUserRole()==null) {
				UserRole userRole = this.getUserRoleById("player");
				user.setUserRole(userRole);
			}
			// This should fail when the username is already taken.
			user.getUserRole().addUser(user);
			
			entityManager.persist(user);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public UserRole getUserRoleById(String userRoleId) {
		try {
			return entityManager.find(UserRole.class, userRoleId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Set<UserRole> getAllUserRoles() {
	    Query q = entityManager.createQuery(" from UserRole ur ");
	    Set<UserRole> resultSet = new HashSet<UserRole>();
	    @SuppressWarnings("unchecked")
		List<UserRole> resultList = (List<UserRole>) q.getResultList();
	    resultSet.addAll(resultList);
		return resultSet;
	}

	@Override
	public GameState getActiveGameStateByUser(User user) {
		Player player = this.getActivePlayerByUser(user);
		if (player==null) {
			return null;
		}
		return player.getGameState();
	}

	@Override
	public Player getActivePlayerByUser(User user) {
		if (user == null) {
			return null;
		}
		
		for (Player player: user.getPlayers()) {
			// If 'completed' is zero, the game is active
			if ((player.getGameState() != null) && (player.getGameState().getCompleted() == 0)) {
				return player;
			}
		}

		// If we are here, the game couldn't be found.
		return null;		
	}

	// Return true if there was a game to forfeit.
	// Return false if there was not a game to forfeit.
	@Override
	public boolean forfeitActiveGameByUser(User user) {
		GameState gameState = this.getActiveGameStateByUser(user);
		
		if (gameState == null) {
			System.err.println("forfeitActiveGameByUser: Failed to find GameState.");
			return false;
		}
		
		// Close out the game:
		gameState.setCompleted((byte) 1);
		gameState.setEndDate(new Date());
		
		System.out.println("User " + user.getId() + "(" + user.getEmail() + ") is forfeiting game " + gameState.getId() + ".");
		
		// Find the winner
		for (Player player: gameState.getPlayers()) {
			if (player.getUser().getId() != user.getId()) {
				gameState.setWinnerId(player.getId()); // The winner is the person who did not forfeit
			}
		}
		
		// Preserve the rest of the game state.
		entityManager.merge(gameState);
		return true;
	}

	@Override
	public GameState createGameStateByUser(User user) {
		GameState gameState = null;
		if (user == null) {
	 		return gameState;			
		}
		
		gameState = this.getActiveGameStateByUser(user);
		
		// If an active gamestate already exists, don't create a new one!
		if (gameState != null) {
			return gameState;
		}
		
		gameState = new GameState ();
		gameState.setPhase(1);
		gameState.setTurn(1);
		gameState.setBeginDate(new Date());
		gameState.setCards(new ArrayList<Card>());

		entityManager.persist(gameState);
		
		initializeGameState(gameState);
		
		//entityManager.merge(gameState);
		
		System.out.println ("Created game ID " + gameState.getId());
		return gameState;
	}

	@Override
	public GameState getGameStateById(long gameStateId) {
		try {
			return entityManager.find(GameState.class, gameStateId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Player joinGameState(long gameStateId, int userId) {
		System.out.println("GameProject.joinGameState(" + gameStateId + ", " + userId + ")");
		
		GameState gameState = this.getGameStateById(gameStateId);
		User user = this.getUserById(userId);

		// 1. Failure to find GameState
		if (gameState == null) {
			System.out.println("Could not find GameStateId (" + gameStateId + ")");
			return null;
		}
		
		// 2. Failure to find User
		if (user == null) {
			System.out.println("Could not find UserId (" + userId + ")");
			return null;
		}
		
		// 3. Player already exists in the game
		for (Player player: gameState.getPlayers()) {
			if ((player.getUser()!=null) && (player.getUser().getId() == userId)) {
				System.out.println ("Player " + player.getId() + " is already in game " + gameState.getId());
				return player;
			}
		}
		
		if (gameState.getPlayers().size() >= 2) {
			// Only allow two players.  Fail if there are more.
			System.out.println ("Two players are already in game " + gameState.getId());
			return null;
		}
		
		// 5. Create a new Player
		Player player = new Player();
		initializePlayer(player, gameState);
		
		//Since player has not already been added to gameState, set their turn value to the current number 
		// of players + 1
		player.setTurn(gameState.getPlayers().size() + 1);
		
		user.addPlayer(player); // Create the connection on both sides.

		// 5a.  Add the new player to the GameState.
		gameState.addPlayer(player);
		
		// Persist the changes
		entityManager.persist(player);
		entityManager.merge(gameState); // Is this required?
		System.out.println("Game " + gameState.getId() + ": player " + player.getId() + " is joining.");
		
		return player;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GameState> getOpenGames() {
		List<GameState> gameStateList = null;
		
		// Games that are not completed and have one player waiting to play.
		final String jpaQlQuery = " from GameState gs where gs.completed=0 and gs.players.size=1 ";
		Query query = entityManager.createQuery(jpaQlQuery);

		try {
			gameStateList = (List<GameState>) query.getResultList();
			if (gameStateList != null) {
				System.out.println("GameProject.getOpenGames(): Found " + gameStateList.size() + " open games.");
			} 
		} catch (Exception e) {
				System.err.println("Exception: " + e);
		}
		return gameStateList;
	}

	@Override
	public List<GameState> getGamesWonByUser(User user) {
		List<GameState> gameStateList = new ArrayList<GameState>();
		if (user != null) {
			for (Player player: user.getPlayers()) {
				// Avoid null pointer
				if (player.getGameState() != null) {
					// Make sure that the game is complete
					if (player.getGameState().getCompleted()==1) {
						// If the player won, add it to the list
						if (player.getGameState().getWinnerId()==player.getId()) {
							gameStateList.add(player.getGameState());
						}	
					}
				}
			}
		}
		System.out.println("Found " + gameStateList.size() + " games won by " + user.getEmail() + ".");
		return gameStateList;
	}

	@Override
	public List<GameState> getGamesLostByUser(User user) {
		List<GameState> gameStateList = new ArrayList<GameState>();
		if (user != null) {
			for (Player player: user.getPlayers()) {
				// Avoid null pointer
				if (player.getGameState() != null) {
					// Make sure that the game is complete
					if (player.getGameState().getCompleted()==1) {
						// If the player lost, add it to the list
						if (player.getGameState().getWinnerId()!=player.getId()) {
							gameStateList.add(player.getGameState());
						}	
					}
				}
			}
		}
		System.out.println("Found " + gameStateList.size() + " games lost by " + user.getEmail() + "."); 
		return gameStateList;
	}

	@Override
	public Card getCardById(int cardId) {
		try {
			return entityManager.find(Card.class, cardId);
		} catch (Exception e) {
			System.err.println("Exception: " + e);
			return null;
		}
	}

	@Override
	public boolean addActions(Player player, int count) {
		try {
			
			if(player == null) return false;
			
			player.addActionCount(count);
			
			entityManager.merge(player);
			
			return true;
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return false;
		}
	}

	@Override
	public boolean addBuys(Player player, int count) {
		try {

			if(player == null) return false;
			
			player.addBuyCount(count);
			
			entityManager.merge(player);
			
			return true;
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return false;
		}
	}

	@Override
	public boolean addCoins(Player player, int count) {
		try {

			if(player == null) return false;
			
			player.addCoinCount(count);
			
			entityManager.merge(player);
			
			return true;
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return false;
		}
	}
	
	//Precondition handling of biz rules handled by calling controller method
	@Override
	public boolean buy(Player player, Card card, GameState gameState) {
		try {

			if(player == null) return false;
			
			if(card == null) return false;
			
			if(gameState == null) return false;
			
			player.addCoinCount(card.getCost());
			player.addBuyCount(-1);
			card.setPlayer(player);
			card.setLocation(DISCARD_LOCATION);
			gameState.setPhase(2);
			
			entityManager.merge(card);
			entityManager.merge(player);
			entityManager.merge(gameState);
			
			return true;
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return false;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean discard(Player player, ArrayList<CardType> cards) {

		try {
			if (cards == null || cards.size() == 0) return false;
			if(player == null) return false;
			
			
			for(CardType ct : cards){
				Card card = player.getFirstInstanceInHandByType(ct);
				if(card == null){
					return false;
				}
				else{
					card.setLocation(DISCARD_LOCATION);
				}
			}
			
//			final String jpaQlQuery = " from Cards c where c.id IN :cardIds";
//			
//			Query query = entityManager.createQuery(jpaQlQuery);
//			query.setParameter("cardIds", cardIds);
//			
//			List<Card> resultList = (List<Card>) query.getResultList();
			
			
			
			entityManager.merge(player);
			
			return true;
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return false;
		}
	}

	@Override
	public List<Card> draw(Player player, int count) {
		try{
			
			//Check to limit potential DOS attack by setting high count value
			if(count > 10) return null;
			
			
			if(player == null) return null;
			
			List<Card> deck = getCardsByPlayerAndLocation(player.getId(), DECK_LOCATION);
			int i = 0;
			Random myRandom = new Random();
			
			while(i < count){
				if(deck.size() == 0){
					shuffle(player.getId());
					deck = getCardsByPlayerAndLocation(player.getId(), DECK_LOCATION);
				}
				Card card = deck.get(myRandom.nextInt(deck.size()));
				deck.remove(card);
				card.setLocation(HAND_LOCATION);
				
				i++;
			}
			
			entityManager.merge(deck);
			return getCardsByPlayerAndLocation(player.getId(), HAND_LOCATION);
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return null;
		}
	}
	

	@Override
	public boolean trash(Card card) {
		try{

			if(card == null) return false;
			
			card.setPlayer(null);
			card.setLocation(DISCARD_LOCATION);
			
			entityManager.merge(card);
			
			return true;
			
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return false;
		}
	}

	@Override
	public Player getPlayerById(long playerId) {
		try {
			return entityManager.find(Player.class, playerId);
		} catch (Exception e) {
			System.err.println("Exception: " + e);
			return null;
		}
	}

	@Override
	public CardEvent createCardEvent(Card card, GameState gameState, Player player, int location) {
		CardEvent cardEvent = new CardEvent();
		cardEvent.setEventDate(new Date());
		
		if (card!=null) {
			cardEvent.setCardType(card.getCardType());
		} else {
			cardEvent.setCardType(CardType.Unknown.cardTypeId);
		}
		
		if (player!=null) {
			cardEvent.setPlayerId(player.getId());
		} else {
			cardEvent.setPlayerId(0);
		}
		
		if (gameState!=null) {
			cardEvent.setGameStateId(gameState.getId());
		} else {
			cardEvent.setGameStateId(0);
		}
		
		cardEvent.setLocation(location);
	
		try {
			entityManager.persist(cardEvent);
			return cardEvent;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean addCardToHandFromGame(Player player, Card card) {
		return addCard(player, card, HAND_LOCATION);

	}
	
	@Override
	public boolean addCardToDiscardFromGame(Player player, Card card) {
		return addCard(player, card, DISCARD_LOCATION);

	}
	
	

	@Override
	public void registerFailedLogin(String email) {
		if (email == null) throw new NullPointerException();
		
		User user = this.getUserByEmail(email);
		if (user == null) throw new NullPointerException();
		
		FailedLogin failedLogin = this.getFailedLogin(email);

		if (failedLogin == null) {
			// Create a new entry for this ipAddress
			failedLogin = new FailedLogin();
			failedLogin.setId(user.getId());
			failedLogin.setLastFailAttemptDate(new Date());
			failedLogin.setFailCount(1); // This is the first detected failed login for this IP address
			failedLogin.setDailyFailCount(1); // This is the first detected failed login for this IP address
			
			// Persist to the database
			try{
				entityManager.persist(failedLogin);
			}
			catch (Exception e) {
				System.err.println("Exception: " + e);
			}
		} else {
			// Update an existing entry
			Calendar cal = Calendar.getInstance();
			
			cal.setTime(failedLogin.getLastFailAttemptDate());
			int year1 = cal.get(Calendar.YEAR);
			int month1 = cal.get(Calendar.MONTH);
			int day1 = cal.get(Calendar.DAY_OF_MONTH);
			
			Date currentDate = new Date();
			
			cal.setTime(currentDate);
			int year2 = cal.get(Calendar.YEAR);
			int month2 = cal.get(Calendar.MONTH);
			int day2 = cal.get(Calendar.DAY_OF_MONTH);
			
			failedLogin.setLastFailAttemptDate(currentDate);
			
			// Check to see if the date has changed.
			if ((year1==year2) && (month1==month2) && (day1==day2)) {
				// If the day hasn't changed, update the count by one
				
				// Protect against integer overflow
				if (failedLogin.getDailyFailCount() < Integer.MAX_VALUE) {
					failedLogin.setDailyFailCount(failedLogin.getDailyFailCount()+1);
				} else {
					System.err.println("Someone has brute force attacked 2^31 times in one day.  Account = " + user.getEmail());
				}
				
				if (failedLogin.getDailyFailCount()>7) {
					// If the account isn't already locked, lock the account
					if (1 != user.getAccountLocked()) {
						System.out.println ("LOCKING ACCOUNT: " + user.getEmail());
						user.setAccountLocked((byte) 1);  // 1 == locked
						entityManager.merge(user);
					}
				}
			} else {
				// Day has changed.  Reset the fail count to one
				failedLogin.setDailyFailCount(1);
			}
			
			// Protect against integer overflow
			if (failedLogin.getFailCount() < Integer.MAX_VALUE) {
				failedLogin.setFailCount(failedLogin.getFailCount()+1);
			} else {
				System.err.println("Someone has brute force attacked 2^31 times.  Account = " + user.getEmail());
			}

			entityManager.merge(failedLogin);
		}
	}

	@Override
	public FailedLogin getFailedLogin(String email) {
		try {
			User user = this.getUserByEmail(email);
			return entityManager.find(FailedLogin.class, user.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean endTurn(Player player, GameState gameState) {
		
		if(player == null) return false;
		if(gameState == null) return false;
		//Reset Player coins, buys, actions
		player.setActionCount(1);
		player.setBuyCount(1);
		player.setCoinCount(0);
		//Reset Player cards
		for(Card c : player.getHand()){
			c.setLocation(DISCARD_LOCATION);
		}
		
		this.draw(player, 5);

		//Reset GameState phase
		gameState.setPhase(1);
		
		//Reset GameState turn
		if(gameState.getTurn() == 1) gameState.setTurn(2);
		else gameState.setTurn(1);
		
		try{
			entityManager.merge(player);
			entityManager.merge(gameState);
			return true;
		}catch (Exception e) {
			System.err.println("Exception: " + e);
			return false;
		}
		
		
	}
	
	//TODO need to set additional values like turn, phase
	private void initializeGameState(GameState gameState){


		
		int counter = 1;
		
		//8 of each victory
		int victoryCount = 8;
		while(counter <= victoryCount){
			Card estate = new Card();
			estate.setCardType(CardType.Estate.cardTypeId);
			estate.setLocation(DECK_LOCATION);
			gameState.addCard(estate);
			entityManager.persist(estate);
			entityManager.merge(gameState);
			
			Card duchy = new Card();
			duchy.setCardType(CardType.Duchy.cardTypeId);
			duchy.setLocation(DECK_LOCATION);
			gameState.addCard(duchy);
			entityManager.persist(duchy);
			entityManager.merge(gameState);
			
			Card province = new Card();
			province.setCardType(CardType.Province.cardTypeId);
			province.setLocation(DECK_LOCATION);
			gameState.addCard(province);
			entityManager.persist(province);
			entityManager.merge(gameState);
			
			counter++;
		}

		//10 of each action
		int actionCount = 10;
		counter = 1;
		while(counter <= actionCount){
			Card cellar = new Card();
			cellar.setCardType(CardType.Cellar.cardTypeId);
			cellar.setLocation(DECK_LOCATION);
			gameState.addCard(cellar);
			entityManager.persist(cellar);
			entityManager.merge(gameState);

			Card market = new Card();
			market.setCardType(CardType.Market.cardTypeId);
			market.setLocation(DECK_LOCATION);
			gameState.addCard(market);
			entityManager.persist(market);
			entityManager.merge(gameState);
			
			Card militia = new Card();
			militia.setCardType(CardType.Militia.cardTypeId);
			militia.setLocation(DECK_LOCATION);
			gameState.addCard(militia);
			entityManager.persist(militia);
			entityManager.merge(gameState);
			
			Card mine = new Card();
			mine.setCardType(CardType.Mine.cardTypeId);
			mine.setLocation(DECK_LOCATION);
			gameState.addCard(mine);
			entityManager.persist(mine);
			entityManager.merge(gameState);
			
			Card moat = new Card();
			moat.setCardType(CardType.Moat.cardTypeId);
			moat.setLocation(DECK_LOCATION);
			gameState.addCard(moat);
			entityManager.persist(moat);
			entityManager.merge(gameState);
			
			Card remodel = new Card();
			remodel.setCardType(CardType.Remodel.cardTypeId);
			remodel.setLocation(DECK_LOCATION);
			gameState.addCard(remodel);
			entityManager.persist(remodel);
			entityManager.merge(gameState);
			
			Card smithy = new Card();
			smithy.setCardType(CardType.Smithy.cardTypeId);
			smithy.setLocation(DECK_LOCATION);
			gameState.addCard(smithy);
			entityManager.persist(smithy);
			entityManager.merge(gameState);
			
			Card village = new Card();
			village.setCardType(CardType.Village.cardTypeId);
			village.setLocation(DECK_LOCATION);
			gameState.addCard(village);
			entityManager.persist(village);
			entityManager.merge(gameState);
			
			Card woodcutter = new Card();
			woodcutter.setCardType(CardType.Woodcutter.cardTypeId);
			woodcutter.setLocation(DECK_LOCATION);
			gameState.addCard(woodcutter);
			entityManager.persist(woodcutter);
			entityManager.merge(gameState);
			
			Card workshop = new Card();
			workshop.setCardType(CardType.Workshop.cardTypeId);
			workshop.setLocation(DECK_LOCATION);
			gameState.addCard(workshop);
			entityManager.persist(workshop);
			entityManager.merge(gameState);
			
			counter++;
		}
		
		//50 of each treasure
		int treasureCount = 50;
		counter = 1;
		while(counter <= treasureCount){
			Card copper = new Card();
			copper.setCardType(CardType.Copper.cardTypeId);
			copper.setLocation(DECK_LOCATION);
			gameState.addCard(copper);
			entityManager.persist(copper);
			entityManager.merge(gameState);
			
			Card silver = new Card();
			silver.setCardType(CardType.Silver.cardTypeId);
			silver.setLocation(DECK_LOCATION);
			gameState.addCard(silver);
			entityManager.persist(silver);
			entityManager.merge(gameState);
			
			Card gold = new Card();
			gold.setCardType(CardType.Gold.cardTypeId);
			gold.setLocation(DECK_LOCATION);
			gameState.addCard(gold);
			entityManager.persist(gold);
			entityManager.merge(gameState);
			
			counter++;
		}
		
	}
	//TODO need to set initial coin, buy, etc.. counts
	private void initializePlayer(Player player, GameState gameState){
		
		//Initial values
		player.setActionCount(1);
		player.setBuyCount(1);
		player.setCoinCount(0);
		
		List<Card> gameCards = gameState.getCards();
		int maxCopperCount = 7;
		int maxEstateCount = 3;
		int currentCopperCount = 0;
		int currentEstateCount = 0;
		
		for(Card c : gameCards){
			if(c.getType() == CardType.Copper && currentCopperCount <= maxCopperCount){
				player.addCard(c);
				currentCopperCount++;
			}
			else if(c.getType() == CardType.Estate && currentEstateCount <= maxEstateCount){
				player.addCard(c);
				currentEstateCount++;
			}
		}
	}
	
	private boolean addCard(Player player, Card card, int locationId){
		if(player == null) return false;
		if(card == null) return false;

		//Card needs to be owned by game
		if(card.getPlayer() != null) return false;
		if(card.getLocation() != DECK_LOCATION) return false;
		
		card.setPlayer(player);
		card.setLocation(HAND_LOCATION);
		
		try{
			entityManager.merge(card);
			return true;
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private List<Card> getCardsByPlayerAndLocation(long playerId, int locationId){
		try{
			final String jpaQlQuery = " from Cards c where c.playerId = :playerId AND c.location = :locationId";
			
			Query query = entityManager.createQuery(jpaQlQuery);
			query.setParameter("playerId", playerId);
			query.setParameter("locationId", locationId);
			
			
			List<Card> resultList = (List<Card>) query.getResultList();
			
			return resultList;
			
			
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return null;
		}
	}
	
    private void shuffle(long playerId)
    {
		List<Card> cards = getCardsByPlayerAndLocation(playerId, DISCARD_LOCATION);
		for(Card c : cards){
			c.setLocation(DECK_LOCATION);
		}
		
		try{
			entityManager.merge(cards);
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
		}
    }
	


	
	
	


}
