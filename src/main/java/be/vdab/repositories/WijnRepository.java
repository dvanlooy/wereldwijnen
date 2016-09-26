package be.vdab.repositories;

import java.util.List;

import javax.persistence.LockModeType;

import be.vdab.entities.Soort;
import be.vdab.entities.Wijn;

public class WijnRepository extends AbstractRepository {

	public Wijn read(long id) {
		return getEntityManager().find(Wijn.class, id);
	}
	
	public Wijn readWithLock(long id) {
		return getEntityManager().find(Wijn.class, id, LockModeType.PESSIMISTIC_WRITE);
	}
	
	public List<Wijn> findPerSoort(Soort soort) {
		return getEntityManager().createNamedQuery("Wijn.findPerSoort", Wijn.class)
				.setParameter("soort", soort)
				.getResultList();
	}
}
