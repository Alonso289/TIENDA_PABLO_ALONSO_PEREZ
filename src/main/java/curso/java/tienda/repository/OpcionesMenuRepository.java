package curso.java.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.model.OpcionesMenu;

public interface OpcionesMenuRepository extends JpaRepository<OpcionesMenu, Integer> {

	Iterable<OpcionesMenu> findById(int id);

}
