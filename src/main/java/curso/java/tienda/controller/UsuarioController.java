package curso.java.tienda.controller;

import java.util.ArrayList;

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
	public String listaUsuario(Model model) {

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

	@GetMapping("/del/{nombre}")
	public String delete(@PathVariable(value = "nombre") String nombre) {

		us.deleteByNombre(nombre);		
		return "redirect:/usuario/list";
	}

	@GetMapping("/usuario/edit/{nombre}")
	public String edit(Model model, @PathVariable(value = "nombre") String nombre) {

		model.addAttribute("listaRoles", rs.getListaRoles());
		model.addAttribute("usuario", us.getByNombre(nombre));		
		return "/usuario/new";
	}

	@PostMapping("/usuario/edit/submit")
	public String editSubmit(Model model, @ModelAttribute Usuario usuario) {
		
		us.addUsuario(usuario);		
		return "redirect:/usuario/list";
	}
}