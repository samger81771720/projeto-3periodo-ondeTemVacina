package model.seletor;

import java.time.LocalDate;

import model.entity.Aplicacao;
import model.entity.Fabricante;
import model.entity.Vacina;

public class AplicacaoSeletor extends BaseSeletor{
	
	private int idPessoaRecebeuAplicacao;
	private String nomeUnidadeAplicacao;
	private String nomeVacinaAplicada;
	private LocalDate dataInicioPesquisaSeletor;
	private LocalDate dataFinalPesquisaSeletor;
	private Aplicacao aplicacao;
	/*
	  Como o objeto Aplicacao nem sempre retorna o objeto Fabricante, 
	 foi necess�rio introduzir essa vari�vel "fabricanteDaVacinaAplicada"
    */ 
	private String fabricanteDaVacinaAplicada;
	
	
	public AplicacaoSeletor() {
		super();
	}
		
	public int getIdPessoaRecebeuAplicacao() {
	return idPessoaRecebeuAplicacao;
	}

	public void setIdPessoaRecebeuAplicacao(int idPessoaRecebeuAplicacao) {
		this.idPessoaRecebeuAplicacao = idPessoaRecebeuAplicacao;
	}

	public String getNomeUnidadeAplicacao() {
		return nomeUnidadeAplicacao;
	}

	public void setNomeUnidadeAplicacao(String nomeUnidadeAplicacao) {
		this.nomeUnidadeAplicacao = nomeUnidadeAplicacao;
	}

	public String getNomeVacinaAplicada() {
		return nomeVacinaAplicada;
	}

	public void setNomeVacinaAplicada(String nomeVacinaAplicada) {
		this.nomeVacinaAplicada = nomeVacinaAplicada;
	}

	public LocalDate getDataInicioPesquisaSeletor() {
		return dataInicioPesquisaSeletor;
	}

	public void setDataInicioPesquisaSeletor(LocalDate dataInicioPesquisaSeletor) {
		this.dataInicioPesquisaSeletor = dataInicioPesquisaSeletor;
	}

	public LocalDate getDataFinalPesquisaSeletor() {
		return dataFinalPesquisaSeletor;
	}

	public void setDataFinalPesquisaSeletor(LocalDate dataFinalPesquisaSeletor) {
		this.dataFinalPesquisaSeletor = dataFinalPesquisaSeletor;
	}

	public Aplicacao getAplicacao() {
		return aplicacao;
	}

	public void setAplicacao(Aplicacao aplicacao) {
		this.aplicacao = aplicacao;
	}
	
	public String getFabricanteDaVacinaAplicada() {
		return fabricanteDaVacinaAplicada;
	}

	public void setFabricanteDaVacinaAplicada(String fabricanteDaVacinaAplicada) {
		this.fabricanteDaVacinaAplicada = fabricanteDaVacinaAplicada;
	}

	public boolean temFiltro() {
		return 
						(this.dataInicioPesquisaSeletor !=null)
					
					||	(this.dataFinalPesquisaSeletor !=null)
					
					|| (this.nomeUnidadeAplicacao != null 
						 && this.nomeUnidadeAplicacao.trim().length() > 0) 
					
					|| (this.nomeVacinaAplicada != null 
						 && this.nomeVacinaAplicada.trim().length() > 0);
						 
	}
	
}
