package model.entity;

import java.time.LocalDate;

public class Vacina {
	
	private int idVacina;
	private String nome; 
	private Pessoa pesquisadorResponsavel;
	private  int estagioDaVacina;
	private LocalDate dataInicioPesquisa;
	private Pais pais;
	private double mediaDaVacina;
	
	public Vacina() {
		super();
	}

	public int getIdVacina() {
		return idVacina;
	}

	public void setIdVacina(int idVacina) {
		this.idVacina = idVacina;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Pessoa getPesquisadorResponsavel() {
		return pesquisadorResponsavel;
	}

	public void setPesquisadorResponsavel(Pessoa pesquisadorResponsavel) {
		this.pesquisadorResponsavel = pesquisadorResponsavel;
	}

	public int getEstagioDaVacina() {
		return estagioDaVacina;
	}

	public void setEstagioDaVacina(int estagioDaVacina) {
		this.estagioDaVacina = estagioDaVacina;
	}

	public LocalDate getDataInicioPesquisa() {
		return dataInicioPesquisa;
	}

	public void setDataInicioPesquisa(LocalDate dataInicioPesquisa) {
		this.dataInicioPesquisa = dataInicioPesquisa;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public double getMediaDaVacina() {
		return mediaDaVacina;
	}

	public void setMediaDaVacina(double mediaDaVacina) {
		this.mediaDaVacina = mediaDaVacina;
	}
}
