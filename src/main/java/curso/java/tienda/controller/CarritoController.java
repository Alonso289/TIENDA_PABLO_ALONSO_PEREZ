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
import curso.java.tienda.service.ProductoService;

@Controller
@RequestMapping("")
public class CarritoController {

	@Autowired
	private ProductoService ps;

	@GetMapping("/carrito/anadir/{id}")
	public String anadirProducto(HttpSession sesion, Model model, @PathVariable("id") String id) {

		ArrayList<Producto> carrito = null;

		if (sesion.getAttribute("carrito") != null) {

			carrito = (ArrayList<Producto>) sesion.getAttribute("carrito");
			carrito.add(ps.getProducto(id));

		} else {

			carrito = new ArrayList<Producto>();
			carrito.add(ps.getProducto(id));

		}
		sesion.setAttribute("carrito", carrito);

		return "redirect:/";
	}

	@GetMapping("/carrito/eliminar/{id}")
	public String deleteProducto(HttpSession sesion, Model model, @PathVariable("id") int id) {

		ArrayList<Producto> carrito = (ArrayList<Producto>) sesion.getAttribute("carrito");

		for (int i = 0; i < carrito.size(); i++) {

			Producto producto = carrito.get(i);
			if (producto.getId() == id)
				carrito.remove(producto);
		}

		return "redirect:/carrito/list";
	}

}
