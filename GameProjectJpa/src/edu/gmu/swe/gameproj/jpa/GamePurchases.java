package edu.gmu.swe.gameproj.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the GamePurchases database table.
 * 
 */
@Entity
@Table(name="GamePurchases")
@NamedQuery(name="GamePurchases.findAll", query="SELECT g FROM GamePurchases g")
public class GamePurchases implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String coinsPurchased;

	@Column(name="package")
	private String package_;

	private int price;

	@Temporal(TemporalType.TIMESTAMP)
	private Date purchaseDate;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="playerId")
	private User user;

	public GamePurchases() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCoinsPurchased() {
		return this.coinsPurchased;
	}

	public void setCoinsPurchased(String coinsPurchased) {
		this.coinsPurchased = coinsPurchased;
	}

	public String getPackage_() {
		return this.package_;
	}

	public void setPackage_(String package_) {
		this.package_ = package_;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}