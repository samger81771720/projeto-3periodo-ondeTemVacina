package controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import model.entity.Vacina;
import model.service.VacinaService;

@Path("/vacina")
public class VacinaController {
	
	VacinaService vacinaService = new VacinaService();
	
	@GET
	@Path("/{id}")
	public Vacina consultarPorId(@PathParam("id")int id) {
		return vacinaService.consultarPorId(id);
	}

}


