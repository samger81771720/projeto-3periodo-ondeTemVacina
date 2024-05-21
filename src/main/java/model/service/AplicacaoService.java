package model.service;

import java.time.LocalDate;
import java.util.ArrayList;
import exception.ControleVacinasException;
import model.entity.Aplicacao;
import model.repository.AplicacaoRepository;
import model.repository.PessoaRepository;
import model.repository.VacinaRepository;

public class AplicacaoService {
	
	// NO MEU BANCO DE DADOS O PESQUISADOR = 1, VOLUNTÁRIO = 2 e PUBLICO GERAL = 3
	private static final int PESQUISADOR = 1;
	private static final int PESQUISADOR_OU_VOLUNTARIO = 2; 
	private static final int INICIAL = 1;
	private static final int TESTES = 2;
	private static final int NOTA_MAXIMA = 5;
	
	private PessoaRepository pessoaRepository = new PessoaRepository();
	
	private VacinaRepository vacinaRepository = new VacinaRepository();
	
	private AplicacaoRepository repository = new AplicacaoRepository();
	
	public Aplicacao salvar(Aplicacao novoRegistroDaAplicacaoDaVacina) throws ControleVacinasException{
		validarCamposPreenchidosDeAplicacao(novoRegistroDaAplicacaoDaVacina);
		validarPermissaoParaVacinar(novoRegistroDaAplicacaoDaVacina);
		return repository.salvar(novoRegistroDaAplicacaoDaVacina);
	}

	public boolean alterar(Aplicacao registroDaAplicacaoDaVacinaAlterado) throws ControleVacinasException{
		validarCampoIdAplicacao(registroDaAplicacaoDaVacinaAlterado);
		validarCamposPreenchidosDeAplicacao(registroDaAplicacaoDaVacinaAlterado);
		return repository.alterar(registroDaAplicacaoDaVacinaAlterado);
	}
	
	private void validarCamposPreenchidosDeAplicacao(Aplicacao novoRegistroDaAplicacaoDaVacina) throws ControleVacinasException{
		String mensagem = "";
		if(novoRegistroDaAplicacaoDaVacina.getIdPessoa()==0) {
			mensagem += "O campo \"id\" da pessoa vacinada é obrigatório.";
		}
		if(novoRegistroDaAplicacaoDaVacina.getVacinaAplicada().getIdVacina()==0 || novoRegistroDaAplicacaoDaVacina.getVacinaAplicada()==null) {
			mensagem += "O campo \"id\" da vacina aplicada é obrigatório.";
		}
		novoRegistroDaAplicacaoDaVacina.setDataAplicacao(LocalDate.now());
		if(novoRegistroDaAplicacaoDaVacina.getAvaliacaoReacao() == 0) {
			novoRegistroDaAplicacaoDaVacina.setAvaliacaoReacao(NOTA_MAXIMA);
		}
		if(!mensagem.isEmpty()) {
			throw new ControleVacinasException("A(s) observaçõe(s) listada(s) precisa(m) ser atendida)s) "
					+ "para que o cadastro do registro da aplicação da vacina seja efetuado com sucesso. Segue: \n"+mensagem);
		}
	}
	
	private void validarCampoIdAplicacao(Aplicacao registroDaAplicacaoDaVacinaAlterado) throws ControleVacinasException{
		if(registroDaAplicacaoDaVacinaAlterado.getIdAplicacao()==0) {
			throw new ControleVacinasException("O campo \"id\" da aplicação da vacina é obrigatório.");
		}
	}
	
	public Aplicacao consultarPorId(int id) {
		return repository.consultarPorId(id);
	}
	
	public boolean excluir(int id) {
		return repository.excluir(id);
	}
	
	public ArrayList<Aplicacao> consultarTodos(){
		return repository.consultarTodos();
	}
	
	public ArrayList<Aplicacao> consultarTodasAplicacoesDaPessoa(int id) {
		return repository.consultarTodasAplicacoesDaPessoa(id);
	}
	
	//- Ao aplicar uma dose de vacina (inserir/atualizar vacinação), o sistema deve verificar se a pessoa pode
		// receber a vacina em questão, consoante a seguinte regra 
		private void validarPermissaoParaVacinar(Aplicacao novoRegistroDaAplicacaoDaVacina) throws ControleVacinasException{
			if(vacinaRepository.consultarPorId(novoRegistroDaAplicacaoDaVacina.getVacinaAplicada().getIdVacina()).getEstagioDaVacina() ==INICIAL 
					&& 	pessoaRepository.consultarPorId(novoRegistroDaAplicacaoDaVacina.getIdPessoa()).getTipo()!=PESQUISADOR ){ 
				throw new ControleVacinasException("No estágio inicial da vacina, apenas os pesquisadores podem ser vacinados.");
			}
			if(vacinaRepository.consultarPorId(novoRegistroDaAplicacaoDaVacina.getVacinaAplicada().getIdVacina()).getEstagioDaVacina()==TESTES 
					&&pessoaRepository.consultarPorId(novoRegistroDaAplicacaoDaVacina.getIdPessoa()).getIdPessoa()!=PESQUISADOR 
					&& pessoaRepository.consultarPorId(novoRegistroDaAplicacaoDaVacina.getIdPessoa()).getTipo()!=PESQUISADOR_OU_VOLUNTARIO ) {
				throw new ControleVacinasException("No estágio de testes da vacina, apenas os pesquisadores ou voluntários podem ser vacinados.");
			}
		}
}
