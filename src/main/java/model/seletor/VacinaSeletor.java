package model.seletor;

import model.entity.Endereco;
import model.entity.Fabricante;
import model.entity.Unidade;
import model.entity.Vacina;

public class VacinaSeletor extends BaseSeletor{
	
	private Vacina vacina;
	private Unidade unidade;
	private Fabricante fabricanteDaVacina;
	
	private Endereco endereco;
	
	/*
	private String bairro;
	private String cep;
	*/
	public VacinaSeletor() {
		super();
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
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

	/*
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
	*/
	public Fabricante getFabricanteDaVacina() {
		return fabricanteDaVacina;
	}

	public void setFabricanteDaVacina(Fabricante fabricanteDaVacina) {
		this.fabricanteDaVacina = fabricanteDaVacina;
	}
	
	public boolean temFiltro() {
		
		return 
						(this.vacina != null && this.vacina.getNome() != null && !this.vacina.getNome().isBlank() && !this.vacina.getNome().isEmpty())    
									
						|| (this.vacina.getCategoria() != null 	&& !this.vacina.getCategoria().isBlank()	&& !this.vacina.getCategoria().isEmpty())
						
						|| (this.vacina.getIdadeMinima() != 0)  
						        
			         	|| (this.vacina.getIdadeMaxima() != 0)
						        
					  	|| (this.vacina.isContraIndicacao())
						
						|| (this.fabricanteDaVacina != null && !this.fabricanteDaVacina.getNome().isBlank() && !this.fabricanteDaVacina.getNome().isEmpty())
							
						|| (this.unidade != null && this.unidade.getNome() != null && !this.unidade.getNome().isBlank() && !this.unidade.getNome().isEmpty())
							
						|| (this.endereco != null && this.endereco.getBairro() != null && !this.endereco.getBairro().isEmpty() && !this.endereco.getBairro().isBlank())
						
						|| (this.endereco != null && this.endereco.getCep() != null && !this.endereco.getCep().isEmpty() && !this.endereco.getCep().isBlank());
	}

}
