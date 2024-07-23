package model.service;

import java.util.ArrayList;
import java.util.List;

import model.dto.EstoqueDTO;
import model.dto.VacinaDTO;
import model.entity.Estoque;
import model.entity.Unidade;
import model.repository.EstoqueRepository;
import model.seletor.EstoqueSeletor;
import model.seletor.VacinaSeletor;

public class EstoqueService {
	
	EstoqueRepository estoqueRepository = new EstoqueRepository();
	
	public ArrayList<Estoque> consultarTodos() {
		return estoqueRepository.consultarTodos();
	}
	
	public boolean excluirEstoqueDaUnidade(int idUnidade, int idVacina) {
		return estoqueRepository.excluirEstoqueDaUnidade(idUnidade, idVacina);
	}
	
	public Estoque salvar(Estoque novoEstoque) {
		return estoqueRepository.salvar(novoEstoque);
	}
	
	public boolean alterar(Estoque estoque) {
		return estoqueRepository.alterar(estoque);
	}
	
	public List<VacinaDTO> consultarVacinaComFiltros(VacinaSeletor seletor){
		return estoqueRepository.consultarVacinaComFiltros(seletor);
	}
	
	public Estoque consultarPorIds(int idUnidade, int idVacina) {
		return estoqueRepository.consultarPorIds(idUnidade, idVacina);
	}
	
	public List<Estoque> consultarEstoquesDaUnidadePorId(Estoque estoqueDaUnidade) {
		return estoqueRepository.consultarEstoquesDaUnidadePorId(estoqueDaUnidade);
	}
	
	public List<EstoqueDTO> consultarEstoquesComFiltrosComoAdministrador(EstoqueSeletor seletor){
		return estoqueRepository.consultarEstoquesComFiltrosComoAdministrador(seletor);
	}
	
}
