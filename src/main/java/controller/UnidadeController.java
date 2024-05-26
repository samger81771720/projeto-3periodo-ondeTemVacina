package controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import model.entity.Unidade;
import model.service.UnidadeService;

@Path("/unidade")
public class UnidadeController {
	
	UnidadeService unidadeService = new UnidadeService();
	
	@GET
	@Path("/{id}")
	public Unidade consultarPorId(@PathParam("id")int id){
		return unidadeService.consultarPorId(id);
	}

}
