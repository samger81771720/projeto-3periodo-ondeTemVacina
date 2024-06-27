package controller;

import exception.ControleVacinasException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.dto.UsuarioDTO;
import model.entity.Pessoa;
import model.service.LoginService;

@Path("/login")
public class LoginController {
	
	private LoginService loginService = new LoginService();
	
	@POST
	@Path("/autenticar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Pessoa autenticar(UsuarioDTO usuarioTentandoAutenticar) throws ControleVacinasException {
		return loginService.autenticar(usuarioTentandoAutenticar);
	}
	
}
