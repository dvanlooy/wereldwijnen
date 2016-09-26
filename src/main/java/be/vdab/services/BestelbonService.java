package be.vdab.services;

import be.vdab.entities.Bestelbon;
import be.vdab.repositories.BestelbonRepository;
import be.vdab.repositories.WijnRepository;
import be.vdab.valueobjects.Bestelbonlijn;

public class BestelbonService extends AbstractService {

	private final BestelbonRepository bestelbonRepository = new BestelbonRepository();
	private final WijnRepository wijnRepository = new WijnRepository();

	public void create(Bestelbon bestelbon) {
		beginTransaction();
		for (Bestelbonlijn bestelbonlijn : bestelbon.getBestelbonlijnen()) {
			Long aantal = bestelbonlijn.getAantal();
			wijnRepository
			.readWithLock(bestelbonlijn
					.getWijn()
					.getId())
			.addInBestelling(aantal);
		}
		bestelbonRepository.create(bestelbon);
		commit();
	}
}
