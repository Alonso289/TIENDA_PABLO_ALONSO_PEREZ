package curso.java.tienda.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.model.Usuario;
import curso.java.tienda.model.OpcionesMenu;
import curso.java.tienda.service.OpcionesMenuService;
import curso.java.tienda.service.UsuarioService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Controller
@RequestMapping("")
public class LoginController {

	@Autowired
	private UsuarioService us;

	@Autowired
	private OpcionesMenuService oms;

	private static Logger logger = LogManager.getLogger(CarritoController.class);
	
	private static final String MENSAJE = "Usuario o Contraseña Incorrectos";

	private static final int ROL_CLIENTE = 3;
	//MUESTRA EL LOGIN
	@GetMapping("/login")
	public String login(Model model) {

		model.addAttribute("mensaje", "");
		logger.info("MOSTRANDO VISTA DEL LOGIN");
		return "/login/login";
	}

	//VALIDA LAS CREDENCIALES INTRODUCIDAS EN EL LOGIN
	@PostMapping("/login/acceso/valida")
	public String validaLogin(HttpSession session, Model model, @Valid @RequestParam(required = true) String email,
			@RequestParam(required = true) String clave) {

		logger.info("VALIDANDO LOGIN");

		boolean correcto = us.validaLogin(email, clave);

		if (correcto) {

			logger.info("LOGIN VALIDADO");

			Usuario usuario = us.getUsuario(email);
			session.setAttribute("usuario", usuario);

			ArrayList<OpcionesMenu> listaOpciones = (ArrayList<OpcionesMenu>) oms.getListaOpciones();
			ArrayList<OpcionesMenu> opcionesMenu = new ArrayList<>();

			for (int i = 0; i < listaOpciones.size(); i++) {

				if (listaOpciones.get(i).getId_rol() == usuario.getRol())
					opcionesMenu.add(listaOpciones.get(i));

			}

			session.setAttribute("opcionesMenu", opcionesMenu);

			logger.info("REDIRECCIONANDO AL INICIO");
			return "redirect:/";
			
		} else {
			logger.info("LOGIN NO VALIDO");
			model.addAttribute("mensaje", MENSAJE);
			
			return "/login/login";
		}
	}

	//MUESTRA LA VISTA DEL REGISTRO DE LA APLICACION
	@GetMapping("/login/registrar")
	public String loginRegistrar(Model model) {

		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);

		logger.info("MOSTRANDO VISTA REGISTRO");

		return "/usuario/registro";

	}

	//REALIZA EL REGISTRO DEL NUEVO USUARIO
	@PostMapping("/login/registro")
	public String loginRegistro(Model model, @Valid @ModelAttribute Usuario usuario, BindingResult bindingResult) {

		
		//NUEVO USUARIO TIENE ROL CLIENTE POR DEFECTO
		logger.info("REGISTRANDO USUARIO");
		if (!bindingResult.hasErrors()) {
			
			usuario.setRol(ROL_CLIENTE);
			us.addUsuario(usuario);
			
			logger.info("USUARIO REGISTRADO");
			return "redirect:/login";
		}else {
			
			logger.info("USUARIO NO PUDO SER REGISTRADO");
			return "/usuario/registro";
		}
		

	}

	//CIERRA LA SESION ACTUAL Y REDIRECCIONA A LA PAGINA DE INICIO
	@GetMapping("/close")
	public String cerrarSession(HttpSession session, Model model) {

		logger.info("CERRANDO SESSION");
		session.invalidate();
		logger.info("SESSION CERRADA");
		return "redirect:/";

	}
}
