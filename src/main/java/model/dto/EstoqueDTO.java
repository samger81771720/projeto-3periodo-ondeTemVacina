package model.dto;

import model.entity.Endereco;
import model.entity.Estoque;
import model.entity.Fabricante;
import model.entity.Unidade;
import model.entity.Vacina;

public class EstoqueDTO {
	
	private Unidade unidade;
	private Endereco endereco;
	private Vacina vacina;
	private Fabricante fabricante;
	private Estoque estoque;

	public EstoqueDTO() {
		super();
	}
	
	public Unidade getUnidade() {
		return unidade;
	}
	
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public Vacina getVacina() {
		return vacina;
	}
	
	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	
}
