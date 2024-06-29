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
A anotação " @Provider " permite que a classe AuthFilter seja registrada como um 
filtro JAX-RS quando a aplicação é iniciada. O método filter da classe AuthFilter 
é chamado para cada requisição recebida, onde ela verifica se a URL é restrita 
e valida o ID de sessão em tempo de execução.
*/
@Provider 
public class AuthFilter implements ContainerRequestFilter {
	/*                                                                                 ^^^^^
	ContainerRequestFilter - "Interface que filtra (bloqueando ou modificando, quando necessário) as requisições http."
	*/
	private static final String BASE_URL_RESTRITA = "restrito"; // Define uma constante para a URL base restrita.
	public static final String CHAVE_ID_SESSAO = "idSessao"; // Define uma constante para a chave de ID de sessão.
	
	private LoginService loginService = new LoginService(); // Cria uma instância da classe LoginService.
	
	/*
	 "ContainerRequestContext" - Representa o contexto da requisição HTTP, fornecendo métodos para acessar detalhes dessa requisição.
	  ContainerRequestContext e UriInfo não pertencem à mesma interface, nem fazem parte da interface ContainerRequestFilter. 
	  Eles são classes/interfaces distintas no JAX-RS, cada uma com seu próprio propósito.
	  
	 ContainerRequestContext e UriInfo são componentes distintos do JAX-RS, cada um com sua própria funcionalidade.
	" ContainerRequestContext " - fornece acesso ao contexto da requisição e pode retornar um UriInfo.
	" UriInfo " -  fornece detalhes específicos sobre a URI da requisição.
	  
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException { // Implementa o método filter da interface ContainerRequestFilter.
	    UriInfo url = requestContext.getUriInfo(); // Obtém informações sobre a URI da requisição.
	    if (url.getPath().contains(BASE_URL_RESTRITA)) { // Verifica se a URL contém a parte restrita.
	        List<String> keysSessionId = requestContext.getHeaders().get(CHAVE_ID_SESSAO); // Obtém o ID de sessão dos cabeçalhos da requisição. A razão pela qual é utilizado um List<String> para obter o ID de sessão é porque os cabeçalhos HTTP podem teoricamente conter múltiplos valores para uma mesma chave. 
	
	        if (keysSessionId != null && keysSessionId.size() == 1) { // Verifica se o ID de sessão está presente e é único.
	            String sessionId = keysSessionId.get(0); // Obtém o ID de sessão.
	            validarApiKey(sessionId, requestContext); // Valida o ID de sessão.
	        } else {
	            montarResponseUnauthorized(requestContext); // Monta a resposta de acesso não autorizado.
	        }
	    }
	}
	
	private void validarApiKey(String idSessao, ContainerRequestContext requestContext) { // Método para validar o ID de sessão.
	    if (!loginService.chaveValida(idSessao)) { // Verifica se a chave é válida.
	        montarResponseUnauthorized(requestContext); // Monta a resposta de acesso não autorizado.
	    }
	}
	
	private void montarResponseUnauthorized(ContainerRequestContext requestContext) { // Método para montar a resposta de acesso não autorizado.
	    ControleVacinasException exception = new ControleVacinasException("Usuário sem acesso"); // Cria uma nova exceção com a mensagem "Usuário sem acesso".
	    String json = ExceptionHandler.converterExceptionParaJson(exception); // Converte a exceção para JSON.
	
	    Response response = Response.status(Status.FORBIDDEN) // Cria a resposta com status 403 (Proibido).
	            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON) // Define o cabeçalho de tipo de conteúdo como JSON.
	            .entity(json) // Define o corpo da resposta como o JSON da exceção.
	            .build();
	
	    requestContext.abortWith(response); // Aborta a requisição com a resposta criada.
	}

}


/*
Recursos JAX-RS são classes Java que tratam requisições HTTP 
em aplicações RESTful(RESTful se refere a serviços web que 
aderem aos princípios REST (Transferência Representacional 
do Estado), os quais utilizam métodos HTTP para operar em 
recursos identificados por URIs, permitindo operações como 
criação, leitura, atualização e exclusão de dados de forma 
stateless (sem estado)). 

Dentro desse contexto a interface “ContainerRequestFilter” 
define um método para filtrar requisições HTTP antes que 
elas sejam processadas por recursos JAX-RS. Implementações 
desta interface podem alterar ou “bloquear” a requisição com 
base em critérios específicos. A interface deve ser anotada 
com @Provider para ser descoberta pelo JAX-RS, e pode ser 
aplicada globalmente ou a recursos específicos usando 
anotações de vinculação de nome.
  */

/*
 URL: Endereço completo de um recurso na web (ex.: https://example.com/api/resource).
 
Requisição: Mensagem enviada ao servidor para acessar ou modificar um recurso, 
incluindo método HTTP (GET, POST, ETC), cabeçalhos e corpo.

A requisição contém a URL, que especifica o endereço do 
recurso que está sendo acessado ou modificado.
 */







