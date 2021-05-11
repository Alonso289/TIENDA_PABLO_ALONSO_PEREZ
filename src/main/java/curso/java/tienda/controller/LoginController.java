package curso.java.tienda.controller;

import java.util.ArrayList;

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
import curso.java.tienda.model.OpcionesMenu;
import curso.java.tienda.service.OpcionesMenuService;
import curso.java.tienda.service.UsuarioService;

@Controller
@RequestMapping("")
public class LoginController {

	@Autowired
	private UsuarioService us;

	@Autowired
	private OpcionesMenuService oms;

	@GetMapping("/login")
	public String login(Model model) {
		return "/login/login";
	}

	@PostMapping("/login/acceso/valida")
	public String validaAcceso(HttpSession session, Model model, @RequestParam(required = true) String email,
			@RequestParam(required = true) String clave) {

		boolean correcto = us.validaLogin(email, clave);

		if (correcto) {

			Usuario usuario = us.getUsuario(email);
			session.setAttribute("usuario", usuario);
			
			ArrayList<OpcionesMenu> listaOpciones = (ArrayList<OpcionesMenu>) oms.getListaOpciones();
			ArrayList<OpcionesMenu> opcionesMenu = new ArrayList<>();
			
			for(int i=0; i<listaOpciones.size(); i++) {
				
				if(listaOpciones.get(i).getId_rol() == usuario.getRol())
					opcionesMenu.add(listaOpciones.get(i));
				
			}
			
			session.setAttribute("opcionesMenu", opcionesMenu);

			return "redirect:/";
		} else
			return "redirect:/login";

	}

	@GetMapping("/login/registrar")
	public String loginRegistrar(Model model) {

		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);

		return "/usuario/registro";

	}

	@PostMapping("/login/registro")
	public String loginRegistro(Model model, @ModelAttribute Usuario usuario) {

		usuario.setRol(3);
		us.addUsuario(usuario);

		return "redirect:/login";

	}

	@GetMapping("/close")
	public String cerrarSesion(HttpSession session, Model model) {

		session.invalidate();
		return "redirect:/";

	}
}
