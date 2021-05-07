package curso.java.tienda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import curso.java.tienda.model.Usuario;
import curso.java.tienda.service.UsuarioService;

@Controller
@RequestMapping("")
public class LoginController {
	
	@GetMapping("login/login")
	public String editSubmit(Model model, @ModelAttribute Usuario usuario) {
		return "/login/login";
	}

}
