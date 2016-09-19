package be.vdab.repositories;

import be.vdab.entities.Bestelbon;

public class BestelbonRepository extends AbstractRepository {
	
	public Bestelbon read(long id) {
		return getEntityManager().find(Bestelbon.class, id);
	}
	
	public void create(Bestelbon bestelbon) {
		getEntityManager().persist(bestelbon);
		}
}
