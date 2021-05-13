package curso.java.tienda.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.DetallePedido;
import curso.java.tienda.model.Pedido;
import curso.java.tienda.model.Producto;
import curso.java.tienda.model.Usuario;
import curso.java.tienda.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private DetallePedidoService dps;
	
	private static final int CLIENTE_ROL = 3;

	public void addPedido(Pedido pedido) {

		pedidoRepository.save(pedido);
	}

	public Iterable<Pedido> getlistaPedidos() {

		return pedidoRepository.findAll();
	}

	public Pedido getPedido(int id) {
		return pedidoRepository.findById(id);
	}

	public void crearPedido(Usuario user, ArrayList<Producto> productos, String pago) {

		Pedido pedido = new Pedido();
		pedido.setIdUsuario((int) user.getId());
		pedido.setEstado("Pendiente");
		pedido.setMetodoPago(pago);
		pedido.setNumFactura("");
		addPedido(pedido);

		double total = 0;
		for (int i = 0; i < productos.size(); i++) {

			Producto producto = productos.get(i);

			DetallePedido detallePedido = new DetallePedido();

			detallePedido.setIdPedido(pedido.getId());
			detallePedido.setIdProducto(producto.getId());

			float precio = Float.parseFloat(producto.getPrecio().toString());
			detallePedido.setPrecioUnidad(precio);

			detallePedido.setUnidades(1);
			detallePedido.setImpuesto(producto.getImpuesto());
			detallePedido.setTotal(producto.getPrecio() * detallePedido.getUnidades());

			dps.addDetallePedido(detallePedido);
			total += detallePedido.getTotal();
		}

		pedido.setTotal(total);
		addPedido(pedido);

	}

	public void pedidoEnviar(Pedido pedido) {

		pedido.setEstado("Enviado");
		
		//Obtenemos un numero para la factura
		Calendar c = new GregorianCalendar();
		String dia = Integer.toString(c.get(Calendar.DATE));
		String mes = Integer.toString(c.get(Calendar.MONTH));
		String annio = Integer.toString(c.get(Calendar.YEAR));
		
		pedido.setNumFactura(annio+mes+dia);
		addPedido(pedido);

	}
	
	public void pedidoCancelar(Pedido pedido, int rol) {

		if(rol == CLIENTE_ROL)
			pedido.setEstado("Pendiente Cancelar");
		else
			pedido.setEstado("Cancelado");
		
		addPedido(pedido);

	}
}
