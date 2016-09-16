package be.vdab.valueobjects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import be.vdab.entities.Wijn;
import be.vdab.util.Invoercontrole;

@Embeddable
public class Bestelbonlijn implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "wijnid")
	private Wijn wijn;
	
	private long aantal;

	//CONSTRUCTORS
	protected Bestelbonlijn() {
	}

	public Bestelbonlijn(Wijn wijn, long aantal) throws IllegalArgumentException, NullPointerException {
		this.wijn = Objects.requireNonNull(wijn, "wijn is verplicht");
		this.aantal = Invoercontrole.positiveLong(aantal, "aantal moet een positief getal zijn");
	}
	
	//GETTERS
	public Wijn getWijn() {
		return wijn;
	}

	public long getAantal() {
		return aantal;
	}
	
	public BigDecimal getTotaleWaarde(){
		return this.wijn.getPrijs().multiply(BigDecimal.valueOf(aantal));
	}

	
	

}
