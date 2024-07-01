package controller;

import java.util.ArrayList;
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
import model.dto.VacinaDTO;
import model.entity.Contato;
import model.entity.Endereco;
import model.entity.Estoque;
import model.entity.Pessoa;
import model.entity.Unidade;
import model.repository.PessoaRepository;
import model.seletor.VacinaSeletor;
import model.service.EstoqueService;

@Path("/restrito/estoque")
public class EstoqueController {
	
	@Context
	private HttpServletRequest request;
	
	EstoqueService estoqueService = new EstoqueService();
	PessoaRepository pessoaRepository = new PessoaRepository();
	
	@GET
	@Path("/consultarTodos")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Estoque> consultarTodos() throws ControleVacinasException{
		String idSessaoNoHeader = request.getHeader(AuthFilter.CHAVE_ID_SESSAO);
		if(idSessaoNoHeader == null || idSessaoNoHeader.replace(" ", "").length() == 0) {
			throw new ControleVacinasException("Permissão negada. O idSessao não foi informado.");
		}
		Pessoa pessoaAutenticada = this.pessoaRepository.consultarPorIdSessao(idSessaoNoHeader);
		if(pessoaAutenticada == null) {
			throw new ControleVacinasException("Usuário não encontrado.");
		}
		if(pessoaAutenticada.getTipo() != Pessoa.ADMINISTRADOR) {
			throw new ControleVacinasException("Usuário sem permissão para cadastrar uma aplicação de vacina.");
		}
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
	public boolean alterar(Estoque estoque) throws ControleVacinasException{
		String idSessaoNoHeader = request.getHeader(AuthFilter.CHAVE_ID_SESSAO);
		if(idSessaoNoHeader == null || idSessaoNoHeader.replace(" ", "").length() == 0) {
			throw new ControleVacinasException("Permissão negada. O idSessao não foi informado.");
		}
		Pessoa pessoaAutenticada = this.pessoaRepository.consultarPorIdSessao(idSessaoNoHeader);
		if(pessoaAutenticada == null) {
			throw new ControleVacinasException("Usuário não encontrado.");
		}
		if(pessoaAutenticada.getTipo() != Pessoa.ADMINISTRADOR) {
			throw new ControleVacinasException("Usuário sem permissão para cadastrar uma aplicação de vacina.");
		}
		return estoqueService.alterar(estoque);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Estoque salvar(Estoque novoEstoque) throws ControleVacinasException{
		String idSessaoNoHeader = request.getHeader(AuthFilter.CHAVE_ID_SESSAO);
		if(idSessaoNoHeader == null || idSessaoNoHeader.replace(" ", "").length() == 0) {
			throw new ControleVacinasException("Permissão negada. O idSessao não foi informado.");
		}
		Pessoa pessoaAutenticada = this.pessoaRepository.consultarPorIdSessao(idSessaoNoHeader);
		if(pessoaAutenticada == null) {
			throw new ControleVacinasException("Usuário não encontrado.");
		}
		if(pessoaAutenticada.getTipo() != Pessoa.ADMINISTRADOR) {
			throw new ControleVacinasException("Usuário sem permissão para cadastrar uma aplicação de vacina.");
		}
		return estoqueService.salvar(novoEstoque);
	}
	
	@POST
	@Path("/filtro-Vacinas-EstoqueDaUnidade")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<VacinaDTO> consultarComFitros(VacinaSeletor seletor){
		return estoqueService.consultarComFiltros(seletor);
	}
	
	@GET
	@Path("/{idUnidade}/{idVacina}")
	public Estoque consultarPorIds(@PathParam("idUnidade") int idUnidade, @PathParam("idVacina") int idVacina){
		return estoqueService.consultarPorIds(idUnidade,idVacina);
	}
	
	@POST
	@Path("/consultarEstoquesDaUnidadePorId")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Estoque> consultarEstoquesDaUnidadePorId(Estoque estoqueDaUnidade){
		return estoqueService.consultarEstoquesDaUnidadePorId(estoqueDaUnidade);
	}

}
