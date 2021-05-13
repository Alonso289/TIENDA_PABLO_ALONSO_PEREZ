package curso.java.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	Pedido findById(int id);

	Iterable<Pedido> findByIdUsuario(int id);
	
}
