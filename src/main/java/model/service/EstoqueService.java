package model.service;

import java.util.ArrayList;
import java.util.List;

import model.dto.VacinaDTO;
import model.entity.Estoque;
import model.entity.Unidade;
import model.repository.EstoqueRepository;
import model.seletor.VacinaSeletor;

public class EstoqueService {
	
	EstoqueRepository estoqueRepository = new EstoqueRepository();
	
	public List<Estoque> consultarEstoquesDaUnidade(int idUnidade) {
		return estoqueRepository.consultarEstoquesDaUnidade(idUnidade);
	}
	
	public ArrayList<Estoque> consultarTodos() {
		return estoqueRepository.consultarTodos();
	}
	
	public boolean excluirEstoqueDaUnidade(int idUnidade, int idVacina) {
		return estoqueRepository.excluirEstoqueDaUnidade(idUnidade, idVacina);
	}
	
	public Estoque salvar(Estoque novoEstoque) {
		return estoqueRepository.salvar(novoEstoque);
	}
	
	public boolean alterar(Estoque estoqueAnterior) {
		return estoqueRepository.alterar(estoqueAnterior);
	}
	
	public List<VacinaDTO> consultarComFiltros(VacinaSeletor seletor){
		return estoqueRepository.consultarComFiltros(seletor);
	}
	
}
