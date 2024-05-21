package model.entity;

import java.time.LocalDate;

public class Aplicacao {
	
	private int idAplicacao;
	private int idPessoa;
	private Vacina vacinaAplicada;
	private LocalDate dataAplicacao;
	private int avaliacaoReacao;

	public int getIdAplicacao() {
		return idAplicacao;
	}
	
	public void setIdAplicacao(int idAplicacao) {
		this.idAplicacao = idAplicacao;
	}
	public int getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(int idPessoa) {
		this.idPessoa = idPessoa;
	}
	public LocalDate getDataAplicacao() {
		return dataAplicacao;
	}
	public void setDataAplicacao(LocalDate dataAplicacao) {
		this.dataAplicacao = dataAplicacao;
	}
	public Vacina getVacinaAplicada() {
		return vacinaAplicada;
	}
	public void setVacinaAplicada(Vacina vacinaAplicada) {
		this.vacinaAplicada = vacinaAplicada;
	}
	public int getAvaliacaoReacao() {
		return avaliacaoReacao;
	}
	public void setAvaliacaoReacao(int avaliacaoReacao) {
		this.avaliacaoReacao = avaliacaoReacao;
	}
	
}
