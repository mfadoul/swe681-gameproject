package edu.gmu.swe.gameproj.ejb;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

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
		List<Player> players = this.getPlayersByGameStateId(gameState.getId());
		for (Player player: players) {
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
		
		List<Card> cards = new ArrayList<Card>();
		
		gameState = new GameState ();
		gameState.setPhase(1);
		gameState.setTurn(1);
		gameState.setBeginDate(new Date());
		//gameState.setPlayers(new ArrayList<Player>());
		gameState.setWinnerId(0);

		//entityManager.persist(gameState);
		
		// initializeGameState(gameState);
		
		// COPYPASTA BEGIN
		int counter = 1;
		
		//8 of each victory
		int victoryCount = 8;
		while(counter <= victoryCount){
			Card estate = new Card();
			estate.setCardType(CardType.Estate.cardTypeId);
			estate.setLocation(DECK_LOCATION);
			estate.setPlayer(null);
			estate.setGameState(gameState);
			cards.add(estate);
			//entityManager.persist(estate);
			
			Card duchy = new Card();
			duchy.setCardType(CardType.Duchy.cardTypeId);
			duchy.setLocation(DECK_LOCATION);
			duchy.setPlayer(null);
			duchy.setGameState(gameState);
			cards.add(duchy);
			//entityManager.persist(duchy);
			
			Card province = new Card();
			province.setCardType(CardType.Province.cardTypeId);
			province.setLocation(DECK_LOCATION);
			province.setPlayer(null);
			province.setGameState(gameState);
			cards.add(province);
			//entityManager.persist(province);
			
			counter++;
		}

		//10 of each action
		int actionCount = 10;
		counter = 1;
		while(counter <= actionCount){
			Card cellar = new Card();
			cellar.setCardType(CardType.Cellar.cardTypeId);
			cellar.setLocation(DECK_LOCATION);
			cellar.setPlayer(null);
			cellar.setGameState(gameState);
			cards.add(cellar);
			//entityManager.persist(cellar);

			Card market = new Card();
			market.setCardType(CardType.Market.cardTypeId);
			market.setLocation(DECK_LOCATION);
			market.setPlayer(null);
			market.setGameState(gameState);
			cards.add(market);
			//entityManager.persist(market);
			
			Card militia = new Card();
			militia.setCardType(CardType.Militia.cardTypeId);
			militia.setLocation(DECK_LOCATION);
			militia.setPlayer(null);
			militia.setGameState(gameState);
			cards.add(militia);
			//entityManager.persist(militia);
			
			Card mine = new Card();
			mine.setCardType(CardType.Mine.cardTypeId);
			mine.setLocation(DECK_LOCATION);
			mine.setPlayer(null);
			mine.setGameState(gameState);
			cards.add(mine);
			//entityManager.persist(mine);
			
			Card moat = new Card();
			moat.setCardType(CardType.Moat.cardTypeId);
			moat.setLocation(DECK_LOCATION);
			moat.setPlayer(null);
			moat.setGameState(gameState);
			cards.add(moat);
			//entityManager.persist(moat);
			
			Card remodel = new Card();
			remodel.setCardType(CardType.Remodel.cardTypeId);
			remodel.setLocation(DECK_LOCATION);
			remodel.setPlayer(null);
			remodel.setGameState(gameState);
			cards.add(remodel);
			//entityManager.persist(remodel);
			
			Card smithy = new Card();
			smithy.setCardType(CardType.Smithy.cardTypeId);
			smithy.setLocation(DECK_LOCATION);
			smithy.setPlayer(null);
			smithy.setGameState(gameState);
			cards.add(smithy);
			//entityManager.persist(smithy);
			
			Card village = new Card();
			village.setCardType(CardType.Village.cardTypeId);
			village.setLocation(DECK_LOCATION);
			village.setPlayer(null);
			village.setGameState(gameState);
			cards.add(village);
			//entityManager.persist(village);
			
			Card woodcutter = new Card();
			woodcutter.setCardType(CardType.Woodcutter.cardTypeId);
			woodcutter.setLocation(DECK_LOCATION);
			woodcutter.setPlayer(null);
			woodcutter.setGameState(gameState);
			cards.add(woodcutter);
			//entityManager.persist(woodcutter);
			
			Card workshop = new Card();
			workshop.setCardType(CardType.Workshop.cardTypeId);
			workshop.setLocation(DECK_LOCATION);
			workshop.setPlayer(null);
			workshop.setGameState(gameState);
			cards.add(workshop);
			//entityManager.persist(workshop);
			
			counter++;
		}
		
		//50 of each treasure
		int treasureCount = 50;
		counter = 1;
		while(counter <= treasureCount){
			Card copper = new Card();
			copper.setCardType(CardType.Copper.cardTypeId);
			copper.setLocation(DECK_LOCATION);
			copper.setPlayer(null);
			copper.setGameState(gameState);
			cards.add(copper);
			//entityManager.persist(copper);
			
			Card silver = new Card();
			silver.setCardType(CardType.Silver.cardTypeId);
			silver.setLocation(DECK_LOCATION);
			silver.setPlayer(null);
			silver.setGameState(gameState);
			cards.add(silver);
			//entityManager.persist(silver);
			
			Card gold = new Card();
			gold.setCardType(CardType.Gold.cardTypeId);
			gold.setLocation(DECK_LOCATION);
			gold.setPlayer(null);
			gold.setGameState(gameState);
			cards.add(gold);
			//entityManager.persist(gold);
			
			counter++;
		}
		//entityManager.merge(gameState);
		gameState.setCards(cards);

		// COPYPASTA END
		entityManager.persist(gameState);
		for (Card card: gameState.getCards()) {
			entityManager.persist(card);
		}
		entityManager.flush();
		System.out.println ("Created game ID " + gameState.getId() + " with " + gameState.getCards().size() + " cards.");
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
		List<Player> players = this.getPlayersByGameStateId(gameStateId);
		
		for (Player player: players) {
			if ((player.getUser()!=null) && (player.getUser().getId() == userId)) {
				System.out.println ("Player " + player.getId() + " is already in game " + gameState.getId());
				return player;
			}
		}
		
		if (this.getPlayersByGameStateId(gameStateId).size() >= 2) {
		//if (gameState.getPlayers().size() >= 2) {
			// Only allow two players.  Fail if there are more.
			System.out.println ("Two players are already in game " + gameState.getId());
			return null;
		}
		
		// 5. Create a new Player
		Player player = new Player();
		user.addPlayer(player); // Create the connection on both sides.
		
		player.setGameState(gameState);

		entityManager.persist(player);
		entityManager.merge(user);
		entityManager.merge(gameState);
		
		initializePlayer(player, gameState);

		//Draw 5 cards
		this.draw(player, 5);

//		// 5a.  Add the new player to the GameState.
		//gameState.addPlayer(player);
		
		System.out.println("Game " + gameState.getId() + ": player " + player.getId() + " is joining.");
		
		return player;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GameState> getOpenGames() {
		List<GameState> gameStateList = null;
		List<GameState> filteredGameStateList = null;
		// Games that are not completed and have one player waiting to play.
		final String jpaQlQuery = " from GameState gs where gs.completed=0";
		Query query = entityManager.createQuery(jpaQlQuery);

		try {
			gameStateList = (List<GameState>) query.getResultList();
			if (gameStateList != null) {
				filteredGameStateList = new ArrayList<GameState>();
				for(GameState gs : gameStateList){
					if(this.getPlayersByGameStateId(gs.getId()).size() == 1){
						filteredGameStateList.add(gs);
					}
				}
				System.out.println("GameProject.getOpenGames(): Found " + filteredGameStateList.size() + " open games.");
			} 
		} catch (Exception e) {
				System.err.println("Exception: " + e);
		}
		return filteredGameStateList;
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
	public Player addActions(Player player, int count) {
		try {
			
			if(player == null) return null;
			
			player.addActionCount(count);
			
			Player response = entityManager.merge(player);
			
			return response;
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return null;
		}
	}

	@Override
	public Player addBuys(Player player, int count) {
		try {

			if(player == null) return null;
			
			player.addBuyCount(count);
			
			Player response = entityManager.merge(player);
			
			return response;
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return null;
		}
	}

	@Override
	public Player addCoins(Player player, int count) {
		try {

			if(player == null) return null;
			
			player.addCoinCount(count);
			
			Player response = entityManager.merge(player);
			
			return response;
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return null;
		}
	}
	
	//Precondition handling of biz rules handled by calling controller method
	@Override
	public boolean buy(Player player, Card card, GameState gameState) {
		try {

			if(player == null) return false;
			
			if(card == null) return false;
			
			if(gameState == null) return false;
			
			player.addCoinCount((card.getCost() * -1));
			player.addBuyCount(-1);
			player.addCard(card);
			card.setLocation(DISCARD_LOCATION);
			gameState.setPhase(2);
			
			entityManager.merge(card);
			entityManager.merge(player);
			entityManager.merge(gameState);
			
			this.createCardEvent(card, gameState, player, DISCARD_LOCATION);

			return true;
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return false;
		}
		
	}


	@Override
	public Card discard(Player player, ArrayList<CardType> cards) {

		try {
			if (cards == null || cards.size() == 0) return null;
			if(player == null) return null;
			
			Card response = null;
			for(CardType ct : cards){
				Card card = player.getFirstInstanceInHandByType(ct);
				if(card == null){
					return null;
				}
				else{
					card.setLocation(DISCARD_LOCATION);
					response = entityManager.merge(card);
				}
			}
									
			return response;
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return null;
		}
	}

	@Override
	public Player draw(Player player, int count) {
		try{
			Player response = null;
			//Check to limit potential DOS attack by setting high count value
			if(count > 10) return null;
			
			
			if(player == null) return null;
			
			List<Card> deck = player.getDeck();
			int i = 0;
			
			while(i < count){
				if(deck.size() == 0){
					shuffle(player);
					deck = player.getDeck();
				}
				Card card = deck.get(randomWithRange(0,deck.size()-1));
				deck.remove(card);
				card.setLocation(HAND_LOCATION);
				
				if(card.getType() == CardType.Copper || card.getType() == CardType.Silver || card.getType() == CardType.Gold){
					player.addCoinCount(card.getType().cardValue); 
				}

				entityManager.merge(card);
				response = entityManager.merge(player);
				i++;
				
				
			}
			return response;
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return null;
		}
	}
	

	@Override
	public Player trash(Player player, CardType cardType) {
		
		try{
			if(cardType == null) return null;
			if(player == null) return null;
			
			Player response = null;
			
			for(Card c : player.getHand()){
				if(c.getType() == cardType) {
					c.setLocation(DISCARD_LOCATION);
					player.removeCard(c);
					//c.setPlayer(null);
					
					entityManager.merge(c);
					break;
				}
			}
			
			response = entityManager.merge(player);
			
			return response;
			
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return null;
		}
//		try{
//			if(card == null) return null;
//			Player response = null;
//			Player player = card.getPlayer();
//
//			if (player != null) {
//				player.removeCard(card);
//			}
//			
//			card.setLocation(DISCARD_LOCATION);
//			
//			
//			entityManager.merge(card);
//			response = entityManager.merge(player);
//			
//			return response;
//			
//		}
//		catch (Exception e) {
//			System.err.println("Exception: " + e);
//			return null;
//		}
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
	public Player addCardToHandFromGame(Player player, CardType cardType) {
//		if(player == null) return null;
//		if(cardType == null) return null;
//		
//		GameState gameState = player.getGameState();
//		
//		for(Card c : gameState.getCards()){
//			//Get cards owned by GameState that aren't in trash
//			if(c.getLocation() == 1 && c.getPlayer() == null){
//				if(c.getType() == cardType) {
//					c.setLocation(HAND_LOCATION);
//					gameState.removeCard(c);
//					player.addCard(c);
//					entityManager.merge(gameState);
//					entityManager.merge(c);
//					break;
//				}
//			}
//		}
//		
//		
//		try{
//			Player response = entityManager.merge(player);
//			return response;
//		}
//		catch (Exception e) {
//			System.err.println("Exception: " + e);
//			return null;
//		}
		
		return addCard(player, cardType, HAND_LOCATION);

	}
	
	@Override
	public Player addCardToDiscardFromGame(Player player, CardType cardType) {
//		
//		if(player == null) return null;
//		if(card == null) return null;
//
//		//Card needs to be owned by game
//		if(card.getPlayer() != null) return null;
//		if(card.getLocation() != DECK_LOCATION) return null;
//		
//		card.setLocation(DISCARD_LOCATION);
//		player.addCard(card);
//		
//		
//		try{
//			entityManager.merge(card);
//			Player response = entityManager.merge(player);
//			return response;
//		}
//		catch (Exception e) {
//			System.err.println("Exception: " + e);
//			return null;
//		}
		return addCard(player, cardType, DISCARD_LOCATION);

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
			entityManager.merge(c);
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
	
	private void initializePlayer(Player player, GameState gameState){
		
		//Initial values
		player.setActionCount(1);
		player.setBuyCount(1);
		player.setCoinCount(0);
		player.setCards(new ArrayList<Card>());

		//Since player has not already been added to gameState, set their turn value to the current number 
		// of players
		player.setTurn(this.getPlayersByGameStateId(gameState.getId()).size());
			
		entityManager.merge(player);
		
		List<Card> gameCards = gameState.getCards();
		int maxCopperCount = 7;
		int maxEstateCount = 3;
		int currentCopperCount = 1;
		int currentEstateCount = 1;
		
		for(Card c : gameCards){
			//Get cards in deck owned by gameState
			if(c.getPlayer() == null && c.getLocation() == DECK_LOCATION){
				if(c.getType() == CardType.Copper && currentCopperCount <= maxCopperCount){

					player.addCard(c);
					entityManager.merge(c);
					currentCopperCount++;
				}
				else if(c.getType() == CardType.Estate && currentEstateCount <= maxEstateCount){
					
					player.addCard(c);
					entityManager.merge(c);
					currentEstateCount++;
				}
			}
		}
		entityManager.merge(player);
	}
	
	private Player addCard(Player player, CardType cardType, int locationId){
		if(player == null) return null;
		if(cardType == null) return null;
		
		GameState gameState = player.getGameState();
		
		for(Card c : gameState.getCards()){
			//Get cards owned by GameState that aren't in trash
			if(c.getLocation() == 1 && c.getPlayer() == null){
				if(c.getType() == cardType) {
					c.setLocation(locationId);
					//gameState.removeCard(c);
					player.addCard(c);
					//entityManager.merge(gameState);
					entityManager.merge(c);
					break;
				}
			}
		}
		
		
		try{
			Player response = entityManager.merge(player);
			return response;
		}
		catch (Exception e) {
			System.err.println("Exception: " + e);
			return null;
		}
	}

//	@SuppressWarnings("unchecked")
//	private List<Card> getCardsByPlayerAndLocation(long playerId, int locationId){
//		try{
//			final String jpaQlQuery = " from Cards c where c.playerId = :playerId AND c.location = :locationId";
//			
//			Query query = entityManager.createQuery(jpaQlQuery);
//			query.setParameter("playerId", playerId);
//			query.setParameter("locationId", locationId);
//			
//			
//			List<Card> resultList = (List<Card>) query.getResultList();
//			
//			return resultList;
//			
//			
//		}
//		catch (Exception e) {
//			System.err.println("Exception: " + e);
//			return null;
//		}
//	}
	
    private void shuffle(Player player)
    {
		List<Card> cards = player.getDiscard();
		for(Card c : cards){
			c.setLocation(DECK_LOCATION);
			entityManager.merge(c);
		}		
    }
    
    private int randomWithRange(int min, int max)
    {
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}

	@Override
	public List<CardEvent> getCardEventsByGameState(GameState gameState) {
		if (gameState == null) {
			return null;
		}
		final String jpaQlQuery = " from CardEvent ce where ce.gameStateId=:gameStateId";
		Query query = entityManager.createQuery(jpaQlQuery);
		query.setParameter("gameStateId", gameState.getId());
		
		@SuppressWarnings("unchecked")
		List<CardEvent> resultList = (List<CardEvent>) query.getResultList();
		Collections.sort(resultList, new Comparator<CardEvent>() {
			public int compare(CardEvent ce1, CardEvent ce2) {
				// Sort by date
				return (ce1.getEventDate().getTime() < ce2.getEventDate().getTime()?-1:1);
			}
		});
		
		return resultList;
	}

	@Override
	public List<Player> getPlayersByGameStateId(long gameStateId) {
		final String jpaQlQuery = " from Player p where p.gameState=:gameState";
		
		Query query = entityManager.createQuery(jpaQlQuery);
		query.setParameter("gameState", this.getGameStateById(gameStateId));

		@SuppressWarnings("unchecked")
		List<Player> resultList = (List<Player>) query.getResultList();
		return resultList;
	}

	@Override
	public boolean isWinner(GameState gameState) {
		TreeMap<String, Integer> victoryCards = gameState.getAvailableVictory();
		
		if(victoryCards.get(CardType.Province.name()) > 0) return false;
		else return true;
	}

	@Override
	public boolean setWinnerEndGame(GameState gameState) {

		if(gameState == null) return false;
		
		// Close out the game:
		gameState.setCompleted((byte) 1);
		gameState.setEndDate(new Date());
		
		
		
		// Find the winner
		List<Player> players = this.getPlayersByGameStateId(gameState.getId());
		
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		//Player 1 has more victory points?
		if(player1.getTotalVictoryPoints() > player2.getTotalVictoryPoints())
			gameState.setWinnerId(player1.getId());
		
		//Player 2 has more victory points?
		else if(player1.getTotalVictoryPoints() < player2.getTotalVictoryPoints())
			gameState.setWinnerId(player2.getId());
		
		//Same # of Victory points. Tie goes to the person with the higher turn order.
		else{
			if(player1.getTurn() > player2.getTurn())
				gameState.setWinnerId(player1.getId());
			else
				gameState.setWinnerId(player2.getId());
		}

		// Preserve the rest of the game state.
		entityManager.merge(gameState);
		return true;
	}

	@Override
	public void attack(Player player) {
		List<Player> players = this.getPlayersByGameStateId(player.getGameState().getId());
		
		for(Player p : players){
			boolean isProtected = false;
			if(p.getId() != player.getId()){
				List<Card> hand = p.getHand();
				for(Card c : hand){
					if(c.getType() == CardType.Moat){
						isProtected = true;
					}
				}
				
				if(!isProtected){
					ArrayList<CardType> discards = new ArrayList<CardType>();
					int count = 0;
					for(Card c : hand){
						if(hand.size() - count <= 3) break;
						else{
							discards.add(c.getType());
							count++;
						}
					}
					this.discard(p, discards);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<GameState> getActiveGames() {
		List<GameState> gameStateList = null;
		Set<GameState> filteredGameStateSet = null;
		// Games that are not completed and have one player waiting to play.
		final String jpaQlQuery = " from GameState gs where gs.completed=0";
		Query query = entityManager.createQuery(jpaQlQuery);

		try {
			gameStateList = query.getResultList();
			if (gameStateList != null) {
				filteredGameStateSet = new HashSet<GameState>();
				for(GameState gs : gameStateList){
					if(this.getPlayersByGameStateId(gs.getId()).size() == 2) {
						filteredGameStateSet.add(gs);
					}
				}
				System.out.println("GameProject.getActiveGames(): Found " + filteredGameStateSet.size() + " active games.");
			} 
		} catch (Exception e) {
				System.err.println("Exception: " + e);
		}
		return filteredGameStateSet;
	}

}
