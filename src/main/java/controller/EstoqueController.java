package controller;

import java.util.ArrayList;
import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.dto.VacinaDTO;
import model.entity.Contato;
import model.entity.Endereco;
import model.entity.Estoque;
import model.entity.Unidade;
import model.seletor.VacinaSeletor;
import model.service.EstoqueService;

@Path("/estoque")
public class EstoqueController {
	
	EstoqueService estoqueService = new EstoqueService();
	
	@GET
	@Path("/consultarEstoquePorUnidade/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Estoque> consultarEstoquesDaUnidade(@PathParam("id")int idUnidade) {
		return estoqueService.consultarEstoquesDaUnidade(idUnidade);
	}
	
	@GET
	@Path("/consultarTodos")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Estoque> consultarTodos() {
		return estoqueService.consultarTodos();
	}
	
	@DELETE
	@Path("/{idUnidade}/{idVacina}")
	public boolean excluir(@PathParam("idUnidade") int idUnidade, @PathParam("idVacina") int idVacina) {
		return estoqueService.excluirEstoqueDaUnidade(idUnidade, idVacina);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean alterar(Estoque estoqueAnterior) {
		return estoqueService.alterar(estoqueAnterior);
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Estoque salvar(Estoque novoEstoque) {
		return estoqueService.salvar(novoEstoque);
	}
	
	@POST
	@Path("/filtro-Vacinas-EstoqueDaUnidade")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<VacinaDTO> consultarComFitros(VacinaSeletor seletor){
		return estoqueService.consultarComFiltros(seletor);
	}

}
