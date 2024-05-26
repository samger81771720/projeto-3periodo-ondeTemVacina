package model.service;

import java.time.LocalDate;
import exception.ControleVacinasException;
import model.entity.Pessoa;
import model.repository.PessoaRepository;

public class PessoaService {
	
	PessoaRepository pessoaRepository = new PessoaRepository();
	
	public Pessoa efetuarLogin(Pessoa pessoa) throws ControleVacinasException {
		validarCamposPreenchidosParalogin(pessoa);
	   Pessoa pessoaLogada = pessoaRepository.efetuarLogin(pessoa);
	    if (pessoaLogada != null) {
	        switch (pessoaLogada.getTipo()) {
	            case Pessoa.ADMINISTRADOR:
	                return pessoaLogada;
	            case Pessoa.USUARIO:
	                return pessoaLogada;
	            default:
	                throw new ControleVacinasException("Essa pessoa n�o se encontra cadastrada no sistema.");
	        }
	    } else {
	        throw new ControleVacinasException("Verifique os dados de acesso e tente novamente.");
	    }
	}
	
	public Pessoa salvar(Pessoa novaPessoa) throws ControleVacinasException{
		 	validarCamposPreenchidosDePessoa(novaPessoa);
			verificarCpfParaCadastrar(novaPessoa);
			verificarDisponibilidadeLogin(novaPessoa);
			verificarDisponibilidadeSenha(novaPessoa);
		return pessoaRepository.salvar(novaPessoa);
	}
	
	public boolean alterar(Pessoa pessoaAtualizada) {
		return pessoaRepository.alterar(pessoaAtualizada);
	}
	
	private void validarCamposPreenchidosDePessoa(Pessoa novaPessoa) throws ControleVacinasException {
		   
		String mensagemValidacao = "";
		   
	    if (novaPessoa.getEnderecoDaPessoa().getCep() == null ||
	    	 novaPessoa.getEnderecoDaPessoa().getCep().isBlank() || 
	    	 novaPessoa.getEnderecoDaPessoa().getCep().isEmpty()) {
	         mensagemValidacao += " - O campo CEP, referente ao endere�o da pessoa, precisa ser preenchido. ";
	    }
	    if (novaPessoa.getContatoDaPessoa().getTelefone() == null ||
	    	 novaPessoa.getContatoDaPessoa().getTelefone().isBlank() || 
	    	 novaPessoa.getContatoDaPessoa().getTelefone().isEmpty()) {
	         mensagemValidacao += " - O campo telefone referente ao contato da pessoa precisa ser preenchido. ";
	    }
		if (novaPessoa.getNome() == null || novaPessoa.getNome().isEmpty() || novaPessoa.getNome().isBlank()) {
		    mensagemValidacao += " - O campo nome precisa ser preenchido. ";
		}
		if(novaPessoa.getNome().trim().length()<8) {
			mensagemValidacao += " - O campo nome precisa ter ao menos oito letras e os espa�os em branco n�o fazem parte da contagem. ";
		}
		if (!novaPessoa.getNome().matches("^[a-zA-Z ]+$")) {
		    mensagemValidacao += "O campo nome precisa ser preenchido apenas com letras. ";
		}
		LocalDate dataNascimento = novaPessoa.getDataNascimento();
		if (dataNascimento != null) {
		    LocalDate dataLimite = LocalDate.now().minusYears(120);
		    if (dataNascimento.isBefore(dataLimite)) {
		        mensagemValidacao += " - A pessoa n�o pode ter mais de 120 anos. ";
		    }
		} else {
		    mensagemValidacao += " - O campo data de nascimento precisa ser preenchido. ";
		}
		if (
				novaPessoa.getSexo().toUpperCase() == null ||
			    novaPessoa.getSexo().toUpperCase().isBlank() ||
			    novaPessoa.getSexo().toUpperCase().isEmpty()
			) {
		    mensagemValidacao += " - O campo sexo precisa ser informado.";
		}
	   if(
		   novaPessoa.getCpf() == null ||
		   novaPessoa.getCpf().isEmpty() ||
		   novaPessoa.getCpf().isBlank()
		   ) {
			mensagemValidacao += " - O campo cpf precisa ser preenchido. ";
		}
	   if(novaPessoa.getCpf().length() != 11) {
			mensagemValidacao += " - O campo cpf precisa ter 11 n�meros. ";
		}
		
	   if (!novaPessoa.getCpf().isEmpty() && !novaPessoa.getCpf().matches("[0-9]+")) {
		    mensagemValidacao += " - O campo CPF precisa ser preenchido apenas com n�meros. ";
		}
		if(
			novaPessoa.getLogin() == null ||
			novaPessoa.getLogin().isEmpty() ||
			novaPessoa.getLogin().isBlank()
			) {
			mensagemValidacao += " - O campo login precisa ser preenchido. ";
		}
		if (novaPessoa.getLogin().contains(" ") || novaPessoa.getLogin().length() < 8 || novaPessoa.getLogin().length() > 20) {
		    mensagemValidacao += " - O campo login precisa ser preenchido com no m�nimo oito e no m�ximo doze caracteres, sem espa�os.";
		}
		if ( novaPessoa.getSenha().contains(" ") ||  novaPessoa.getSenha().length() < 8 ||  novaPessoa.getSenha().length() > 20) {
		    mensagemValidacao += " - O campo senha precisa ser preenchido com no m�nimo oito e no m�ximo doze caracteres, sem espa�os.";
		}
		if(novaPessoa.getTipo() != Pessoa.ADMINISTRADOR  && novaPessoa.getTipo() != Pessoa.USUARIO) {
			mensagemValidacao += " - Os �nicos tipos permitidos para preencher esse campo s�o: USU�RIO ou ADMINISTRADOR.";
		}
		if(!mensagemValidacao.isEmpty()) {
			throw new ControleVacinasException("As observa��e(s) a seguir precisa(m) ser atendida(s): "+mensagemValidacao);
		}
	
	}
	
