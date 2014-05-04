package edu.gmu.swe.gameproj.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the CardEvents database table.
 * 
 */
@Entity
@Table(name="CardEvents")
@NamedQuery(name="CardEvent.findAll", query="SELECT c FROM CardEvent c")
public class CardEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @TableGenerator(name="TABLE_GEN_CARDEVENT", table="SEQUENCE_TABLE", pkColumnName="SEQ_NAME",
    valueColumnName="SEQ_COUNT", pkColumnValue="CARDEVENT_SEQ")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="TABLE_GEN_CARDEVENT")
	private long id;

	private int cardType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date eventDate;

	private long gameStateId;

	private int location;

	private long playerId;

	public CardEvent() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCardType() {
		return this.cardType;
	}

	public void setCardType(int cardType) {
		this.cardType = cardType;
	}

	public Date getEventDate() {
		return this.eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public long getGameStateId() {
		return this.gameStateId;
	}

	public void setGameStateId(long gameStateId) {
		this.gameStateId = gameStateId;
	}

	public int getLocation() {
		return this.location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public long getPlayerId() {
		return this.playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

}