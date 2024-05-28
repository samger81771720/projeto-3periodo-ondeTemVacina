package model.seletor;

import model.entity.Unidade;
import model.entity.Vacina;

public class VacinaSeletor extends BaseSeletor{
	
	private Vacina vacina;
	private String fabricante;
	private Unidade unidade;
	private String bairro;
	private String cep;
	
	public VacinaSeletor() {
		super();
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public boolean temFiltro() {
		return (
						this.vacina != null 
						&& this.vacina.getNome() != null  
				        && !this.vacina.getNome().isBlank() 
				        && !this.vacina.getNome().isEmpty()
				        && this.vacina.getCategoria() != null  
				        && !this.vacina.getCategoria().isBlank() 
				        && !this.vacina.getCategoria().isEmpty()
				        && this.vacina.getIdadeMinima() != 0  
				        && this.vacina.getIdadeMaxima() != 0
				        && !this.vacina.isContraIndicacao()
				        && this.vacina.isContraIndicacao()
				       )
					||(
						this.fabricante != null 
						&& this.fabricante.isBlank() 
						&& this.fabricante.isEmpty()
						)
					||(
						this.unidade != null
						&& this.unidade.getNome() != null
						&& this.unidade.getNome().isBlank()
						&& this.unidade.getNome().isEmpty()
						)
					||(
						this.bairro != null
						&& this.bairro.isEmpty()
						&& this.bairro.isBlank()
						)
					||(
						this.cep != null
						&& this.cep.isEmpty()
						&& this.cep.isBlank()
						);
	}

}
