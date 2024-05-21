package model.seletor;

import java.time.LocalDate;
import model.entity.Vacina;

public class AplicacaoSeletor extends BaseSeletor{
	
	private String vacinaUsadaNaAplicacao;
	private LocalDate dataInicialDaAplicacaoDaVacina;
	private LocalDate dataFinalDaAplicacaoDaVacina;
	private int avaliacaoReacaoVacina;
	private String pessoaQueRecebeu;
	
	public String getVacinaUsadaNaAplicacao() {
		return vacinaUsadaNaAplicacao;
	}

	public void setVacinaUsadaNaAplicacao(String vacinaUsadaNaAplicacao) {
		this.vacinaUsadaNaAplicacao = vacinaUsadaNaAplicacao;
	}

	public LocalDate getDataInicialDaAplicacaoDaVacina() {
		return dataInicialDaAplicacaoDaVacina;
	}


	public void setDataInicialDaAplicacaoDaVacina(LocalDate dataInicialDaAplicacaoDaVacina) {
		this.dataInicialDaAplicacaoDaVacina = dataInicialDaAplicacaoDaVacina;
	}


	public LocalDate getDataFinalDaAplicacaoDaVacina() {
		return dataFinalDaAplicacaoDaVacina;
	}


	public void setDataFinalDaAplicacaoDaVacina(LocalDate dataFinalDaAplicacaoDaVacina) {
		this.dataFinalDaAplicacaoDaVacina = dataFinalDaAplicacaoDaVacina;
	}


	public int getAvaliacaoReacaoVacina() {
		return avaliacaoReacaoVacina;
	}


	public void setAvaliacaoReacaoVacina(int avaliacaoReacaoVacina) {
		this.avaliacaoReacaoVacina = avaliacaoReacaoVacina;
	}


	public String getPessoaQueRecebeu() {
		return pessoaQueRecebeu;
	}


	public void setPessoaQueRecebeu(String pessoaQueRecebeu) {
		this.pessoaQueRecebeu = pessoaQueRecebeu;
	}


	public boolean temFiltro() {
		return (this.vacinaUsadaNaAplicacao != null)
					|| (this.getDataInicialDaAplicacaoDaVacina() != null)
					|| (this.getDataFinalDaAplicacaoDaVacina() != null)
					|| (this.avaliacaoReacaoVacina != 0)
					|| (this.pessoaQueRecebeu != null);
	}
	
}

