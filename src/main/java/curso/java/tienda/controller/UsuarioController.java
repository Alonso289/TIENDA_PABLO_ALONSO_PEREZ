package curso.java.tienda.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import curso.java.tienda.model.Rol;
import curso.java.tienda.model.Usuario;
import curso.java.tienda.service.RolService;
import curso.java.tienda.service.UsuarioService;

@Controller
@RequestMapping("")
public class UsuarioController {
//  <input id="provincia" th:field="*{provincia}" name="provincia" type="text" placeholder="" class="form-control input-md" required="" >

	@Autowired
	private UsuarioService us;
	@Autowired
	private RolService rs;

	@GetMapping("/usuario/list")
	public String listaUsuario(HttpSession session, Model model) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		if(usuario.getRol() == 2)
			model.addAttribute("listaUsuarios", us.getListaByRol(3));
		else
			model.addAttribute("listaUsuarios", us.getListaUsuarios());
		
		model.addAttribute("listaRoles", rs.getListaRoles());
		return "/usuario/list";
	}

	@GetMapping("/usuario/new")
	public String nuevo(Model model) {
		
		model.addAttribute("listaRoles", rs.getListaRoles());
		model.addAttribute("usuario", new Usuario());		
		return "/usuario/new";
	}

	@PostMapping("/usuario/new/submit")
	public String nuevoSubmit(Model model, @ModelAttribute Usuario usuario) {
	
		us.addUsuario(usuario);		
		return "redirect:/usuario/list";
	}

	@GetMapping("/del/{id}")
	public String delete(@PathVariable(value = "id") int id) {

		us.deleteById(id);	
		return "redirect:/usuario/list";
	}

	@GetMapping("/usuario/edit/{id}")
	public String edit(Model model, @PathVariable(value = "id") int id) {

		model.addAttribute("listaRoles", rs.getListaRoles());
		model.addAttribute("usuario", us.getUsuario(id));		
		return "/usuario/new";
	}

	@PostMapping("/usuario/edit/submit")
	public String editSubmit(Model model, @ModelAttribute Usuario usuario) {
		
		us.addUsuario(usuario);		
		return "redirect:/usuario/list";
	}
	
	@GetMapping("/usuario/perfil")
	public String perfil(Model model) {
	
		return "/usuario/perfil";
	}
}