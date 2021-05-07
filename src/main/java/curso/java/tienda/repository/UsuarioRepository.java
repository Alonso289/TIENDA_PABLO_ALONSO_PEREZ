package curso.java.tienda.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import curso.java.tienda.model.Usuario;


public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	Usuario findById(long id);
	Usuario findByNombre(String nombre);
}

