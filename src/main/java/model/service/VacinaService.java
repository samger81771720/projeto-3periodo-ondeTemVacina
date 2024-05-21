package model.service;

import java.util.ArrayList;

import exception.ControleVacinasException;
import model.entity.Vacina;
import model.repository.AplicacaoRepository;
import model.repository.VacinaRepository;
import model.seletor.VacinaSeletor;

public class VacinaService {
	
	private static final int PESQUISADOR = 1;
	
	private VacinaRepository vacinaRepository = new VacinaRepository();
	
	private AplicacaoRepository aplicacaoRepository = new AplicacaoRepository(); 
	
	public Vacina salvar(Vacina novaVacina) throws ControleVacinasException{
		validarPesquisador(novaVacina);
		return vacinaRepository.salvar(novaVacina);
	}
	
	public Vacina consultarPorId(int id) {
		return vacinaRepository.consultarPorId(id);
	}
	
	public ArrayList<Vacina> consultarTodos() {
		return vacinaRepository.consultarTodos();
	}
	
	public boolean excluir(int id) throws ControleVacinasException{
		validarAplicacaoDeVacinaPorId(id);
		return vacinaRepository.excluir(id);
	}
	
	public boolean alterar(Vacina vacinaParaAlterar) {
		return vacinaRepository.alterar(vacinaParaAlterar);
	}
	
	private void validarPesquisador(Vacina vacina) throws ControleVacinasException{
		if(vacinaRepository.validarPesquisador(vacina)!=PESQUISADOR) {
			throw new ControleVacinasException("O id da pessoa informada para cadastrar essa vacina não é o id de um pesquisador.");
		}
	}
	
	// Vacina não pode ser excluída caso já tenha sido aplicada em pelo menos uma pessoa	
	private void validarAplicacaoDeVacinaPorId(int id) throws ControleVacinasException{	
		if (aplicacaoRepository.validarAplicacaoDeVacinaPorId(id)) {
			throw new ControleVacinasException("Não é possível excluir a vacina do sistema, pois a mesma já foi aplicada em uma pessoa.");
		}
	}
	
	public ArrayList<Vacina> consultarComFiltros(VacinaSeletor seletor){
		return vacinaRepository.consultarComFiltros(seletor);
	}
	
	public int contarTotalRegistros(VacinaSeletor seletor) {
		return vacinaRepository.contarTotalRegistros(seletor);
	}
	
	public int contarPaginas(VacinaSeletor seletor) {
		return this.vacinaRepository.contarPaginas(seletor);
	}

}
