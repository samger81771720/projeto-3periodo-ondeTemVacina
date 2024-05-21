/*
package controller;

import java.util.ArrayList;
import java.util.List;

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
import model.entity.Pessoa;
import model.entity.Vacina;
import model.seletor.VacinaSeletor;
import model.service.VacinaService;

@Path("/vacina")
public class VacinaController {
	
	private VacinaService vacinaService = new VacinaService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vacina salvar(Vacina novaVacina) throws ControleVacinasException{
		return vacinaService.salvar(novaVacina);
	}
	
	@GET
	@Path("/{id}")
	public Vacina consultarPorId(@PathParam("id")int id){
		return vacinaService.consultarPorId(id);
	}
	
	@GET
	@Path("/consultarVacinas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Vacina> consultarTodos(){
		 return vacinaService.consultarTodos();
	}
	
	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id") int id) throws ControleVacinasException{
		 return vacinaService.excluir(id);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean alterar(Vacina vacinaParaAlterar) {
		return vacinaService.alterar(vacinaParaAlterar);
	}
	
	@GET
	@Path("/consultarAplicacoesDaPessoa/{idPessoa}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Aplicacao> consultarTodasvaAplicacoesDaPessoa(@PathParam("idPessoa")int id){
		return service.consultarTodasAplicacoesDaPessoa(id);
	}
	
	@POST
	@Path("/filtro")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Vacina> consultarComFitros(VacinaSeletor seletor){
		return vacinaService.consultarComFiltros(seletor);
	}
	
	@POST
	@Path("/contabilizar-total-registros")
	@Consumes(MediaType.APPLICATION_JSON)
	public int contarTotalRegistros(VacinaSeletor seletor) {
		return vacinaService.contarTotalRegistros(seletor);
	}
	
	@POST
	@Path("/total-paginas")
	@Consumes(MediaType.APPLICATION_JSON)
	public int contarPaginas(VacinaSeletor seletor) {
		return this.vacinaService.contarPaginas(seletor);
	}
	
}
*/