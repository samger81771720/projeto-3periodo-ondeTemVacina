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

																			// ContainerRequestFilter - "Filtro de solicita��o de cont�iner"
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
 
Recursos JAX-RS s�o classes Java que tratam requisi��es 
HTTP em aplica��es RESTful. Cada recurso � mapeado para 
um caminho URI espec�fico e cont�m m�todos que respondem 
a diferentes tipos de requisi��es HTTP (GET, POST, etc.), 
processando a entrada e retornando respostas. RESTful se 
refere a servi�os web que aderem aos princ�pios 
REST (Transfer�ncia Representacional do Estado), utilizando m�todos 
HTTP para operar em recursos identificados por URIs, 
permitindo opera��es como cria��o, leitura, atualiza��o 
e exclus�o de dados de forma stateless (sem estado). 
A interface ContainerRequestFilter define um m�todo 
para filtrar requisi��es HTTP antes que elas sejam 
processadas por recursos JAX-RS. Implementa��es 
desta interface podem alterar ou interromper a requisi��o 
com base em crit�rios espec�ficos. A interface deve ser
 anotada com @Provider para ser descoberta pelo JAX-RS,
  e pode ser aplicada globalmente ou a recursos espec�ficos 
  usando anota��es de vincula��o de nome. 
  */
