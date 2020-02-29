package technest.testcode.accountservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Account model
 * @author sergio.sanchez.gil
 * 
 */


@Entity
@Table(name="TB_ACCOUNT")
public class Account {
	
	
	/**
	 * enum with currency values
	 */
	public enum Currency{
		Euro, Dolar, Libra
	}

	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
	
	@Column(name="NAME", nullable=false)
	private String name;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="CURRENCY", nullable=false)
	private Currency currency;
	
	@Column(name="BALANCE", nullable=false)
	private double balance;
	
	@Column(name="TREASURY", nullable=false)
	private boolean treasury;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public boolean isTreasury() {
		return treasury;
	}
	public void setTreasury(boolean treasury) {
		this.treasury = treasury;
	}
	
	
	
}
