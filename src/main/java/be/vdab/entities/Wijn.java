package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wijnen")
public class Wijn implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "soortid")
	private Soort soort;
	
	private short jaar;
	private byte beoordeling;
	private BigDecimal prijs;
	private long inBestelling;
	
	//CONSTRUCTORS
	protected Wijn() {
	}

	public Wijn(Soort soort, short jaar, byte beoordeling, BigDecimal prijs, long inBestelling){
		this.soort = soort;
		this.jaar = jaar;
		this.beoordeling = beoordeling;
		this.prijs = prijs;
		this.inBestelling = inBestelling;
	}
	
	//GETTERS & SETTERS
	public BigDecimal getPrijs() {
		return prijs;
	}

	public long getInBestelling() {
		return inBestelling;
	}

	public long getId() {
		return id;
	}

	public Soort getSoort() {
		return soort;
	}

	public short getJaar() {
		return jaar;
	}

	public byte getBeoordeling() {
		return beoordeling;
	}
	
	public String getSterretjes(){
		StringBuilder sterretjes = new StringBuilder();
		for (byte teller = 0; teller < beoordeling; teller ++){
			sterretjes.append("&#9733;");
		}
		return sterretjes.toString();
	}

	
	//OVERRIDES
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + jaar;
		result = prime * result + ((soort == null) ? 0 : soort.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Wijn))
			return false;
		Wijn other = (Wijn) obj;
		if (jaar != other.jaar)
			return false;
		if (soort == null) {
			if (other.soort != null)
				return false;
		} else if (!soort.equals(other.soort))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Wijn [id=" + id + ", soort=" + soort + ", jaar=" + jaar + ", beoordeling=" + beoordeling + ", prijs="
				+ prijs + ", inBestelling=" + inBestelling + "]";
	}

	



}
