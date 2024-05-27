package model.service;

import model.entity.Vacina;
import model.repository.VacinaRepository;

public class VacinaService {
	
	VacinaRepository vacinaRepository = new VacinaRepository();
	
	public Vacina consultarPorId(int id) {
		return vacinaRepository.consultarPorId(id);
	}
	
	public boolean excluir(int id) {
		return vacinaRepository.excluir(id);
	}

}
