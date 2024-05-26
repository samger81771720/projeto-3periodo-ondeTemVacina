package controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import model.entity.Fabricante;
import model.service.FabricanteService;

@Path("/fabricante")
public class FabricanteController {
	
	FabricanteService fabricanteService = new FabricanteService();
	
	@GET
	@Path("/{id}")
	public Fabricante consultarPorId(@PathParam("id")int id) {
		return fabricanteService.consultarPorId(id);
	}

}
