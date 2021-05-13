package curso.java.tienda.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.model.Producto;
import curso.java.tienda.model.Usuario;
import curso.java.tienda.service.CarritoService;
import curso.java.tienda.service.CategoriaService;
import curso.java.tienda.service.MetodoPagoService;
import curso.java.tienda.service.PedidoService;
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

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private CarritoService carritoService;

	@Autowired
	private MetodoPagoService mps;

	private static Logger logger = LogManager.getLogger(CarritoController.class);

	// MUESTRA LA LISTA DEL CARRITO
	@GetMapping("/carrito/list")
	public String listCarrito(HttpSession session, Model model) {

		ArrayList<Producto> carrito = (ArrayList<Producto>) session.getAttribute("carrito");

		// EN CASO DE NO TENER CARRITO CREA UNO Y LO PONE EN LA SESSION
		if (carrito == null) {
			carrito = new ArrayList<Producto>();
			session.setAttribute("carrito", carrito);
		}

		// ASIGNA DATOS AL MODELO PARA MOSTRAR EN LA VISTA
		model.addAttribute("listaCategorias", cs.getListaCategorias());
		model.addAttribute("total", carritoService.getTotal(carrito) + " €");
		model.addAttribute("metodoPago", mps.getMetodoPago());

		logger.info("MOSTRANDO LA LISTA DEL CARRITO");
		return "/carrito/list";
	}

	// ANADE PRODUCTO AL CARRITO
	@GetMapping("/carrito/anadir/{id}")
	public String anadirProducto(HttpSession session, Model model, @PathVariable("id") int id) {

		logger.info("ANADIENDO PRODUCTO AL CARRITO");
		ArrayList<Producto> carrito = null;

		// CREA CARRITO EN EL CASO DE QUE NO EXISTA YA Y ANADE EL PRODUCTO
		if (session.getAttribute("carrito") == null) {

			carrito = new ArrayList<Producto>();
			carrito.add(ps.getProducto(id));

		} else {
			carrito = (ArrayList<Producto>) session.getAttribute("carrito");
			carrito.add(ps.getProducto(id));

		}

		// ASIGNA DATOS A LA SESION/MODELO PARA MOSTRAR EN LA VISTA
		session.setAttribute("carrito", carrito);
		model.addAttribute("listaCategorias", cs.getListaCategorias());

		logger.info("PRODUCTO ANADIDO AL CARRITO");

		return "redirect:/carrito/list";
	}

	//ELIMINA PRODUCTO DEL CARRITO
	@GetMapping("/carrito/eliminar/{id}")
	public String deleteProducto(HttpSession session, Model model, @PathVariable("id") int id) {

		logger.info("ELIMINANDO PRODUCTO DEL CARRITO");
		ArrayList<Producto> carrito = (ArrayList<Producto>) session.getAttribute("carrito");

		//RECORRE EL CARRITO Y BORRA EL CORRESPONDIENTE PRODUCTO
		for (int i = 0; i < carrito.size(); i++) {

			Producto producto = carrito.get(i);
			if (producto.getId() == id)
				carrito.remove(producto);
		}

		logger.info("PRODUCTO ELMININADO DEL CARRITO");
		return "redirect:/carrito/list";
	}

	// REALIZA PEDIDO DE LO QUE ESTA EN EL CARRITO
	@GetMapping("/carrito/buy")
	public String realizaPedido(HttpSession session, Model model, @RequestParam(required = true) String metodoPago) {

		logger.info("REALIZANDO PEDIDO DEL CARRITO");

		Usuario usuario = (Usuario) session.getAttribute("usuario");
		if (usuario != null) {

			ArrayList<Producto> carrito = (ArrayList<Producto>) session.getAttribute("carrito");
			pedidoService.crearPedido(usuario, carrito, metodoPago);

			session.setAttribute("carrito", new ArrayList<Producto>());

			logger.info("PEDIDO REALIZADO");
			return "/pedido/comprado";
		}

		logger.info("NO SE PUDO REALIZAR EL PEDIDO");
		return "redirect:/login";
	}

}
