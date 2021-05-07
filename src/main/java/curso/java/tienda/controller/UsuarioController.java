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
public class UsuarioController {
//  <input id="provincia" th:field="*{provincia}" name="provincia" type="text" placeholder="" class="form-control input-md" required="" >
	@Autowired
	private UsuarioService us;
	
	@GetMapping("/TIENDA_PABLO_ALONSO_PEREZ")
	public String index(Model model) {
		//carga de datos inicial
		model.addAttribute("lista", us.getListaUsuarios());
		return "/usuario/list";
	}
	
	@GetMapping("/usuario/new")
	public String nuevo(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "/usuario/new";
	}
	
	@PostMapping("/usuario/new/submit")
	public String nuevoSubmit(Model model, @ModelAttribute Usuario usuario) {
		us.addUsuario(usuario);
		return "redirect:/TIENDA_PABLO_ALONSO_PEREZ";
	}	
	
	@GetMapping("/del/{nombre}")
    public String eliminar(@PathVariable(value="nombre") String nombre) {
		//us.deleteById(id);
		us.deleteByNombre(nombre);
        return "redirect:/TIENDA_PABLO_ALONSO_PEREZ";
    }
	
	@GetMapping("usuario/edit/{nombre}")
    public String editar(Model model, @PathVariable(value="nombre") String nombre) {
		//Usuario usuario = us.getUsuario(id);
		Usuario usuario = us.getByNombre(nombre);
		model.addAttribute("usuario", usuario);
        return "/usuario/new";
    }
	
	@PostMapping("usuario/edit/submit")
	public String editSubmit(Model model, @ModelAttribute Usuario usuario) {
		us.addUsuario(usuario);
		return "redirect:/TIENDA_PABLO_ALONSO_PEREZ";
	}	
}