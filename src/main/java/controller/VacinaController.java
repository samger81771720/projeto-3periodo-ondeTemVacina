package controller;

import java.util.List;

import exception.ControleVacinasException;
import filter.AuthFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import model.entity.Pessoa;
import model.entity.Vacina;
import model.repository.PessoaRepository;
import model.service.VacinaService;

@Path("/restrito/vacina")
public class VacinaController {
	
	@Context
	private HttpServletRequest request;
	
	VacinaService vacinaService = new VacinaService();
	PessoaRepository pessoaRepository = new PessoaRepository();
	
	@GET
	@Path("/{id}")
	public Vacina consultarPorId(@PathParam("id")int id) {
		return vacinaService.consultarPorId(id);
	}
	
	@GET
	@Path("/consultarTodasVacinas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Vacina> consultarTodos(){
		 return vacinaService.consultarTodos();
	}
	
	@GET
	@Path("/consultarTodasCategorias")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> consultarTodasCategorias(){
		 return vacinaService.consultarTodasCategorias();
	}
	
	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id")int id) throws ControleVacinasException{
		if(!this.validarAcaoDeAdministrador()) {
			throw new ControleVacinasException("O usuário logado não tem permissão para excluir uma vacina no banco de dados.");
		}
		return vacinaService.excluir(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vacina salvar(Vacina novaVacina) throws ControleVacinasException{
		if(!this.validarAcaoDeAdministrador()) {
			throw new ControleVacinasException("O usuário logado não tem permissão para salvar um novo registro  de vacina no banco de dados.");
		}
		return vacinaService.salvar(novaVacina);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean alterar(Vacina vacinaParaAlterar) throws ControleVacinasException{
		if(!this.validarAcaoDeAdministrador()) {
			throw new ControleVacinasException("O usuário logado não tem permissão para alterar um registro de vacina no banco de dados.");
		}
		return vacinaService.alterar(vacinaParaAlterar);
	}
	
	public boolean validarAcaoDeAdministrador(){
		boolean isAdministrator = true;                                            
		String idSessaoNoHeader = request.getHeader(AuthFilter.CHAVE_ID_SESSAO); 
		Pessoa pessoaAutenticada = this.pessoaRepository.consultarPorIdSessao(idSessaoNoHeader);  
		if(pessoaAutenticada.getTipo() == Pessoa.USUARIO) {
			isAdministrator = false;
		}
		return isAdministrator;
	}
	
}


