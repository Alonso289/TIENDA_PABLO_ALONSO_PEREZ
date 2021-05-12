package curso.java.tienda.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import curso.java.tienda.model.DetallePedido;
import curso.java.tienda.model.Pedido;
import curso.java.tienda.model.Usuario;
import curso.java.tienda.service.DetallePedidoService;
import curso.java.tienda.service.PedidoService;
import curso.java.tienda.service.ProductoService;
import curso.java.tienda.service.UsuarioService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Controller
@RequestMapping("")
public class PedidoController {

	@Autowired
	private PedidoService ps;
	@Autowired
	private UsuarioService us;
	@Autowired
	private DetallePedidoService dps;
	@Autowired
	private ProductoService productoServicio;

	private static Logger logger = LogManager.getLogger(CarritoController.class);

	@GetMapping("/pedido/list")
	public String listar(Model model) {

		logger.info("OBTENIENDO LISTAS");

		model.addAttribute("listaUsuarios", us.getListaUsuarios());
		model.addAttribute("listaPedidos", ps.getlistaPedidos());

		logger.info("MOSTRANDO VISTA DE PEDIDOS");
		return "/pedido/list";
	}

	@GetMapping("/pedido/listDetalle/{id}")
	public String detalles(Model model, @PathVariable("id") int id) {

		logger.info("OBTENIENDO LISTAS");

		model.addAttribute("pedido", ps.getPedido(id));
		model.addAttribute("listaDetalles", dps.getListaDetalleByIdPedido(id));
		model.addAttribute("listaProductos", productoServicio.getListaProductos());

		logger.info("MOSTRANDO VISTA DETALLE");
		return "/pedido/listDetalle";
	}
	
	@GetMapping("/pedido/enviar/{id}")
	public String enviar(Model model, @PathVariable("id") int id) {

		logger.info("ENVIANDO PEDIDO");

		ps.pedidoEnviar(ps.getPedido(id));

		logger.info("PEDIDO ENVIADO");
		return "redirect:/pedido/list";
	}
	
	@GetMapping("/pedido/cancelar/{id}")
	public String cancelar(HttpSession session, Model model, @PathVariable("id") int id) {

		logger.info("CANCELANDO PEDIDO");
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		ps.pedidoCancelar(ps.getPedido(id), usuario.getRol());

		logger.info("PEDIDO CANCELADO");
		return "redirect:/pedido/list";
	}

	
	
	
	
	
	
	


}