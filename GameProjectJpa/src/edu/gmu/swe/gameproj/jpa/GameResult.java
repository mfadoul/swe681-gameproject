package edu.gmu.swe.gameproj.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the GameResults database table.
 * 
 */
@Entity
@Table(name="GameResults")
@NamedQuery(name="GameResult.findAll", query="SELECT g FROM GameResult g")
public class GameResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date beginDate;

	private byte completed;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="winner")
	private User user1;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="loser")
	private User user2;

	public GameResult() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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

	public User getUser1() {
		return this.user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

}