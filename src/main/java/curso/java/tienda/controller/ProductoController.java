package curso.java.tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import curso.java.tienda.model.Producto;
import curso.java.tienda.model.Usuario;
import curso.java.tienda.service.CategoriaService;
import curso.java.tienda.service.ProductoService;
import curso.java.tienda.service.RolService;
import curso.java.tienda.service.UsuarioService;
import curso.java.tienda.util.Data;

@Controller
@RequestMapping("")
public class ProductoController {

	@Autowired
	private CategoriaService cs;
	@Autowired
	private ProductoService ps;
	@Autowired
	private UsuarioService us;

	@GetMapping("")
	public String index(HttpSession session, Model model) {
		// carga de datos inicial
		//Data.cargaDatos(us, ps);
		
		if (session.getAttribute("carrito") == null) { 
			ArrayList<Producto> carrito = new ArrayList<Producto>();
			session.setAttribute("carrito", carrito);
		}
		
		model.addAttribute("listaProductos", ps.getListaProductos());
		model.addAttribute("listaCategorias", cs.getListaCategorias());
		
		return "/producto/index";
	}

	@GetMapping("/producto/list")
	public String listaProducto(Model model) {

		model.addAttribute("listaProductos", ps.getListaProductos());
		model.addAttribute("listaCategorias", cs.getListaCategorias());
		return "/producto/list";
	}
	
	@GetMapping("/producto/verProducto/{id}")
	public String verProducto(Model model, @PathVariable(value = "id") int id) {

		model.addAttribute("producto", ps.getProducto(id));
		model.addAttribute("listaCategorias", cs.getListaCategorias());
		return "/producto/verProducto";
	}
	
	@GetMapping("/producto/new")
	public String nuevo(Model model) {
		
		model.addAttribute("listaCategorias", cs.getListaCategorias());
		model.addAttribute("producto", new Producto());		
		return "/producto/new";
	}

	@PostMapping("/producto/new/submit")
	public String nuevoSubmit(Model model, @ModelAttribute Producto producto) {
		
		ps.addProducto(producto);		
		return "redirect:/producto/list";
	}
	
	@GetMapping("/producto/edit/{id}")
	public String edit(Model model, @PathVariable(value = "id") int id) {

		model.addAttribute("listaCategorias", cs.getListaCategorias());
		model.addAttribute("producto", ps.getProducto(id));		
		return "/producto/new";
	}

	@PostMapping("/producto/edit/submit")
	public String editSubmit(Model model, @ModelAttribute Producto producto) {
		
		ps.addProducto(producto);		
		return "redirect:/producto/list";
	}
	
	
}
