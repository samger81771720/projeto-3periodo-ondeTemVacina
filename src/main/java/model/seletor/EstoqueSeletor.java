package model.seletor;

public class EstoqueSeletor extends BaseSeletor {
	
	private String cidade;
	private String unidade;
	private String vacina;
	private String fabricante;
	private Boolean temEstoque;
	private Boolean temOrdenacaoPorUnidade;
	private Boolean temOrdenacaoPorCidade;
	private Boolean temOrdenacaoPorVacina;
	private Boolean temOrdenacaoPorFabricante;
	
	public EstoqueSeletor() {
		super();
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getVacina() {
		return vacina;
	}

	public void setVacina(String vacina) {
		this.vacina = vacina;
	}
		
	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	
	public Boolean getTemEstoque() {
		return temEstoque;
	}

	public void setTemEstoque(Boolean temEstoque) {
		this.temEstoque = temEstoque;
	}
	
	public Boolean getTemOrdenacaoPorUnidade() {
		return temOrdenacaoPorUnidade;
	}

	public void setTemOrdenacaoPorUnidade(Boolean temOrdenacaoPorUnidade) {
		this.temOrdenacaoPorUnidade = temOrdenacaoPorUnidade;
	}
	
	public Boolean getTemOrdenacaoPorCidade() {
		return temOrdenacaoPorCidade;
	}

	public void setTemOrdenacaoPorCidade(Boolean temOrdenacaoPorCidade) {
		this.temOrdenacaoPorCidade = temOrdenacaoPorCidade;
	}

	public Boolean getTemOrdenacaoPorVacina() {
		return temOrdenacaoPorVacina;
	}

	public void setTemOrdenacaoPorVacina(Boolean temOrdenacaoPorVacina) {
		this.temOrdenacaoPorVacina = temOrdenacaoPorVacina;
	}

	public Boolean getTemOrdenacaoPorFabricante() {
		return temOrdenacaoPorFabricante;
	}

	public void setTemOrdenacaoPorFabricante(Boolean temOrdenacaoPorFabricante) {
		this.temOrdenacaoPorFabricante = temOrdenacaoPorFabricante;
	}

	public boolean temFiltro() {
		return (this.cidade != null && this.cidade.trim().length() > 0)              
				   || (this.unidade != null && this.unidade.trim().length() > 0)          
				   || (this.vacina != null && this.vacina.trim().length() > 0)               
				   || (this.fabricante != null && this.fabricante.trim().length() > 0) 
			       || (this.temEstoque != null)
			       || (this.temOrdenacaoPorUnidade != null)
			       || (this.temOrdenacaoPorCidade != null)
			       || (this.temOrdenacaoPorFabricante != null)
			       || (this.temOrdenacaoPorVacina != null); 
	}
	
}
