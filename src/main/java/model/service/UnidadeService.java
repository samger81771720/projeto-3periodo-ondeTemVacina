package model.service;

import model.entity.Unidade;
import model.repository.UnidadeRepository;

public class UnidadeService {
	
	UnidadeRepository unidadeRepository = new UnidadeRepository();
	
	public Unidade consultarPorId(int id) {
		return unidadeRepository.consultarPorId(id);
	}
	
}
