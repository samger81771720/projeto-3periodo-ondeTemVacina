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
import model.entity.Vacina;
import model.service.VacinaService;

@Path("/restrito/vacina")
public class VacinaController {
	
	@Context
	private HttpServletRequest request;
	
	VacinaService vacinaService = new VacinaService();
	PessoaController pessoaController = new PessoaController();
	
	@GET
	@Path("/{id}")
	public Vacina consultarPorId(@PathParam("id")int id) {
		return vacinaService.consultarPorId(id);
	}
	
	@GET
	@Path("/consultarTodasVacinas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Vacina> consultarTodos(){
		 return vacinaService.consultarTodos();
	}
	
	@GET
	@Path("/consultarTodasCategorias")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> consultarTodasCategorias(){
		 return vacinaService.consultarTodasCategorias();
	}
	
	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id")int id) throws ControleVacinasException{
		if(!pessoaController.validarTipoDeUsuario()) {
			throw new ControleVacinasException("O usuário logado não tem permissão para excluir um registro de uma vacina do banco de dados.");
		}
		return vacinaService.excluir(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vacina salvar(Vacina novaVacina) throws ControleVacinasException{
		if(!pessoaController.validarTipoDeUsuario()) {
			throw new ControleVacinasException("O usuário logado não tem permissão para salvar um registro de uma vacina no banco de dados.");
		}
		return vacinaService.salvar(novaVacina);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean alterar(Vacina vacinaParaAlterar) throws ControleVacinasException{
		if(!pessoaController.validarTipoDeUsuario()) {
			throw new ControleVacinasException("O usuário logado não tem permissão para atualizar um registro de uma vacina no banco de dados.");
		}
		return vacinaService.alterar(vacinaParaAlterar);
	}
	
}


