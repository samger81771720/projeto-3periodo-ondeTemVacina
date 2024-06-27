package model.service;
import java.util.UUID;
import exception.ControleVacinasException;
import model.dto.UsuarioDTO;
import model.entity.Pessoa;
import model.repository.PessoaRepository;

public class LoginService {
	
	private PessoaRepository pessoaRepository = new PessoaRepository();
	
	public Pessoa autenticar(UsuarioDTO usuarioTentandoAutenticar) throws ControleVacinasException {
		if(
			usuarioTentandoAutenticar == null || 
			usuarioTentandoAutenticar.getLogin() == null || 
			usuarioTentandoAutenticar.getLogin().replace(" ", "").length() == 0
			) {
			throw new ControleVacinasException("Usuário não informado");
		}
		if(
			usuarioTentandoAutenticar.getSenha() == null || 
			usuarioTentandoAutenticar.getSenha().replace(" ", "").length() == 0) {
			throw new ControleVacinasException("Senha não informada");
		}
		Pessoa pessoaAutenticada = pessoaRepository.efetuarLogin(usuarioTentandoAutenticar);
		if(pessoaAutenticada == null) {
			throw new ControleVacinasException("Login ou senha inválidos, tente novamente");
		}
		String idSessao = UUID.randomUUID().toString();
		pessoaAutenticada.setIdSessao(idSessao);
		pessoaRepository.alterarIdSessao(pessoaAutenticada);
		return pessoaAutenticada;
	}
	
	public boolean chaveValida(String idSessao) {
		Pessoa pessoa = this.pessoaRepository.consultarPorIdSessao(idSessao);
		return pessoa != null && pessoa.getIdSessao() != null && pessoa.getIdSessao().equals(idSessao);
	}

}
