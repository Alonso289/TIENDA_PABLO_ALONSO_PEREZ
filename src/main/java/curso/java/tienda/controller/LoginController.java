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

	@Autowired
	private UsuarioService us;

	@GetMapping("/login")
	public String login(Model model) {
		return "/login/login";
	}

	@PostMapping("/login/acceso/validar")
	public String validarAcceso(HttpSession sesion, Model model, @RequestParam(required = true) String email,
			@RequestParam(required = true) String clave) {

		Usuario usuario = us.validarLogin(email, clave);
		sesion.setAttribute("usuario", usuario);

		return "redirect:/";
	}
}
