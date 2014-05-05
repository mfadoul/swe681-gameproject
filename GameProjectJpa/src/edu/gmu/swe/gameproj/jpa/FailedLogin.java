package edu.gmu.swe.gameproj.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the FailedLogins database table.
 * 
 */
@Entity
@Table(name="FailedLogins")
@NamedQuery(name="FailedLogin.findAll", query="SELECT f FROM FailedLogin f")
public class FailedLogin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int dailyFailCount;

	private int failCount;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastFailAttemptDate;

	public FailedLogin() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDailyFailCount() {
		return this.dailyFailCount;
	}

	public void setDailyFailCount(int dailyFailCount) {
		this.dailyFailCount = dailyFailCount;
	}

	public int getFailCount() {
		return this.failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public Date getLastFailAttemptDate() {
		return this.lastFailAttemptDate;
	}

	public void setLastFailAttemptDate(Date lastFailAttemptDate) {
		this.lastFailAttemptDate = lastFailAttemptDate;
	}

}