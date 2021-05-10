package curso.java.tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@GetMapping("")
	public String index(Model model) {
		// carga de datos inicial
		// Data.cargaDatos(us, ps);
		model.addAttribute("listaProductos", ps.getListaProductos());
		return "/producto/list";
	}

	@GetMapping("/producto/verProducto/{nombre}")
	public String verProducto(Model model, @ModelAttribute String nombre) {

		model.addAttribute("producto", ps.getProducto(nombre));
		return "/producto/verProducto";
	}
	
	@GetMapping("/producto/listDos")
	public String segundaLista(Model model) {

		model.addAttribute("listaProductos", ps.getListaProductos());
		model.addAttribute("listaCategorias", cs.getListaCategorias());
		return "/producto/listDos";
	}

}
