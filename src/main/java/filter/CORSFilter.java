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
Essa classe CORSFilter � um filtro Java Servlet que lida com o CORS 
(Cross-Origin Resource Sharing), permitindo que aplica��es web 
possam fazer requisi��es para outros dom�nios al�m do dom�nio de origem.

@Provider: Indica que essa classe � um provedor JAX-RS, mas no contexto 
de filtros Servlet, isso pode n�o ter impacto direto. Normalmente usado em 
ambientes que suportam JAX-RS.

Implementa��o de Filter: Implementa a interface Filter, que permite 
interceptar requisi��es HTTP antes que elas alcancem um servlet ou JSP.

doFilter: M�todo principal onde a l�gica do filtro � implementada.

Configura cabe�alhos CORS como Access-Control-Allow-Origin para permitir 
todos os origens (*), especifica os m�todos permitidos 
(GET, POST, PUT, DELETE, OPTIONS, HEAD), exp�e cabe�alhos adicionais 
e permite credenciais (Access-Control-Allow-Credentials).
Verifica se a requisi��o � do tipo OPTIONS e, se for, retorna um status 200 OK 
sem passar pela cadeia de filtros (FilterChain). Caso contr�rio, permite que a 
requisi��o prossiga normalmente.

Objetivo: Esse filtro � usado para lidar com requisitos de seguran�a do 
navegador que restringem requisi��es feitas de scripts em uma p�gina 
para um servidor em outro dom�nio.

Em resumo, o CORSFilter garante que as requisi��es entre diferentes 
origens sejam seguras e controladas conforme as pol�ticas CORS 
definidas, permitindo que aplica��es web possam funcionar corretamente 
com servi�os em diferentes dom�nios.







*/