	private void verificarCpfParaCadastrar(Pessoa novaPessoa) throws ControleVacinasException{
	       if(pessoaRepository.verificarCpfParaCadastrar(novaPessoa)) {
	        	throw new ControleVacinasException("O cpf informado j� se encontra cadastrado no sistema.");
	        } 
	}
	
	private void validarCamposPreenchidosParalogin(Pessoa novaPessoa) throws ControleVacinasException {
		   
		String mensagemValidacao = "";
			
	    if (
	    		novaPessoa.getLogin() == null || novaPessoa.getSenha() == null
	    		|| novaPessoa.getLogin().isEmpty() || novaPessoa.getSenha().isEmpty()
	    		|| novaPessoa.getLogin().isBlank() || novaPessoa.getSenha().isBlank()
	    		) {
	        mensagemValidacao += " - O campo login e/ou o campo senha precisam ser preenchidos e n�o podem haver espa�os em branco no preenchimento dos campos. ";
	    }
	    if(novaPessoa.getLogin().contains(" ") || novaPessoa.getLogin().length()<8 || novaPessoa.getLogin().length() > 20) {
	    	mensagemValidacao += " - O campo login precisa ter no m�nimo oito e no m�ximo 12 caracteres e sem espa�os. ";
	    }
	    if(novaPessoa.getSenha().contains(" ") || novaPessoa.getSenha().length()<8 || novaPessoa.getSenha().length() > 20) {
	    	mensagemValidacao += " - O campo senha precisa ter no m�nimo oito e no m�ximo 12 caracteres e sem espa�os. ";
	    }
	    if(!mensagemValidacao.isEmpty()) {
			throw new ControleVacinasException("As observa��es a seguir precisam ser atendidas para efetuar o login com sucesso: " + mensagemValidacao);
		}
	    
	}
	
	private void verificarDisponibilidadeLogin(Pessoa pessoaLoginDesejado) throws ControleVacinasException{
		if(pessoaRepository.verificarDisponibilidadeLogin(pessoaLoginDesejado)) {
			throw new ControleVacinasException("Login j� existente no sistema. Tente outro.");
		}
	}
	
	private void verificarDisponibilidadeSenha(Pessoa pessoaSenhaDesejada) throws ControleVacinasException{
		if(pessoaRepository.verificarDisponibilidadeSenha(pessoaSenhaDesejada)) {
			throw new ControleVacinasException("Senha j� existente no sistema. Tente outra.");
		}
	}
		
}
