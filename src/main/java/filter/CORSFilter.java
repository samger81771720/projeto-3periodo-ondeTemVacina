package filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse resp = (HttpServletResponse) response;
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Headers", "idSessao,Content-Type,Authorization,Accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Content-Length,Connection");
		resp.addHeader("Access-Control-Expose-Headers", "Authorization,Access-Control-Allow-Origin,Access-Control-Allow-Credentials,Content-Type,Content-Length,Content-Encoding,Connection");
		resp.addHeader("Access-Control-Allow-Credentials", "true");
		resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		resp.addHeader("Access-Control-Max-Age", "1209600");
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		if(req.getMethod().equalsIgnoreCase("OPTIONS")) {
			resp.setStatus(200);
		}else {
			chain.doFilter(request, response);
		}
	}
}
/*
Essa classe CORSFilter é um filtro Java Servlet que lida com o CORS 
(Cross-Origin Resource Sharing), permitindo que aplicações web 
possam fazer requisições para outros domínios além do domínio de origem.

@Provider: Indica que essa classe é um provedor JAX-RS, mas no contexto 
de filtros Servlet, isso pode não ter impacto direto. Normalmente usado em 
ambientes que suportam JAX-RS.

Implementação de Filter: Implementa a interface Filter, que permite 
interceptar requisições HTTP antes que elas alcancem um servlet ou JSP.

doFilter: Método principal onde a lógica do filtro é implementada.

Configura cabeçalhos CORS como Access-Control-Allow-Origin para permitir 
todos os origens (*), especifica os métodos permitidos 
(GET, POST, PUT, DELETE, OPTIONS, HEAD), expõe cabeçalhos adicionais 
e permite credenciais (Access-Control-Allow-Credentials).
Verifica se a requisição é do tipo OPTIONS e, se for, retorna um status 200 OK 
sem passar pela cadeia de filtros (FilterChain). Caso contrário, permite que a 
requisição prossiga normalmente.

Objetivo: Esse filtro é usado para lidar com requisitos de segurança do 
navegador que restringem requisições feitas de scripts em uma página 
para um servidor em outro domínio.

Em resumo, o CORSFilter garante que as requisições entre diferentes 
origens sejam seguras e controladas conforme as políticas CORS 
definidas, permitindo que aplicações web possam funcionar corretamente 
com serviços em diferentes domínios.







*/
