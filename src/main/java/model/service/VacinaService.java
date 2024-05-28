package model.service;

import java.util.List;

import model.entity.Vacina;
import model.repository.VacinaRepository;
import model.seletor.VacinaSeletor;

public class VacinaService {
	
	VacinaRepository vacinaRepository = new VacinaRepository();
	
	public Vacina consultarPorId(int id) {
		return vacinaRepository.consultarPorId(id);
	}
	
	public boolean excluir(int id) {
		return vacinaRepository.excluir(id);
	}
	
	public List<VacinaSeletor> consultarComFiltros(VacinaSeletor seletor){
		return vacinaRepository.consultarComFiltros(seletor);
	}

}
