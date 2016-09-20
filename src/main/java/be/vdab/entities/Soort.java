package be.vdab.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "soorten")
public class Soort implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String naam;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "landid")
	private Land land;


	
	
	//CONSTRUCTORS
	protected Soort() {
	}

	public Soort(String naam, Land land){
		this.naam = naam;
		this.land = land;
	}
	
	
	//GETTERS
	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public Land getLand() {
		return land;
	}

	//OVERRIDES
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((land == null) ? 0 : land.hashCode());
		result = prime * result + ((naam == null) ? 0 : naam.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Soort))
			return false;
		Soort other = (Soort) obj;
		if (land == null) {
			if (other.land != null)
				return false;
		} else if (!land.equals(other.land))
			return false;
		if (naam == null) {
			if (other.naam != null)
				return false;
		} else if (!naam.equals(other.naam))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Soort [id=" + id + ", naam=" + naam + ", land=" + land + "]";
	}
		
}
