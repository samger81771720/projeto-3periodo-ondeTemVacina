package controller;

import java.util.List;

import exception.ControleVacinasException;
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
import model.service.EnderecoService;

@Path("/restrito/endereco")
public class EnderecoControler {
	
	@Context
	private HttpServletRequest request;

	EnderecoService enderecoService = new EnderecoService();
	PessoaController pessoaController = new PessoaController();
	
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
		if(!pessoaController.validarTipoDeUsuario()) {
			throw new ControleVacinasException("O usu�rio logado n�o tem permiss�o para excluir um endere�o.");
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
	
}


