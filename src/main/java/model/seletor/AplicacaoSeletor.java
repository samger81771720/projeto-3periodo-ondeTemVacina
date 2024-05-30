package model.seletor;

import java.time.LocalDate;

import model.entity.Aplicacao;

public class AplicacaoSeletor extends BaseSeletor{
	
	private Aplicacao aplicacao;
	private LocalDate dataInicioPesquisaSeletor;
	private LocalDate dataFinalPesquisaSeletor;
	
	public AplicacaoSeletor() {
		super();
	}
		
	public Aplicacao getAplicacao() {
		return aplicacao;
	}

	public void setAplicacao(Aplicacao aplicacao) {
		this.aplicacao = aplicacao;
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

	public boolean temFiltro() {
		return 
						(this.dataInicioPesquisaSeletor !=null)
					||	(this.dataFinalPesquisaSeletor !=null)
					|| (this.aplicacao.getVacinaAplicada() != null 
						 && this.aplicacao.getVacinaAplicada().getNome() != null 
						 && this.aplicacao.getVacinaAplicada().getNome().isBlank() 
						 && this.aplicacao.getVacinaAplicada().getNome().isEmpty())
					|| (this.aplicacao.getUnidadeOndeAplicou() != null 
						 && this.aplicacao.getUnidadeOndeAplicou().getNome() != null 
						 && this.aplicacao.getUnidadeOndeAplicou().getNome().isEmpty()
						 && this.aplicacao.getUnidadeOndeAplicou().getNome().isBlank());
	}
	
}
