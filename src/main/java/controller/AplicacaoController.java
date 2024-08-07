package controller;

import java.util.List;
import exception.ControleVacinasException;
import filter.AuthFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import model.dto.AplicacaoDTO;
import model.entity.Aplicacao;
import model.entity.Pessoa;
import model.repository.PessoaRepository;
import model.seletor.AplicacaoSeletor;
import model.service.AplicacaoService;

@Path("/restrito/aplicacao")
public class AplicacaoController {
	
	@Context
	private HttpServletRequest request;
	
	AplicacaoService aplicacaoService = new AplicacaoService();
	PessoaRepository pessoaRepository = new PessoaRepository();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Aplicacao salvar(Aplicacao aplicacao) throws ControleVacinasException{
		if(!validarAcaoDeAdministrador()) {
			throw new ControleVacinasException("O usu�rio logado n�o tem permiss�o para salvar uma aplica��o.");
		}
		return aplicacaoService.salvar(aplicacao);
	}
	
	@GET
	@Path("/{id}")
	public Aplicacao consultarPorId(@PathParam("id")int id){
		return aplicacaoService.consultarPorId(id);
	}
	
	@POST
	@Path("/filtroAplicacoesPessoa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<AplicacaoDTO> consultarComFiltros(AplicacaoSeletor seletor){
		return aplicacaoService.consultarComFiltros(seletor);
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
