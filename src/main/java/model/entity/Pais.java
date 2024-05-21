package model.entity;

public class Pais {
	
	private int id_Pais;
	private String nome;
	private String sigla;
	
	public Pais(String nome, String sigla, Pessoa pessoa) {
		super();
		this.nome = nome;
		this.sigla = sigla;
	}
	
	public Pais() {
		super();
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public int getId_Pais() {
		return id_Pais;
	}

	public void setId_Pais(int id_Pais) {
		this.id_Pais = id_Pais;
	}

}
