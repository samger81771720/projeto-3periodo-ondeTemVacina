package model.seletor;

import java.time.LocalDate;

public class PessoaSeletor extends BaseSeletor{

	private String nomePessoa; 
	private int tipoDaPessoa; 
	private String sexodaPessoa; 
	private String paisDaPessoa; 
	private LocalDate dataNascimentoInicialPesquisaSeletor;
	private LocalDate dataNascimentoFinalPesquisaSeletor;

	public PessoaSeletor() {
		//constructor
	}
	
	public String getNomePessoa() {
		return nomePessoa;
	}
	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}
	public int getTipoDaPessoa() {
		return tipoDaPessoa;
	}
	public void setTipoDaPessoa(int tipoDaPessoa) {
		this.tipoDaPessoa = tipoDaPessoa;
	}
	public String getSexodaPessoa() {
		return sexodaPessoa;
	}
	public void setSexodaPessoa(String sexodaPessoa) {
		this.sexodaPessoa = sexodaPessoa;
	}
	public String getPaisDaPessoa() {
		return paisDaPessoa;
	}
	public void setPaisDaPessoa(String paisDaPessoa) {
		this.paisDaPessoa = paisDaPessoa;
	}
	public LocalDate getDataNascimentoInicialPesquisaSeletor() {
		return dataNascimentoInicialPesquisaSeletor;
	}
	public void setDataNascimentoInicialPesquisaSeletor(LocalDate dataNascimentoInicialPesquisaSeletor) {
		this.dataNascimentoInicialPesquisaSeletor = dataNascimentoInicialPesquisaSeletor;
	}
	public LocalDate getDataNascimentoFinalPesquisaSeletor() {
		return dataNascimentoFinalPesquisaSeletor;
	}
	public void setDataNascimentoFinalPesquisaSeletor(LocalDate dataNascimentoFinalPesquisaSeletor) {
		this.dataNascimentoFinalPesquisaSeletor = dataNascimentoFinalPesquisaSeletor;
	}

	public boolean temFiltro() {
		return (this.paisDaPessoa != null && this.paisDaPessoa.trim().length() > 0)
					|| (this.nomePessoa != null && this.nomePessoa.trim().length() > 0)
					|| (this.sexodaPessoa != null && this.sexodaPessoa.trim().length() > 0)
					|| (this.tipoDaPessoa != 0)
					|| (this.dataNascimentoInicialPesquisaSeletor != null)
					|| (this.dataNascimentoFinalPesquisaSeletor != null);
	}
	
}
