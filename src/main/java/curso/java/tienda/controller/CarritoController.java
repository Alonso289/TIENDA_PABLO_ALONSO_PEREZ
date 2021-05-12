package curso.java.tienda.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import curso.java.tienda.model.Pedido;
import curso.java.tienda.model.Producto;
import curso.java.tienda.service.CategoriaService;
import curso.java.tienda.service.ProductoService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Controller
@RequestMapping("")
public class CarritoController {

	@Autowired
	private ProductoService ps;
	
	@Autowired
	private CategoriaService cs;
	
	private static Logger logger = LogManager.getLogger(CarritoController.class);

	@GetMapping("/carrito/list")
	public String listCarrito(HttpSession session, Model model) {
				
		ArrayList<Producto> carrito;
		
		if (session.getAttribute("carrito") == null) { 
			carrito = new ArrayList<Producto>();
			session.setAttribute("carrito", carrito);
		}
		
		model.addAttribute("listaCategorias", cs.getListaCategorias());
		
		logger.info("MOSTRANDO LA LISTA DEL CARRITO");
		return "/carrito/list";
	}

	@GetMapping("/carrito/anadir/{id}")
	public String anadirProducto(HttpSession session, Model model, @Valid @PathVariable("id") int id) {

		logger.info("ANADIENDO PRODUCTO AL CARRITO");
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
		
		logger.info("PRODUCTO ANADIDO AL CARRITO");

		return "redirect:/carrito/list";
	}

	@GetMapping("/carrito/eliminar/{id}")
	public String deleteProducto(HttpSession session, Model model, @PathVariable("id") int id) {

		logger.info("ELIMINANDO PRODUCTO DEL CARRITO");
		ArrayList<Producto> carrito = (ArrayList<Producto>) session.getAttribute("carrito");

		for (int i = 0; i < carrito.size(); i++) {

			Producto producto = carrito.get(i);
			if (producto.getId() == id)
				carrito.remove(producto);
		}

		logger.info("PRODUCTO ELMININADO DEL CARRITO");
		return "redirect:/carrito/list";
	}
	
	@GetMapping("/carrito/buy")
	public String realizaPedido(HttpSession session, Model model) {
		logger.info("REALIZANDO PEDIDO DEL CARRITO");
		
		if(session.getAttribute("usuario")!=null) {
//			Usuario usuario = session.getAttribute("usuario");
//			Pedido pedido = new Pedido(usuario.id, "", metodoPago, "pendiente", numFactura, total)
			model.addAttribute("usuario", new Pedido());
			
		}
		else
			return "redirect:/login";
		
		return "redirect:/carrito/list";
	}

}
