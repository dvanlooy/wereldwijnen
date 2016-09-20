package be.vdab.repositories;

import be.vdab.entities.Bestelbon;

public class BestelbonRepository extends AbstractRepository {
	
	public void create(Bestelbon bestelbon) {
		getEntityManager().persist(bestelbon);
		}
}
