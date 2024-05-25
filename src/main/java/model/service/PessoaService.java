package model.service;

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
	                throw new ControleVacinasException("Essa pessoa não se encontra cadastrada no sistema.");
	        }
	    } else {
	        throw new ControleVacinasException("Verifique os dados de acesso e tente novamente.");
	    }
	}
	
	public Pessoa salvar(Pessoa novaPessoa) throws ControleVacinasException{
		//validarCamposPreenchidosDePessoa(novaPessoa);
			verificarCpfParaCadastrar(novaPessoa);
			verificarDisponibilidadeLogin(novaPessoa);
			verificarDisponibilidadeSenha(novaPessoa);
		return pessoaRepository.salvar(novaPessoa);
	}
	
	public boolean alterar(Pessoa pessoaAtualizada) {
		return pessoaRepository.alterar(pessoaAtualizada);
	}
	
	private void verificarCpfParaCadastrar(Pessoa novaPessoa) throws ControleVacinasException{
	       if(pessoaRepository.verificarCpfParaCadastrar(novaPessoa)!=false) {
	        	throw new ControleVacinasException("O cpf informado já se encontra cadastrado no sistema.");
	        } 
	}
	
	private void validarCamposPreenchidosParalogin(Pessoa novaPessoa) throws ControleVacinasException {
		   
		String mensagemValidacao = "";
			
	    if (
	    		novaPessoa.getLogin() == null || novaPessoa.getSenha() == null
	    		|| novaPessoa.getLogin().isEmpty() || novaPessoa.getSenha().isEmpty()
	    		|| novaPessoa.getLogin().isBlank() || novaPessoa.getSenha().isBlank()
	    		) {
	        mensagemValidacao += " - O campo login e/ou o campo senha precisam ser preenchidos e não podem haver espaços em branco no preenchimento dos campos. ";
	    }
	    if(novaPessoa.getLogin().length()<8) {
	    	mensagemValidacao += " - O campo login precisa ter ao menos oito caracteres e sem espaços. ";
	    }
	    if(novaPessoa.getSenha().length()<8) {
	    	mensagemValidacao += " - O campo senha precisa ter ao menos oito caracteres e sem espaços. ";
	    }
	    if(!mensagemValidacao.isEmpty()) {
			throw new ControleVacinasException("As observações a seguir precisam ser atendidas para efetuar o login com sucesso: " + mensagemValidacao);
		}
	    
	}
	
	private void verificarDisponibilidadeLogin(Pessoa pessoaLoginDesejado) throws ControleVacinasException{
		if(pessoaRepository.verificarDisponibilidadeLogin(pessoaLoginDesejado)) {
			throw new ControleVacinasException("Login já existente no sistema. Tente outro.");
		}
	}
	
	private void verificarDisponibilidadeSenha(Pessoa pessoaSenhaDesejada) throws ControleVacinasException{
		if(pessoaRepository.verificarDisponibilidadeSenha(pessoaSenhaDesejada)) {
			throw new ControleVacinasException("Senha já existente no sistema. Tente outra.");
		}
	}
		
}
