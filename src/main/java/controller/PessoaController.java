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
import model.seletor.PessoaSeletor;
import model.seletor.VacinaSeletor;
import model.service.PessoaService;

@Path("/pessoa")
public class PessoaController {
	
	private PessoaService service = new PessoaService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Pessoa salvar(Pessoa novaPessoa) throws ControleVacinasException {
		return service.salvar(novaPessoa);
	}
	
	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id") int id) throws ControleVacinasException{
		 return service.excluir(id);
	}
	
	@GET
	@Path("/{id}")
	public Pessoa consultarPorId(@PathParam("id")int id)throws ControleVacinasException{
		return service.consultarPorId(id);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean alterar(Pessoa pessoaParaAlterar) throws ControleVacinasException{
		return service.alterar(pessoaParaAlterar);
	}
	
	@GET
	@Path("/consultarPessoas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pessoa> consultarTodas(){
		 return service.consultarTodos();
	}
	
	@GET
	@Path("/pesquisadores")
	public  ArrayList<Pessoa> consultarPesquisadores(){
		 return service.consultarPesquisadores();
	}
	
	@POST
	@Path("/filtro")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Pessoa> consultarComFiltros(PessoaSeletor seletor){
		return service.consultarComFiltros(seletor);
	}
	
	@POST
	@Path("/contabilizar-total-registros")
	@Consumes(MediaType.APPLICATION_JSON)
	public int contarTotalRegistros(PessoaSeletor seletor) {
		return this.service.contarTotalRegistros(seletor);
	}
	
	@POST
	@Path("/contabilizar-total-paginas")
	@Consumes(MediaType.APPLICATION_JSON)
	public int contarPaginas(PessoaSeletor seletor) {
		return this.service.contarPaginas(seletor);
	}
	
}
*/