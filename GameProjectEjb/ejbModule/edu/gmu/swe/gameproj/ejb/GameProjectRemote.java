package edu.gmu.swe.gameproj.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import edu.gmu.swe.gameproj.jpa.Card;
import edu.gmu.swe.gameproj.jpa.CardEvent;
import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.jpa.FailedLogin;
import edu.gmu.swe.gameproj.jpa.GameState;
import edu.gmu.swe.gameproj.jpa.Player;
import edu.gmu.swe.gameproj.jpa.User;
import edu.gmu.swe.gameproj.jpa.UserRole;

@Remote
public interface GameProjectRemote {
	// Users
	public abstract Set<User> getAllUsers();
	public abstract boolean doesUserExist(String email); 
	public abstract User getUserByEmail(String email);
	public abstract User getUserById(int userId);
	public abstract boolean addUser (User user);

	// User Role
	public abstract UserRole getUserRoleById (String userRoleId);
	public abstract Set<UserRole> getAllUserRoles();  // For debug...
	
	// Games
	public abstract GameState createGameStateByUser (User user);
	public abstract GameState getActiveGameStateByUser(User user);
	public abstract GameState getGameStateById(long gameStateId);
	public abstract Player getActivePlayerByUser(User user);
	public abstract boolean forfeitActiveGameByUser (User user);

	public abstract List<GameState> getOpenGames();
	
	public abstract List<GameState> getGamesWonByUser(User user);
	public abstract List<GameState> getGamesLostByUser(User user);

	public abstract Player joinGameState(long gameStateId, int userId);
	//public abstract Player getCurrentTurnPlayer(long gameStateId);
	
	public abstract List<Player> getPlayersByGameStateId(long gameStateId);
	
	//Actions
	public abstract Card getCardById(int cardId);
	//public abstract Card getCardInstanceByTypeByPlayer(Player player, CardType cardType);

	public abstract Player addActions(Player player, int count);
	public abstract Player addBuys(Player player, int count);
	public abstract Player addCoins(Player player, int count);
	public abstract Player addCardToHandFromGame(Player player, Card card);
	public abstract Player addCardToDiscardFromGame(Player player, Card card);

	public abstract Card discard(Player player, ArrayList<CardType> cards);
	public abstract Player draw(Player player, int count);
	public abstract Player trash(Card card);
	
	public abstract Player getPlayerById(long playerId);

	//Buy
	public abstract boolean buy(Player player, Card card, GameState gameState);
	
	//End Turn
	public abstract boolean endTurn(Player player, GameState gameState);
	
	// Card Events
	public abstract CardEvent createCardEvent (Card card, GameState gameState, Player player, int location);
	public abstract List<CardEvent> getCardEventsByGameState (GameState gameState);

	
	// Failed login attempts
	public abstract void registerFailedLogin(String email);
	public abstract FailedLogin getFailedLogin(String email);
}
