package edu.gmu.swe.gameproj.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import edu.gmu.swe.gameproj.jpa.Card;
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
	
	//Cards
	public abstract Card getCardById(int cardId);
	public abstract boolean addActions(int playerId, int count);
	public abstract boolean addBuys(int playerId, int count);
	public abstract boolean addCoins(int playerId, int count);
	//public abstract boolean addCard();
	public abstract boolean buy(int playerId, int cardId);
	public abstract boolean discard(int playerId, ArrayList<Integer> cardIds);
	public abstract boolean draw(int playerId, int count);
	public abstract boolean trash(int playerId, int cardId);
	
	public abstract Player getPlayerById(int playerId);
}
