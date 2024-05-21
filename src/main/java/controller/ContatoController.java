package controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import model.entity.Contato;
import model.service.ContatoService;

@Path("/contato")
public class ContatoController {
	
	ContatoService contatoService = new ContatoService();
	
	@GET
	@Path("/{id}")
	public Contato consultarPorId(@PathParam("id")int id){
		return contatoService.consultarPorId(id);
	}
	
}
