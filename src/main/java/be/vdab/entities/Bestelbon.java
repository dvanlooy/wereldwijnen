package be.vdab.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import be.vdab.enums.Bestelwijze;
import be.vdab.util.Invoercontrole;
import be.vdab.valueobjects.Adres;
import be.vdab.valueobjects.Bestelbonlijn;

@Entity
@Table(name = "bestelbonnen")
public class Bestelbon implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private LocalDateTime besteld;
	private String naam;
	@Embedded
	private Adres adres;
	private Bestelwijze bestelwijze;
	
	@ElementCollection(fetch=FetchType.LAZY)
	@CollectionTable(name = "bestelbonlijnen", joinColumns = @JoinColumn(name = "bonid"))
	private Set<Bestelbonlijn> bestelbonlijnen;
	


	//CONSTRUCTORS
	protected Bestelbon() {
	}

	public Bestelbon(String naam, Adres adres, Bestelwijze bestelwijze, Set<Bestelbonlijn> bestelbonlijnen) {
		this.besteld = LocalDateTime.now();
		this.naam = naam;
		this.adres = adres;
		this.bestelwijze = bestelwijze;
		this.bestelbonlijnen = bestelbonlijnen;
	}



	//GETTERS
	public long getId() {
		return id;
	}

	public LocalDateTime getBesteld() {
		return besteld;
	}

	public String getNaam() {
		return naam;
	}

	public Adres getAdres() {
		return adres;
	}

	public Bestelwijze getBestelwijze() {
		return bestelwijze;
	}

	public Set<Bestelbonlijn> getBestelbonlijnen() {
		return bestelbonlijnen;
	}

	
	//OVERRIDES
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adres == null) ? 0 : adres.hashCode());
		result = prime * result + ((bestelbonlijnen == null) ? 0 : bestelbonlijnen.hashCode());
		result = prime * result + ((besteld == null) ? 0 : besteld.hashCode());
		result = prime * result + ((bestelwijze == null) ? 0 : bestelwijze.hashCode());
		result = prime * result + ((naam == null) ? 0 : naam.hashCode());
		return result;
	}

	//objects zijn dezelfde wanneer besteld, naam, adres, bestelbonlijnen en bestelwijze gelijk zijn
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Bestelbon))
			return false;
		Bestelbon other = (Bestelbon) obj;
		if (adres == null) {
			if (other.adres != null)
				return false;
		} else if (!adres.equals(other.adres))
			return false;
		if (bestelbonlijnen == null) {
			if (other.bestelbonlijnen != null)
				return false;
		} else if (!bestelbonlijnen.equals(other.bestelbonlijnen))
			return false;
		if (besteld == null) {
			if (other.besteld != null)
				return false;
		} else if (!besteld.equals(other.besteld))
			return false;
		if (bestelwijze != other.bestelwijze)
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
		return "Bestelbon [id=" + id + ", besteld=" + besteld + ", naam=" + naam + ", adres=" + adres + ", bestelwijze="
				+ bestelwijze + ", bestelbonlijnen=" + bestelbonlijnen + "]";
	}
	
	
	
	
	

}
