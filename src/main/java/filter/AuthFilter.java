package filter;

import java.io.IOException;
import java.util.List;

import exception.ExceptionHandler;
import exception.ControleVacinasException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import model.service.LoginService;

																			// ContainerRequestFilter - "Filtro de solicitação de contêiner"
@Provider
public class AuthFilter implements ContainerRequestFilter{
	
	private static final String BASE_URL_RESTRITA = "restrito";
	public static final String CHAVE_ID_SESSAO = "idSessao";
	
	private LoginService loginService = new LoginService();
	
	@Override
	public void filter(Conta) {
		
	}
	
}

/*
 
Recursos JAX-RS são classes Java que tratam requisições 
HTTP em aplicações RESTful. Cada recurso é mapeado para 
um caminho URI específico e contém métodos que respondem 
a diferentes tipos de requisições HTTP (GET, POST, etc.), 
processando a entrada e retornando respostas. RESTful se 
refere a serviços web que aderem aos princípios 
REST (Transferência Representacional do Estado), utilizando métodos 
HTTP para operar em recursos identificados por URIs, 
permitindo operações como criação, leitura, atualização 
e exclusão de dados de forma stateless (sem estado). 
A interface ContainerRequestFilter define um método 
para filtrar requisições HTTP antes que elas sejam 
processadas por recursos JAX-RS. Implementações 
desta interface podem alterar ou interromper a requisição 
com base em critérios específicos. A interface deve ser
 anotada com @Provider para ser descoberta pelo JAX-RS,
  e pode ser aplicada globalmente ou a recursos específicos 
  usando anotações de vinculação de nome. 
  */
