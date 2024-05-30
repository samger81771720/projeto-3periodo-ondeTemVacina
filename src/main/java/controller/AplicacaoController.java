package controller;

import exception.ControleVacinasException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.Aplicacao;
import model.service.AplicacaoService;

@Path("/aplicacao")
public class AplicacaoController {
	
	AplicacaoService aplicacaoService = new AplicacaoService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Aplicacao salvar(Aplicacao aplicacao) throws ControleVacinasException{
		return aplicacaoService.salvar(aplicacao);
	}
	
}
