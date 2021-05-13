package curso.java.tienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.model.DetallePedido;
import curso.java.tienda.repository.DetallePedidoRepository;

@Service
public class DetallePedidoService {

	@Autowired
	private DetallePedidoRepository detallePedidoRepository;

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

}
