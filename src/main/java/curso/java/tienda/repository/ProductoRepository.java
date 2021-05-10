package curso.java.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

	Producto findById(int id);
	Producto findByNombre(String nombre);
}
