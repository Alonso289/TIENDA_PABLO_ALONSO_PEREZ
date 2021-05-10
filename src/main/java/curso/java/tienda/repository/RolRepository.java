package curso.java.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.java.tienda.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {

}
