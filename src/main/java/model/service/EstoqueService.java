package model.service;

import java.util.ArrayList;
import java.util.List;

import model.entity.Estoque;
import model.entity.Unidade;
import model.repository.EstoqueRepository;

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
	
}
