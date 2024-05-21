/*package controller;

import java.util.ArrayList;

import exception.ControleVacinasException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.Aplicacao;
import model.service.AplicacaoService;

@Path("/aplicacao")
public class AplicacaoController {

	private AplicacaoService service = new AplicacaoService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Aplicacao salvar(Aplicacao novoRegistroDaAplicacaoDaVacina) throws ControleVacinasException{
		return service.salvar(novoRegistroDaAplicacaoDaVacina);
	}
	
	@GET
	@Path("/{id}")
	public Aplicacao consultarPorId(@PathParam("id")int id) {
		return service.consultarPorId(id);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean alterar(Aplicacao registroDaAplicacaoDaVacinaAlterado) throws ControleVacinasException{
		return service.alterar(registroDaAplicacaoDaVacinaAlterado);
	}
	
	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id") int id) {
		return service.excluir(id);
	}
	
	@GET
	@Path("/consultarTodas")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Aplicacao> consultarTodos(){
		return service.consultarTodos();
	}
	
	@GET
	@Path("/consultarTodasAplicacoesDaPessoa/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Aplicacao> consultarTodasAplicacoesDaPessoa(@PathParam("id")int id) {
		return service.consultarTodasAplicacoesDaPessoa(id);
	}
	
}
*/