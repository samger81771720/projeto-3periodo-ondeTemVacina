package controller;

import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.Vacina;
import model.seletor.VacinaSeletor;
import model.service.VacinaService;

@Path("/vacina")
public class VacinaController {
	
	VacinaService vacinaService = new VacinaService();
	
	@GET
	@Path("/{id}")
	public Vacina consultarPorId(@PathParam("id")int id) {
		return vacinaService.consultarPorId(id);
	}
	
	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id")int id) {
		return vacinaService.excluir(id);
	}
	
	@POST
	@Path("/filtroVacinasUnidade")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<VacinaSeletor> consultarComFitros(VacinaSeletor seletor){
		return vacinaService.consultarComFiltros(seletor);
	}
	
}


