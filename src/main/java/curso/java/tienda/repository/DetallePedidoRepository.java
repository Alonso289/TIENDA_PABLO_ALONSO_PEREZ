package curso.java.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.model.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

	Iterable<DetallePedido> findByIdPedido(int id);

	DetallePedido findById(int id);
}
