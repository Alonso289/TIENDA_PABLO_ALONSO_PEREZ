package curso.java.tienda.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import curso.java.tienda.model.Producto;
import curso.java.tienda.service.CategoriaService;
import curso.java.tienda.service.ProductoService;

@Controller
@RequestMapping("")
public class CarritoController {

	@Autowired
	private ProductoService ps;
	
	@Autowired
	private CategoriaService cs;

	@GetMapping("/carrito/list")
	public String listCarrito(HttpSession session, Model model) {
		
		ArrayList<Producto> carrito;
		
		if (session.getAttribute("carrito") == null) { 
			carrito = new ArrayList<Producto>();
			session.setAttribute("carrito", carrito);
		}
		
		model.addAttribute("listaCategorias", cs.getListaCategorias());
		return "/carrito/list";
	}

	@GetMapping("/carrito/anadir/{id}")
	public String anadirProducto(HttpSession session, Model model, @PathVariable("id") int id) {

		ArrayList<Producto> carrito = null;

		if (session.getAttribute("carrito") == null) {

			carrito = new ArrayList<Producto>();
			carrito.add(ps.getProducto(id));


		} else {
			carrito = (ArrayList<Producto>) session.getAttribute("carrito");
			carrito.add(ps.getProducto(id));

		}
		session.setAttribute("carrito", carrito);
		model.addAttribute("listaCategorias", cs.getListaCategorias());

		return "redirect:/carrito/list";
	}

	@GetMapping("/carrito/eliminar/{id}")
	public String deleteProducto(HttpSession session, Model model, @PathVariable("id") int id) {

		ArrayList<Producto> carrito = (ArrayList<Producto>) session.getAttribute("carrito");

		for (int i = 0; i < carrito.size(); i++) {

			Producto producto = carrito.get(i);
			if (producto.getId() == id)
				carrito.remove(producto);
		}

		return "redirect:/carrito/list";
	}

}
