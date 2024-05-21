
package model.entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pessoa {
	
	private int idPessoa;
	private int tipo; 
	private String nome;
	private LocalDate dataNascimento; 
	private String sexo; 
	private String cpf;
	private Pais pais; 
	private ArrayList<Aplicacao>aplicacoesNaPessoa;
	
	public Pessoa() {
		super();
	}

	public int getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(int idPessoa) {
		this.idPessoa = idPessoa;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public ArrayList<Aplicacao> getAplicacoesNaPessoa() {
		return aplicacoesNaPessoa;
	}

	public void setAplicacoesNaPessoa(ArrayList<Aplicacao> aplicacoesNaPessoa) {
		this.aplicacoesNaPessoa = aplicacoesNaPessoa;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
}
