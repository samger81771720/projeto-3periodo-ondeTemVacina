package controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import model.entity.Endereco;
import model.entity.Estoque;
import model.entity.Unidade;
import model.service.UnidadeService;

@Path("/restrito/unidade")
public class UnidadeController {
	
	@Context
	private HttpServletRequest request;
	
	UnidadeService unidadeService = new UnidadeService();
	
	@GET
	@Path("/{id}")
	public Unidade consultarPorId(@PathParam("id")int id){
		return unidadeService.consultarPorId(id);
	}
	
	// método não está sendo usado
	@GET
	@Path("/consultarEstoquePorUnidade/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Estoque> consultarEstoquesDaUnidade(@PathParam("id")int idUnidade) {
		return unidadeService.consultarEstoquesDaUnidade(idUnidade);
	}
	
	@GET
	@Path("/consultarTodas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Unidade> consultarTodos(){
		 return unidadeService.consultarTodos();
	}

}
