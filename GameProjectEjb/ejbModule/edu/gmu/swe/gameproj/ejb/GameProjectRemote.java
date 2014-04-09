package edu.gmu.swe.gameproj.ejb;

import java.util.Set;

import javax.ejb.Remote;

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

}
