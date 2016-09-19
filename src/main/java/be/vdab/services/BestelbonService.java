package be.vdab.services;

import be.vdab.entities.Bestelbon;
import be.vdab.repositories.BestelbonRepository;

public class BestelbonService extends AbstractService {
	
private final BestelbonRepository bestelbonRepository = new BestelbonRepository();
	
public void create(Bestelbon bestelbon) {
	beginTransaction();
	bestelbonRepository.create(bestelbon);
	commit();
	}
}
