
package controller;

import java.util.List;

import exception.ControleVacinasException;
import filter.AuthFilter;
import jakarta.servlet.http.HttpServlet;
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
import model.repository.PessoaRepository;
import model.seletor.PessoaSeletor;
import model.service.PessoaService;

@Path("/pessoa/restrito")
public class PessoaController {
	
	@Context
	private HttpServletRequest request;
	
	PessoaService pessoaService = new PessoaService();
	PessoaRepository pessoaRepository = new PessoaRepository();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Pessoa salvar(Pessoa novaPessoa) throws ControleVacinasException{
		return pessoaService.salvar(novaPessoa);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean alterar(Pessoa pessoaAtualizada)throws ControleVacinasException {
		return pessoaService.alterar(pessoaAtualizada);
	}
	
	@GET
	@Path("/{id}")
	public Pessoa consultarPorId(@PathParam("id")int id){
		return pessoaService.consultarPorId(id);
	}
	
	@GET
	@Path("/consultarTodasPessoas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pessoa> consultarTodos() throws ControleVacinasException{
		validarTipoDeUsuario();
		 return pessoaService.consultarTodos();
	}
	
	@POST
	@Path("/filtroConsultarPessoas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pessoa> consultarPessoasComFiltro(PessoaSeletor seletor) throws ControleVacinasException{
		return pessoaService.consultarPessoasComFiltro(seletor);
	}
	
	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id")int id) {
		return pessoaService.excluir(id);
	}
	
	public boolean validarTipoDeUsuario() throws ControleVacinasException{
		boolean isAdministrator = true;
		String idSessaoNoHeader = request.getHeader(AuthFilter.CHAVE_ID_SESSAO);
		Pessoa pessoaAutenticada = this.pessoaRepository.consultarPorIdSessao(idSessaoNoHeader);
		if(pessoaAutenticada.getTipo() != Pessoa.ADMINISTRADOR) {
			throw new ControleVacinasException("Usuário sem permissão acessar a lista de todas as pessoas.");
		}
		return isAdministrator;
	}
	
}


/*
 A anotação @Context em Java/Jakarta EE é usada 
 para injetar objetos contextuais específicos no código, 
 como HttpServletRequest, HttpServletResponse, 
 ServletContext, entre outros. Esses objetos são 
 providos pelo ambiente de execução (por exemplo, 
 um contêiner de servlets) e permitem interação 
 com o ambiente de execução sem necessidade 
 de inicialização explícita.
  
  */

/*

A classe HttpServletRequest representa uma requisição HTTP 
recebida pelo servidor do back end. Ela é utilizada pelo servidor(no caso dessa aplicação, pelo tomcat) 
para processar as solicitações feitas pelos clientes (como 
navegadores web, aplicativos móveis, entre outros) e responder 
de acordo com a lógica da aplicação back end. 
  */
