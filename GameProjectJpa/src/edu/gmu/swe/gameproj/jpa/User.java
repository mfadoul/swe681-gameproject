package edu.gmu.swe.gameproj.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Users database table.
 * 
 */
@Entity
@Table(name="Users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Lob
	private String aboutme;

	private String email;

	private String firstname;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_active")
	private Date lastActive;

	@Temporal(TemporalType.DATE)
	@Column(name="last_password_change")
	private Date lastPasswordChange;

	private String lastname;

	private String nickname;

	private String password;

	private String salutation;

	//bi-directional many-to-one association to UserRole
	@ManyToOne
	@JoinColumn(name="role")
	private UserRole userRole;

	//bi-directional many-to-one association to GamePurchas
	@OneToMany(mappedBy="user")
	private List<GamePurchases> gamePurchases;

	//bi-directional many-to-one association to GameResult
	@OneToMany(mappedBy="user1")
	private List<GameResult> gameResults1;

	//bi-directional many-to-one association to GameResult
	@OneToMany(mappedBy="user2")
	private List<GameResult> gameResults2;

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAboutme() {
		return this.aboutme;
	}

	public void setAboutme(String aboutme) {
		this.aboutme = aboutme;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Date getLastActive() {
		return this.lastActive;
	}

	public void setLastActive(Date lastActive) {
		this.lastActive = lastActive;
	}

	public Date getLastPasswordChange() {
		return this.lastPasswordChange;
	}

	public void setLastPasswordChange(Date lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalutation() {
		return this.salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public UserRole getUserRole() {
		return this.userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public List<GamePurchases> getGamePurchases() {
		return this.gamePurchases;
	}

	public void setGamePurchases(List<GamePurchases> gamePurchases) {
		this.gamePurchases = gamePurchases;
	}

	public GamePurchases addGamePurchas(GamePurchases gamePurchas) {
		getGamePurchases().add(gamePurchas);
		gamePurchas.setUser(this);

		return gamePurchas;
	}

	public GamePurchases removeGamePurchas(GamePurchases gamePurchas) {
		getGamePurchases().remove(gamePurchas);
		gamePurchas.setUser(null);

		return gamePurchas;
	}

	public List<GameResult> getGameResults1() {
		return this.gameResults1;
	}

	public void setGameResults1(List<GameResult> gameResults1) {
		this.gameResults1 = gameResults1;
	}

	public GameResult addGameResults1(GameResult gameResults1) {
		getGameResults1().add(gameResults1);
		gameResults1.setUser1(this);

		return gameResults1;
	}

	public GameResult removeGameResults1(GameResult gameResults1) {
		getGameResults1().remove(gameResults1);
		gameResults1.setUser1(null);

		return gameResults1;
	}

	public List<GameResult> getGameResults2() {
		return this.gameResults2;
	}

	public void setGameResults2(List<GameResult> gameResults2) {
		this.gameResults2 = gameResults2;
	}

	public GameResult addGameResults2(GameResult gameResults2) {
		getGameResults2().add(gameResults2);
		gameResults2.setUser2(this);

		return gameResults2;
	}

	public GameResult removeGameResults2(GameResult gameResults2) {
		getGameResults2().remove(gameResults2);
		gameResults2.setUser2(null);

		return gameResults2;
	}

}