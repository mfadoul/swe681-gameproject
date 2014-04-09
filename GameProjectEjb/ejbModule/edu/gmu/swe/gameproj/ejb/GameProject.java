package edu.gmu.swe.gameproj.ejb;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

}
