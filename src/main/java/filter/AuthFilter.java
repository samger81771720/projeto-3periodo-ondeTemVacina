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




/*
A anota��o " @Provider " permite que a classe AuthFilter seja registrada como um 
filtro JAX-RS quando a aplica��o � iniciada. O m�todo filter da classe AuthFilter 
� chamado para cada requisi��o recebida, onde ela verifica se a URL � restrita 
e valida o ID de sess�o em tempo de execu��o.
*/
@Provider 
public class AuthFilter implements ContainerRequestFilter {
	/*                                                                                 ^^^^^
	ContainerRequestFilter - "Interface que filtra (bloqueando ou modificando, quando necess�rio) as requisi��es http."
	*/
	private static final String BASE_URL_RESTRITA = "restrito"; // Define uma constante para a URL base restrita.
	public static final String CHAVE_ID_SESSAO = "idSessao"; // Define uma constante para a chave de ID de sess�o.
	
	private LoginService loginService = new LoginService(); // Cria uma inst�ncia da classe LoginService.
	
	/*
	 "ContainerRequestContext" - Representa o contexto da requisi��o HTTP, fornecendo m�todos para acessar detalhes dessa requisi��o.
	  ContainerRequestContext e UriInfo n�o pertencem � mesma interface, nem fazem parte da interface ContainerRequestFilter. 
	  Eles s�o classes/interfaces distintas no JAX-RS, cada uma com seu pr�prio prop�sito.
	  
	 ContainerRequestContext e UriInfo s�o componentes distintos do JAX-RS, cada um com sua pr�pria funcionalidade.
	" ContainerRequestContext " - fornece acesso ao contexto da requisi��o e pode retornar um UriInfo.
	" UriInfo " -  fornece detalhes espec�ficos sobre a URI da requisi��o.
	  
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException { // Implementa o m�todo filter da interface ContainerRequestFilter.
	    UriInfo url = requestContext.getUriInfo(); // Obt�m informa��es sobre a URI da requisi��o.
	    if (url.getPath().contains(BASE_URL_RESTRITA)) { // Verifica se a URL cont�m a parte restrita.
	        List<String> keysSessionId = requestContext.getHeaders().get(CHAVE_ID_SESSAO); // Obt�m o ID de sess�o dos cabe�alhos da requisi��o. A raz�o pela qual � utilizado um List<String> para obter o ID de sess�o � porque os cabe�alhos HTTP podem teoricamente conter m�ltiplos valores para uma mesma chave. 
	
	        if (keysSessionId != null && keysSessionId.size() == 1) { // Verifica se o ID de sess�o est� presente e � �nico.
	            String sessionId = keysSessionId.get(0); // Obt�m o ID de sess�o.
	            validarApiKey(sessionId, requestContext); // Valida o ID de sess�o.
	        } else {
	            montarResponseUnauthorized(requestContext); // Monta a resposta de acesso n�o autorizado.
	        }
	    }
	}
	
	private void validarApiKey(String idSessao, ContainerRequestContext requestContext) { // M�todo para validar o ID de sess�o.
	    if (!loginService.chaveValida(idSessao)) { // Verifica se a chave � v�lida.
	        montarResponseUnauthorized(requestContext); // Monta a resposta de acesso n�o autorizado.
	    }
	}
	
	private void montarResponseUnauthorized(ContainerRequestContext requestContext) { // M�todo para montar a resposta de acesso n�o autorizado.
	    ControleVacinasException exception = new ControleVacinasException("Usu�rio sem acesso"); // Cria uma nova exce��o com a mensagem "Usu�rio sem acesso".
	    String json = ExceptionHandler.converterExceptionParaJson(exception); // Converte a exce��o para JSON.
	
	    Response response = Response.status(Status.FORBIDDEN) // Cria a resposta com status 403 (Proibido).
	            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON) // Define o cabe�alho de tipo de conte�do como JSON.
	            .entity(json) // Define o corpo da resposta como o JSON da exce��o.
	            .build();
	
	    requestContext.abortWith(response); // Aborta a requisi��o com a resposta criada.
	}

}


/*
Recursos JAX-RS s�o classes Java que tratam requisi��es HTTP 
em aplica��es RESTful(RESTful se refere a servi�os web que 
aderem aos princ�pios REST (Transfer�ncia Representacional 
do Estado), os quais utilizam m�todos HTTP para operar em 
recursos identificados por URIs, permitindo opera��es como 
cria��o, leitura, atualiza��o e exclus�o de dados de forma 
stateless (sem estado)). 

Dentro desse contexto a interface �ContainerRequestFilter� 
define um m�todo para filtrar requisi��es HTTP antes que 
elas sejam processadas por recursos JAX-RS. Implementa��es 
desta interface podem alterar ou �bloquear� a requisi��o com 
base em crit�rios espec�ficos. A interface deve ser anotada 
com @Provider para ser descoberta pelo JAX-RS, e pode ser 
aplicada globalmente ou a recursos espec�ficos usando 
anota��es de vincula��o de nome.
  */

/*
 URL: Endere�o completo de um recurso na web (ex.: https://example.com/api/resource).
 
Requisi��o: Mensagem enviada ao servidor para acessar ou modificar um recurso, 
incluindo m�todo HTTP (GET, POST, ETC), cabe�alhos e corpo.

A requisi��o cont�m a URL, que especifica o endere�o do 
recurso que est� sendo acessado ou modificado.
 */







