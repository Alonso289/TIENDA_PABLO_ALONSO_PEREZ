package curso.java.tienda.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.model.Usuario;
import curso.java.tienda.service.UsuarioService;

@Controller
@RequestMapping("")
public class LoginController {

	private UsuarioService us;

	@GetMapping("login/login")
	public String editSubmit(Model model, @ModelAttribute Usuario usuario) {
		return "/login/login";
	}

	@PostMapping("/acceso/validar")
	public String validarAcceso(HttpSession sesion, Model model, @RequestParam(required = true) String email,
			@RequestParam(required = true) String password) {

		Usuario usuario = us.validarLogin(email, password);
		sesion.setAttribute("usuario", usuario);

		return "login/login";
	}
}
