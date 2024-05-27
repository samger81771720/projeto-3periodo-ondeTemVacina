package model.seletor;

import model.entity.Contato;
import model.entity.Endereco;
import model.entity.Fabricante;
import model.entity.Unidade;
import model.entity.Vacina;

public class VacinaSeletor extends BaseSeletor{
	
	private Vacina vacina;
	private Fabricante fabricante;
	private Unidade unidade;
	private Endereco endereco;
	private Contato contato;
	
	private VacinaSeletor() {
		super();
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

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}
	
	/*
	public boolean temFiltro() {
		return (this.nomeVacina != null && this.nomeVacina.trim().length() > 0)
					|| (this.nomeUnidade != null && this.nomeUnidade.trim().length()>0)
					|| (this.endereco != null && this.endereco.trim().length()>0)
					|| (this.nomeFabricante != null && this.nomeFabricante.trim().length()>0)
					|| (this.categoriaVacina != null && this.categoriaVacina.trim().length()>0)
					|| (this.idadeMinimaVacina != 0)
					|| (this.idadeMaximaVacina != 0)
					||  (this.contraIndicacao);
	}
*/
}
