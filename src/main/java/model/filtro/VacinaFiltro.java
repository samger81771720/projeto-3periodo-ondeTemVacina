package model.filtro;

import model.entity.Fabricante;
import model.entity.Unidade;
import model.entity.Vacina;

public class VacinaFiltro {
	
	private Vacina vacina;
	private Fabricante fabricante;
	private Unidade unidade;
	
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

}
