package controller;

import java.util.List;

import exception.ControleVacinasException;
import filter.AuthFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import model.entity.Endereco;
import model.entity.Pessoa;
import model.repository.PessoaRepository;
import model.service.EnderecoService;

@Path("/restrito/endereco")
public class EnderecoControler {
	
	@Context
	private HttpServletRequest request;

	EnderecoService enderecoService = new EnderecoService();
	PessoaRepository pessoaRepository = new PessoaRepository();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Endereco salvar(Endereco endereco) {
		return enderecoService.salvar(endereco);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean alterar(Endereco endereco) {
		return enderecoService.alterar(endereco);
	}
	
	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id")int id) throws ControleVacinasException{
		if(!this.validarAcaoDeAdministrador()) {
			throw new ControleVacinasException("O usuário logado não tem permissão para excluir um endereço.");
		}
		return enderecoService.excluir(id);
	}
	
	@GET
	@Path("/{id}")
	public Endereco consultarPorId(@PathParam("id")int id){
		return enderecoService.consultarPorId(id);
	}
	
	@GET
	@Path("/consultarTodosEnderecos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Endereco> consultarTodos()throws ControleVacinasException{
		 return enderecoService.consultarTodos();
	}
	
	public boolean validarAcaoDeAdministrador(){
		boolean isAdministrator = true;                                               
		String idSessaoNoHeader = request.getHeader(AuthFilter.CHAVE_ID_SESSAO); 
		Pessoa pessoaAutenticada = this.pessoaRepository.consultarPorIdSessao(idSessaoNoHeader);  
		if(pessoaAutenticada.getTipo() == Pessoa.USUARIO) {
			isAdministrator = false;
		}
		return isAdministrator;
	}
	
}


