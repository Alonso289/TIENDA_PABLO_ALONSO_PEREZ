package curso.java.tienda.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import curso.java.tienda.model.Usuario;
import curso.java.tienda.service.RolService;
import curso.java.tienda.service.UsuarioService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Controller
@RequestMapping("")
public class UsuarioController {

	@Autowired
	private UsuarioService us;
	@Autowired
	private RolService rs;

	private static Logger logger = LogManager.getLogger(UsuarioController.class);

	@GetMapping("/usuario/list")
	public String listaUsuario(HttpSession session, Model model) {

		logger.info("OBTENIENDO LISTA PRODUCTOS");

		Usuario usuario = (Usuario) session.getAttribute("usuario");
		if (usuario.getRol() == 2)
			model.addAttribute("listaUsuarios", us.getListaByRol(3));
		else
			model.addAttribute("listaUsuarios", us.getListaUsuarios());

		model.addAttribute("listaRoles", rs.getListaRoles());

		logger.info("MOSTRANDO LISTA PRODUCTOS");
		return "/usuario/list";
	}

	@GetMapping("/usuario/new")
	public String nuevo(Model model) {

		model.addAttribute("listaRoles", rs.getListaRoles());
		model.addAttribute("usuario", new Usuario());

		logger.info("MOSTRANDO VISTA DE NUEVO USUARIO");
		return "/usuario/new";
	}

	@PostMapping("/usuario/new/submit")
	public String nuevoSubmit(Model model, @Valid @ModelAttribute Usuario usuario, BindingResult bindingResult) {

		logger.info("ANADIENDO NUEVO USUARIO");
		if (bindingResult.hasErrors()) {

			logger.info("NO SE HA PODIDO ANADIR EL USUARIO");
			model.addAttribute("listaRoles", rs.getListaRoles());
			return "/usuario/new";
		} else {
			logger.info("USUARIO ANADIDO");
			us.addUsuario(usuario);
			return "redirect:/usuario/list";
		}

		// return "redirect:/usuario/list";
	}

	@GetMapping("/del/{id}")
	public String eliminarUsuario(@PathVariable("id") int id, Model model) {

		us.deleteById(id);
		return "redirect:/usuario/list";
	}

	@GetMapping("/usuario/edit/{id}")
	public String edit(Model model, @PathVariable(value = "id") int id) {

		model.addAttribute("listaRoles", rs.getListaRoles());
		model.addAttribute("usuario", us.getUsuario(id));

		logger.info("MOSTRANDO VISTA DE EDICION DE USUARIO");
		return "/usuario/new";
	}

	@PostMapping("/usuario/edit/submit")
	public String editSubmit(Model model, @Valid @ModelAttribute Usuario usuario, BindingResult bindingResult) {

		logger.info("GUARDANDO USUARIO");

		if (bindingResult.hasErrors()) {
			logger.info("NO SE HA PODIDO GUARDAR EL USUARIO");

			model.addAttribute("listaRoles", rs.getListaRoles());
			model.addAttribute("usuario", usuario);
			
			return "/usuario/new";
		} else {
			logger.info("USUARIO GUARDADO");

			us.addUsuario(usuario);
			return "redirect:/usuario/list";
		}

	}

	@GetMapping("/usuario/perfil")
	public String perfil(Model model) {

		logger.info("MOSTRANDO PERFIL DEL USUARIO");
		return "/usuario/perfil";
	}
}