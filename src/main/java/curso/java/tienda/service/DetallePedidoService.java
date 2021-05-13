package curso.java.tienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.DetallePedido;
import curso.java.tienda.model.Pedido;
import curso.java.tienda.repository.DetallePedidoRepository;

@Service
public class DetallePedidoService {

	@Autowired
	private DetallePedidoRepository detallePedidoRepository;
	@Autowired
	private PedidoService ps;
	@Autowired
	private DetallePedidoService dps;
	
	private static final int VACIO = 0;


	public void addDetallePedido(DetallePedido detallePedido) {

		detallePedidoRepository.save(detallePedido);
	}

	public Iterable<DetallePedido> getListaDetalleByIdPedido(int id) {

		return detallePedidoRepository.findByIdPedido(id);
	}

	public DetallePedido getDetallePedidoById(int id) {
		return detallePedidoRepository.findById(id);
	}
	
	public void deleteDetalle(int idDetalle) {
		
		detallePedidoRepository.deleteById(idDetalle);
	}

	//ELIMINA LOS DETALLES RELACIONADOS CON EL ID DEL PEDIDO DEL USUARIO A ELIMINAR
	public void deleteAllDetalleUsuario(int id) {
		
		ArrayList<DetallePedido> listaDetalle = (ArrayList<DetallePedido>) detallePedidoRepository.findByIdPedido(id);
		
		for(int i=0; i<listaDetalle.size();i++) {
			
			DetallePedido detallePedido = listaDetalle.get(i);
			detallePedidoRepository.deleteById(detallePedido.getId());
		
		}
	}
	
	//ELIMINA LOS DETALLES RELACIONADOS CON EL ID DEL PRODUCTO A ELIMINAR
	public void deleteAllDetalleProducto(int id) {
		
		
		ArrayList<DetallePedido> listaDetalle = (ArrayList<DetallePedido>) detallePedidoRepository.findByIdProducto(id);
		
		for(int i=0; i<listaDetalle.size();i++){ 
			
			
			//ELIMINAMOS EL DETALLE
			detallePedidoRepository.deleteById(listaDetalle.get(i).getId());
			
			int idPedido = listaDetalle.get(i).getIdPedido();
			//OBTENEMOS LOS DETALLE PRODUCTO DEL PEDIDO AL QUE PERTENECE EL DETALLE
			ArrayList<DetallePedido> listaProductoDetalle = (ArrayList<DetallePedido>) dps.getListaDetalleByIdPedido(idPedido);
			
			//CANCELAMOS EL PEDIDO EN EL CASO DE NO TENER DETALLE PRODUCTO
			if(listaProductoDetalle.size() == VACIO) {
				
				Pedido pedido = ps.getPedido(idPedido);
				ps.pedidoCancelar(pedido, VACIO);
			
			}
			
			
		}

	}

}
