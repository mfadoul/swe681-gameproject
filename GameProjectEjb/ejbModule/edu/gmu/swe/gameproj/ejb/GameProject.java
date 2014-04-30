package edu.gmu.swe.gameproj.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
		gameState.setBeginDate(new Date());
		entityManager.persist(gameState);
		
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
		System.out.println("Found " + gameStateList.size() + " games won by " + user.getEmail() + ".");
		return gameStateList;
	}

	@Override
	public List<GameState> getGamesLostByUser(User user) {
		List<GameState> gameStateList = new ArrayList<GameState>();
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
		System.out.println("Found " + gameStateList.size() + " games lost by " + user.getEmail() + "."); 
		return gameStateList;
	}

}
